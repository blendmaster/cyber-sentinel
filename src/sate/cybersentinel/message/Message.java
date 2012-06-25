package sate.cybersentinel.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an immutable message. Because there is only a default constructor, the class
 * is an interface since such a message would have absolutely no attributes. Instead, use
 * the MutableMessage class to create the message.
 * 
 * Once the message is created, the Message interface should always be used rather than
 * MutableMessage to prevent the attributes from changing in the middle of analysis.
 * 
 * @author Jared Hance
 */
public interface Message extends Serializable {
	public boolean hasContents();
	public String getContents();
	
	public boolean hasTime();
	public Date getTime();
	
	public boolean hasLocation();
	public Location getLocation();
	
	public boolean hasSender();
	public String getSender();
}
