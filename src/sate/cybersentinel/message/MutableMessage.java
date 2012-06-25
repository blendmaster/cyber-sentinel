package sate.cybersentinel.message;

import java.util.Date;

/**
 * Used to create a Message. In contrast to Message, which has a `has` and a `get` method
 * for each attribute, this class also has a `set` and `clear`. `clear` simply sets the
 * `has` field for the attribute to false.
 * 
 * @author Jared Hance
 */
public class MutableMessage implements Message {
	private static final long serialVersionUID = 5324243179213401252L;
	
	protected boolean hasContents = false;
	protected String contents;
	
	protected boolean hasTime = false;
	protected Date time;
	
	protected boolean hasLocation = false;
	protected Location location;
	
	protected boolean hasSender;
	protected String sender;
	
	@Override
	public boolean hasContents() {
		return hasContents;
	}
	
	@Override
	public String getContents() {
		if(!hasContents) {
			throw new MessageAttributeNotFoundException("contents");
		}
		return contents;
	}
	
	public void setContents(String contents) {
		this.hasContents = true;
		this.contents = contents;
	}
	
	public void clearContents() {
		this.hasContents = false;
		this.contents = null;
	}
	
	@Override
	public boolean hasTime() {
		return hasTime;
	}
	
	@Override
	public Date getTime() {
		if(!hasTime) {
			throw new MessageAttributeNotFoundException("time");
		}
		return time;
	}
	
	public void setTime(Date time) {
		this.hasTime = true;
		this.time = time;
	}
	
	public void clearTime() {
		this.hasTime = false;
		this.time = null;
	}
	
	@Override
	public boolean hasLocation() {
		return hasLocation;
	}
	
	@Override
	public Location getLocation() {
		if(!hasLocation) {
			throw new MessageAttributeNotFoundException("location");
		}
		return location;
	}
	
	public void setLocation(Location location) {
		this.hasLocation = true;
		this.location = location;
	}
	
	public void clearLocation() {
		this.hasLocation = false;
		this.location = null;
	}

	@Override
	public boolean hasSender() {
		return hasSender;
	}

	@Override
	public String getSender() {
		if(!hasSender) {
			throw new MessageAttributeNotFoundException("sender");
		}
		return sender;
	}
	
	public void setSender(String sender) {
		this.hasSender = true;
		this.sender = sender;
	}
	
	public void clearSender() {
		this.hasSender = false;
		this.sender = null;
	}
}
