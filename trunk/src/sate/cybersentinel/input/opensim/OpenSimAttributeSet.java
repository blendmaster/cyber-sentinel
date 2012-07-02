package sate.cybersentinel.input.opensim;

import sate.cybersentinel.message.AttributeSet;

public class OpenSimAttributeSet implements AttributeSet {
	private static final long serialVersionUID = 4851448944884257076L;

	@Override
	public boolean hasChannel() {
		return true;
	}

	@Override
	public boolean hasContents() {
		return true;
	}

	@Override
	public boolean hasLocation() {
		return true;
	}

	@Override
	public boolean hasSenderName() {
		return true;
	}
	
	@Override
	public boolean hasSenderUUID() {
		return true;
	}
	
	@Override
	public boolean hasReceiverName() {
		return false;
	}
	
	@Override
	public boolean hasReceiverUUID() {
		return false;
	}
	
	@Override
	public boolean hasTime() {
		return true;
	}
}
