package sate.cybersentinel.message;

public class DefaultAttributeSet implements AttributeSet {
	private static final long serialVersionUID = -8952680165355414486L;
	
	private boolean hasChannel;
	private boolean hasContents;
	private boolean hasTime;
	private boolean hasLocation;
	private boolean hasReceiverName;
	private boolean hasReceiverUUID;
	private boolean hasSenderName;
	private boolean hasSenderUUID;
	
	public DefaultAttributeSet(
			boolean hasContents, 
			boolean hasTime, 
			boolean hasLocation, 
			boolean hasReceiverName,
			boolean hasReceiverUUID,
			boolean hasSenderName,
			boolean hasSenderUUID) {
		this.hasContents = hasContents;
		this.hasTime = hasTime;
		this.hasLocation = hasLocation;
		this.hasReceiverName = hasReceiverName;
		this.hasReceiverUUID = hasReceiverUUID;
		this.hasSenderName = hasSenderName;
		this.hasSenderUUID = hasSenderUUID;
	}
	
	@Override
	public boolean hasChannel() {
		return hasChannel;
	}
	
	@Override
	public boolean hasContents() {
		return hasContents;
	}

	@Override
	public boolean hasTime() {
		return hasTime;
	}

	@Override
	public boolean hasLocation() {
		return hasLocation;
	}
	
	@Override
	public boolean hasReceiverName() {
		return this.hasReceiverName;
	}
	
	@Override
	public boolean hasReceiverUUID() {
		return this.hasReceiverUUID;
	}

	@Override
	public boolean hasSenderName() {
		return hasSenderName;
	}
	
	@Override
	public boolean hasSenderUUID() {
		return this.hasSenderUUID;
	}
}
