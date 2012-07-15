package sate.cybersentinel.message.filter;

import java.util.List;

import sate.cybersentinel.message.Message;

public interface MessageFilter {
	public List<Message> filter(Iterable<Message> messages);
}
