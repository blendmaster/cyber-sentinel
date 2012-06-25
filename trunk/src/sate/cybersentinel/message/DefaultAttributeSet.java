package sate.cybersentinel.message;

public class DefaultAttributeSet implements AttributeSet {
	private static final long serialVersionUID = -8952680165355414486L;
	
	private boolean hasContents;
	private boolean hasTime;
	private boolean hasLocation;
	private boolean hasSender;
	
	public DefaultAttributeSet(
			boolean hasContents, 
			boolean hasTime, 
			boolean hasLocation, 
			boolean hasSender) {
		this.hasContents = hasContents;
		this.hasTime = hasTime;
		this.hasLocation = hasLocation;
		this.hasSender = hasSender;
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
	public boolean hasSender() {
		return hasSender;
	}
}
