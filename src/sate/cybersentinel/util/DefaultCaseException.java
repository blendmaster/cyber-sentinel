package sate.cybersentinel.util;

/**
 * An exception thrown when a switch statement reaches a default case which should
 * normally be impossible under circumstances.
 * 
 * @author Jared Hance
 */
public class DefaultCaseException extends ImpossibleCodeExecutionException {
	private static final long serialVersionUID = -3331407631410613253L;

	public DefaultCaseException() {
		
	}
	
	public DefaultCaseException(String err) {
		super(err);
	}
}
