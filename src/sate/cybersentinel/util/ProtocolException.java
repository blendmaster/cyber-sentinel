package sate.cybersentinel.util;

public class ProtocolException extends RuntimeException {
	private static final long serialVersionUID = -6890258878858527623L;

	public ProtocolException() { }
	public ProtocolException(String err) {
		super(err);
	}
}
