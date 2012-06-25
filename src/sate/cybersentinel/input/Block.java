package sate.cybersentinel.input;

import java.io.Serializable;
import java.util.Collection;

import sate.cybersentinel.message.Message;

/**
 * A Block either consists of a group of messages or a signal to terminate.
 * 
 * @author Jared Hance
 */
public class Block implements Serializable {
	public enum Signal {
		MESSAGES,
		TERMINATE
	}

	private static final long serialVersionUID = -3818755464727471649L;

	private Signal signal;
	private Collection<Message> messages;
	
	private Block(Signal signal) {
		this.signal = signal;
	}
	
	private Block(Signal signal, Collection<Message> messages) {
		this(signal);
		this.messages = messages;
	}
	
	public Signal getSignal() {
		return signal;
	}
	
	public Collection<Message> getMessages() {
		return messages;
	}
	
	public static Block terminate() {
		return new Block(Signal.TERMINATE);
	}
	
	public static Block messages(Collection<Message> messages) {
		return new Block(Signal.MESSAGES, messages);
	}
}
