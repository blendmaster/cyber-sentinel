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
 * A Message without a receiver is assumed to be in global chat (within the region if
 * the location is available, otherwise to everyone)
 * 
 * @author Jared Hance
 */
public interface Message extends Serializable {
	public int getChannel();
	public String getContents();
	public Location getLocation();
	public String getReceiver();
	public String getSender();
	public Date getTime();
	
	public AttributeSet getAttributeSet();
	
	/**
	 * A message conforms to an AttributeSet iff for all attributes in the target, the
	 * attribute set of the message also contains those attributes. It is okay for the
	 * message to have extra attributes not within the target. A message always
	 * conforms to its own attribute set.
	 *  
	 * @param target The target attribute set
	 */
	public boolean conformsTo(AttributeSet target);
}
