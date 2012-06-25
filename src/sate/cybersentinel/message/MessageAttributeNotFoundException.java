package sate.cybersentinel.message;

/**
 * Depending on where a specific message comes from, it may not have all the possible
 * different attributes. If there is no such attribute but the attribute is requested,
 * then this exception will be thrown.
 * 
 * @author Jared Hance
 */
public class MessageAttributeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5470332005534667065L;

	public MessageAttributeNotFoundException() {
		super();
	}
	
	public MessageAttributeNotFoundException(String error) {
		super(error);
	}
}
