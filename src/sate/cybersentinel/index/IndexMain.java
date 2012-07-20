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
import sate.cybersentinel.analysis.technique.AnalysisTechnique;
import sate.cybersentinel.analysis.technique.ConversationCycleAnalysisTechnique;
import sate.cybersentinel.analysis.technique.DirectMessagingAnalysisTechnique;
import sate.cybersentinel.analysis.technique.PrivateMessageAnalysisTechnique;
import sate.cybersentinel.input.opensim.OpenSimGlobalChatAttributeSet;
import sate.cybersentinel.input.opensim.OpenSimPrivateChatAttributeSet;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.filter.AttributeFilter;
import sate.cybersentinel.message.filter.MessageFilter;
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
			//index.addFilter(new AttributeFilter(new OpenSimGlobalChatAttributeSet()));
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
		
		MessageFilter globalFilter = new AttributeFilter(new OpenSimGlobalChatAttributeSet());
		MessageFilter privateFilter = new AttributeFilter(new OpenSimPrivateChatAttributeSet());
		List<Message> globals = globalFilter.filter(messages);
		List<Message> privates = privateFilter.filter(messages);
		
		UserManager.process(globals);
		UserManager.process(privates);
		
		
		System.out.print("\n\n\n===== ANALYZING DIRECT MESSAGES =====\n\n\n");
		AnalysisTechnique directAnalysisTechnique = new DirectMessagingAnalysisTechnique();
		InteractionGraph directMessageInteractions = directAnalysisTechnique.analyze(globals);
		System.out.println(directMessageInteractions);
		
		System.out.print("\n\n\n===== ANALYZING CONVERSATION CYCLE =====\n\n\n");
		AnalysisTechnique conversationCycleAnalysisTechnique = new ConversationCycleAnalysisTechnique();
		InteractionGraph conversationCycleInteractions = conversationCycleAnalysisTechnique.analyze(globals);
		System.out.println(conversationCycleInteractions);
		
		System.out.print("\n\n\n===== ANALYZING PRIVATE MESSAGES =====\n\n\n");
		AnalysisTechnique privateAnalysisTechnique = new PrivateMessageAnalysisTechnique();
		InteractionGraph privateInteractions = privateAnalysisTechnique.analyze(privates);
		System.out.println(privateInteractions);
	}
	
	public static void help() {
		System.out.println("Usage: IndexMain <Location> <Query> <Hits>");
	}
}
