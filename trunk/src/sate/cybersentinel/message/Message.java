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
