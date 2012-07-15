package sate.cybersentinel.input.protocol;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import sate.cybersentinel.message.Message;

/**
 * A Block either consists of a group of messages or a signal to finish or to
 * abort. Termination is an emergency that 
 * 
 * @author Jared Hance
 */
public class Block implements Serializable {
	public enum Signal {
		ABORT,
		DONE,
		MESSAGES
	}

	private static final long serialVersionUID = -3818755464727471649L;

	private Signal signal;
	private Collection<Message> messages;
	private String reason;
	
	private Block(Signal signal) {
		this.signal = signal;
	}
	
	private Block(Signal signal, Collection<Message> messages) {
		this(signal);
		this.messages = messages;
	}
	
	private Block(Signal signal, String reason) {
		this(signal);
		this.reason = reason;
	}
	
	public Signal getSignal() {
		return signal;
	}
	
	public Collection<Message> getMessages() {
		return messages;
	}
	
	public String getAbortReason() {
		return reason;
	}
	
	public static Block abort(String reason) {
		return new Block(Signal.ABORT, reason);
	}
	
	public static Block done() {
		return new Block(Signal.DONE);
	}
	
	public static Block messages(Collection<Message> messages) {
		return new Block(Signal.MESSAGES, messages);
	}
	
	public String toString() {
		Map<String, Object> map = new TreeMap<>();
		map.put("signal", signal);
		map.put("messages", messages);
		map.put("reason", reason);
		
		return map.toString();
	}
}
