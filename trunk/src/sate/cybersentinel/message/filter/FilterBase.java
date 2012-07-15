package sate.cybersentinel.message.filter;

import java.util.ArrayList;
import java.util.List;

import sate.cybersentinel.message.Message;

public abstract class FilterBase implements MessageFilter {
	protected abstract boolean check(Message message);
	
	@Override
	public List<Message> filter(Iterable<Message> messages) {
		List<Message> filtered = new ArrayList<Message>();
		for(Message m : messages) {
			if(check(m)) {
				filtered.add(m);
			}
		}
		
		return filtered;
	}
}
