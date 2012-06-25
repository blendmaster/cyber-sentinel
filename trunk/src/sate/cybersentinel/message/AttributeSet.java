package sate.cybersentinel.message;

import java.io.Serializable;

/**
 * Purely contains what attributes a given message has.
 * 
 * @author Jared Hance
 */
public interface AttributeSet extends Serializable {
	public boolean hasContents();
	public boolean hasTime();
	public boolean hasLocation();
	public boolean hasSender();
}
