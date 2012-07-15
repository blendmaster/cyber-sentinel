package sate.cybersentinel.input.protocol;

import java.io.Serializable;

import sate.cybersentinel.message.AttributeSet;

/**
 * Represents the start of a session with an input driver. The session specifies basic
 * information that can be used throughout, such as what output port to send analysis results
 * to. Notably, it also includes the global attribute set that all messages sent must
 * conform to (which will be checked before analysis even starts). This class is immutable.
 * 
 * TODO Figure out exactly what fields need to be in here.
 * 
 * @author Jared Hance
 */
public class Session implements Serializable {
	private static final long serialVersionUID = -7135877383622459213L;
	
	private String identifier;
	private AttributeSet attributeSet;
	private String outputAddress;
	private int outputPort;
	
	public Session(String identifier, AttributeSet attributeSet, String outputAddress, int outputPort) {
		this.identifier = identifier;
		this.attributeSet = attributeSet;
		this.outputAddress = outputAddress;
		this.outputPort = outputPort;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public AttributeSet getAttributeSet() {
		return this.attributeSet;
	}
	
	public String getOutputAddress() {
		return this.outputAddress;
	}
	
	public int getOutputPort() {
		return this.outputPort;
	}
}
