package sate.cybersentinel.message;

import java.io.Serializable;
import java.util.Date;

import sate.cybersentinel.message.user.User;

/**
 * Represents an immutable message. Because there is only a default constructor, the class
 * is an interface since such a message would have absolutely no attributes. Instead, use
 * the MutableMessage class to create the message.
 * 
 * Once the message is created, the Message interface should always be used rather
 * than MutableMessage to prevent the attributes from changing in the middle of
 * analysis.
 * 
 * A Message without a receiver is assumed to be in global chat (within the region if
 * the location is available, otherwise to everyone)
 * 
 * There is a lot of boilerplate here to begin with but it should be mostly over
 * with; using this in analysis and input drivers should be relatively easy.
 * 
 * @author Jared Hance
 */
public interface Message extends Serializable, Comparable {
	public int getChannel();
	public String getContents();
	public Location getLocation();
	public String getReceiverName();
	public String getReceiverUUID();
	public String getSenderName();
	public String getSenderUUID();
	public Date getTime();
    public User getUser();
    public int getID();
	
	public AttributeSet getAttributeSet();
	
	/**
	 * A message conforms to an AttributeSet iff for all attributes in the target, the
	 * attribute set of the message also contains those attributes. It is okay for the
	 * message to have extra attributes not within the target. A message always
	 * conforms to its own attribute set. This essentially means that it is a non
	 * strict subset.
	 * 
	 * @param target The target attribute set
	 */
	public boolean conformsTo(AttributeSet target);

    
}
