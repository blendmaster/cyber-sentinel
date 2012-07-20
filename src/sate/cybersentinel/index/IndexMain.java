package sate.cybersentinel.index;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import sate.cybersentinel.analysis.ChatResponse;
import sate.cybersentinel.analysis.ConversationProbabilityFunction;
import sate.cybersentinel.analysis.DirectMessageGraph;
import sate.cybersentinel.analysis.LogLogisticDistribution;
import sate.cybersentinel.analysis.MessageGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.input.opensim.OpenSimGlobalChatAttributeSet;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.filter.AttributeFilter;
import sate.cybersentinel.message.user.UserManager;

public class IndexMain {
	private static Logger logger = Logger.getLogger(IndexMain.class.getName());
	
	public static void main(String[] args) {
		if(args.length < 3) {
			help();
			System.exit(0);
		}
		
		String loc = args[0];
		String query = args[1];
		int hits = Integer.parseInt(args[2]);
		
		MessageIndex index = null;
		
		try {
			index = new LocalMessageIndex(new File(loc));
			index.addFilter(new AttributeFilter(new OpenSimGlobalChatAttributeSet()));
		} catch (CorruptIndexException e) {
			logger.severe("Corrupted index at; " + loc);
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe("Could not open index at: " + loc);
			e.printStackTrace();
		}
		
		List<Message> messages = null;
		try {
			messages = index.query(query, hits);
		} catch (QueryNodeException e) {
			logger.severe("Could not parse query: " + query);
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			logger.severe("Corrupt index at: " + loc);
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe("IO Failure at index at: " + loc);
			e.printStackTrace();
		}
		
		System.out.println(messages);
		UserManager.process(messages);
		
		System.out.print("\n\n\n===== ANALYZING DIRECT MESSAGES =====\n\n\n");
		
		DirectMessageGraph directMessageGraph = new DirectMessageGraph(messages, index);
		InteractionGraph directMessageInteractions = directMessageGraph.getInteractionGraph();
		System.out.println(directMessageInteractions);
		
		System.out.print("\n\n\n===== ANALYZING CONVERSATION CYCLE =====\n\n\n");
		
		ChatResponse response = new ChatResponse(messages);
		MessageGraph messageGraph = new MessageGraph(new LogLogisticDistribution(0.5, 0.5), response.getInteractions());
		InteractionGraph conversationCycleInteractions = messageGraph.getInteractionGraph();
		System.out.println(conversationCycleInteractions);
	}
	
	public static void help() {
		System.out.println("Usage: IndexMain <Location> <Query> <Hits>");
	}
}
