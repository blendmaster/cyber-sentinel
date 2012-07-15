package sate.cybersentinel.input.opensim;

import sate.cybersentinel.message.AttributeSet;

public class OpenSimGlobalChatAttributeSet implements AttributeSet {
	private static final long serialVersionUID = 2038392533740524171L;

	@Override
	public boolean hasChannel() {
		return true;
	}

	@Override
	public boolean hasContents() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasTime() {
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
}
