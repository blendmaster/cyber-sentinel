package sate.cybersentinel.util;

/**
 * Useful for two things:
 * 
 * 1. Shutting up the compiler safely.
 * 
 * 2. Noting that a particular code block can't be reached even if the compiler
 * cannot guarantee it, and then provide an exception if the code is actually
 * possible to reach.
 * 
 * @author Jared Hance
 *
 */
public class ImpossibleCodeExecutionException extends RuntimeException {
	private static final long serialVersionUID = 4135194212242807466L;

	public ImpossibleCodeExecutionException() {
		
	}
	
	public ImpossibleCodeExecutionException(String err) {
		super(err);
	}
}
