package sate.cybersentinel.message;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Used to create a Message. In contrast to Message, which has `get` method
 * for each attribute, this class also has a `set` and `clear`. `clear` simply sets the
 * `has` field for the attribute to false.
 * 
 * @author Jared Hance
 */
public class MutableMessage implements Message {
	private static final long serialVersionUID = 5324243179213401252L;
	
	protected boolean hasChannel = false;
	protected int channel;
	
	protected boolean hasContents = false;
	protected String contents;
	
	protected boolean hasLocation = false;
	protected Location location;
	
	protected boolean hasReceiver = false;
	protected String receiver;
	
	protected boolean hasSender;
	protected String sender;

	protected boolean hasTime = false;
	protected Date time;
	
	@Override
	public int getChannel() {
		if(!hasChannel) {
			throw new MessageAttributeNotFoundException("channel");
		}
		return channel;
	}
	
	public void setChannel(int channel) {
		this.hasChannel = true;
		this.channel = channel;
	}
	
	public void clearChannel() {
		this.hasChannel = false;
		this.channel = 0;
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
	public String getReceiver() {
		if(!this.hasReceiver) {
			throw new MessageAttributeNotFoundException("receiver");
		}
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	public AttributeSet getAttributeSet() {
		return new AttributeSet() {
			private static final long serialVersionUID = 3862550727811323851L;

			@Override
			public boolean hasChannel() {
				return hasChannel;
			}
			
			@Override
			public boolean hasContents() {
				return hasContents;
			}
			
			@Override
			public boolean hasLocation() {
				return hasLocation;
			}
			
			@Override
			public boolean hasSender() {
				return hasSender;
			}
			
			@Override
			public boolean hasTime() {
				return hasTime;
			}
		};
	}

	@Override
	public boolean conformsTo(AttributeSet target) {
		return (!target.hasChannel()  || this.hasChannel)
			&& (!target.hasContents() || this.hasContents)
			&& (!target.hasLocation() || this.hasLocation)
			&& (!target.hasSender()   || this.hasSender)
			&& (!target.hasTime()     || this.hasTime);
	}
	
	@Override
	public String toString() {
		Map<String, Object> map = new TreeMap<String, Object>();
		
		if(hasChannel) {
			map.put("channel", channel);
		}
		if(hasContents) {
			map.put("contents", contents);
		}
		if(hasLocation) {
			map.put("location", location);
		}
		if(hasSender) {
			map.put("sender", sender);
		}
		if(hasTime) {
			map.put("time", time);
		}
		
		return map.toString();
	}
}
