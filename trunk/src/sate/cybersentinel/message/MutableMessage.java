package sate.cybersentinel.message;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import sate.cybersentinel.message.user.User;
import sate.cybersentinel.message.user.UserManager;
import sate.cybersentinel.util.ImpossibleCodeExecutionException;

/**
 * Used to create a Message. In contrast to Message, which has `get` method for
 * each attribute, this class also has a `set` and `clear`. `clear` simply sets
 * the `has` field for the attribute to false.
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

	protected boolean hasReceiverName = false;
	protected String receiverName;

	protected boolean hasReceiverUUID = false;
	protected String receiverUUID;

	protected boolean hasSenderName;
	protected String senderName;

	protected boolean hasSenderUUID = false;
	protected String senderUUID;

	protected boolean hasTime = false;
	protected Date time;
	
	private static Map<String, User> uuidMap;

	@Override
	public int getChannel() {
		if (!hasChannel) {
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
		if (!hasContents) {
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
		if (!hasLocation) {
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
	public String getReceiverName() {
		if (!this.hasReceiverName) {
			throw new MessageAttributeNotFoundException("receiverName");
		}
		return receiverName;
	}

	public void setReceiverName(String receiver) {
		this.hasReceiverName = true;
		this.receiverName = receiver;
	}

	public void clearReceiverName() {
		this.hasReceiverName = false;
		this.receiverName = null;
	}

	@Override
	public String getReceiverUUID() {
		if (!this.hasReceiverUUID) {
			throw new MessageAttributeNotFoundException("receiverUUID");
		}

		return this.receiverUUID;
	}

	public void setReceiverUUID(String receiverUUID) {
		this.hasReceiverUUID = true;
		this.receiverUUID = receiverUUID;
	}

	public void clearReceiverUUID() {
		this.hasReceiverUUID = false;
		this.receiverUUID = null;
	}

	@Override
	public String getSenderName() {
		if (!this.hasSenderName) {
			throw new MessageAttributeNotFoundException("senderName");
		}
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.hasSenderName = true;
		this.senderName = senderName;
	}

	public void clearSenderName() {
		this.hasSenderName = false;
		this.senderName = null;
	}

	@Override
	public String getSenderUUID() {
		if (!this.hasSenderUUID) {
			throw new MessageAttributeNotFoundException("senderUUID");
		}
		return this.senderUUID;
	}

	public void setSenderUUID(String senderUUID) {
		this.hasSenderUUID = true;
		this.senderUUID = senderUUID;
	}

	public void clearSenderUUID() {
		this.hasSenderUUID = false;
		this.senderUUID = null;
	}

	@Override
	public Date getTime() {
		if (!hasTime) {
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
			public boolean hasSenderName() {
				return hasSenderName;
			}

			@Override
			public boolean hasSenderUUID() {
				return hasSenderUUID;
			}

			@Override
			public boolean hasReceiverName() {
				return hasReceiverName;
			}

			@Override
			public boolean hasReceiverUUID() {
				return hasReceiverUUID;
			}

			@Override
			public boolean hasTime() {
				return hasTime;
			}
		};
	}

	@Override
	public boolean conformsTo(AttributeSet target) {
		return (!target.hasChannel() || this.hasChannel)
				&& (!target.hasContents() || this.hasContents)
				&& (!target.hasLocation() || this.hasLocation)
				&& (!target.hasSenderName() || this.hasSenderName)
				&& (!target.hasSenderUUID() || this.hasSenderUUID)
				&& (!target.hasReceiverName() || this.hasReceiverName)
				&& (!target.hasReceiverUUID() || this.hasReceiverUUID)
				&& (!target.hasTime() || this.hasTime);
	}

	@Override
	public String toString() {
		Map<String, Object> map = new TreeMap<String, Object>();

		if (hasChannel) {
			map.put("channel", channel);
		}
		if (hasContents) {
			map.put("contents", contents);
		}
		if (hasLocation) {
			map.put("location", location);
		}
		if (hasSenderName) {
			map.put("senderName", senderName);
		}
		if (hasSenderUUID) {
			map.put("senderUUID", senderUUID);
		}
		if (hasReceiverName) {
			map.put("receiverName", receiverName);
		}
		if (hasReceiverUUID) {
			map.put("receiverUUID", receiverUUID);
		}
		if (hasTime) {
			map.put("time", time);
		}

		return map.toString();
	}

	@Override
	/**
	 * Needs to be called at least once to add the message to the database.
	 */
	public User getUser() {
		if(hasSenderUUID && hasSenderName) {
			return UserManager.getUserOrCreate(senderUUID, senderName);
		}
		else if(hasSenderUUID) {
			return UserManager.getUserOrCreate(senderUUID, senderUUID);
		}
		else {
			throw new ImpossibleCodeExecutionException("Message doesn't have UUID. This is probably a bug.");
		}
	}

	@Override
	public int compareTo(Message o) {
            if(o == null)
                return -1;
            return o.getID() - this.getID();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
