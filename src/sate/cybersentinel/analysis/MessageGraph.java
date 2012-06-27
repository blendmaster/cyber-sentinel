package sate.cybersentinel.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sate.cybersentinel.message.Message;

public class MessageGraph {
	private List<Message> messages;
	
	public MessageGraph(List<Message> messages) {
		this.messages = messages;
	}
	
	public void resolveConversationCycles(String name) {
		Map<String, List<Integer>> responsesMap = new HashMap<>();
		int count = 0;
		int i = 0;
		while(i < messages.size()) {
			Message message = messages.get(i);
			if(message.getSender().equals(name)) {
				if(i + 1 >= messages.size()) {
					break;
				}
				
				i++;
				
				List<String> personsTemp = new ArrayList<>();
				int responseCount = 0;
				
				while(i < messages.size() && !messages.get(i).equals(name)) {
					message = messages.get(i);
					
					String other = message.getSender();
					personsTemp.add(other);
					
					List<Integer> responses;
					if(responsesMap.containsKey(other)) {
						responses = responsesMap.get(other);
					}
					else {
						responses = new ArrayList<Integer>();
						Integer[] zeroFiller = new Integer[count];
						Arrays.fill(zeroFiller, 0);
						responses.addAll(Arrays.asList(zeroFiller));
					}
					
					responses.add(1);
					responsesMap.put(other, responses);
				
					count++;
				}
			}
		}
	}
}
