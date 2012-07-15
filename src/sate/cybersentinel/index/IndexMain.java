package sate.cybersentinel.index;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import sate.cybersentinel.input.opensim.OpenSimGlobalChatAttributeSet;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.filter.AttributeFilter;

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
	}
	
	public static void help() {
		System.out.println("Usage: IndexMain <Location> <Query> <Hits>");
	}
}
