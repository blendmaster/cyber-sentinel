package sate.cybersentinel.input.opensim;

import sate.cybersentinel.message.AttributeSet;

public class OpenSimPrivateChatAttributeSet implements AttributeSet {

	@Override
	public boolean hasChannel() {
		return false;
	}

	@Override
	public boolean hasContents() {
		return true;
	}

	@Override
	public boolean hasTime() {
		return true;
	}

	@Override
	public boolean hasLocation() {
		return false;
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
		return true;
	}

	@Override
	public boolean hasReceiverUUID() {
		return true;
	}
}
