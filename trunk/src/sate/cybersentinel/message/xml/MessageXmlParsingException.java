package sate.cybersentinel.message.xml;

public class MessageXmlParsingException extends RuntimeException {
	private static final long serialVersionUID = 8996563118342463105L;

	public MessageXmlParsingException() { }
	
	public MessageXmlParsingException(String error) {
		super(error);
	}
}
