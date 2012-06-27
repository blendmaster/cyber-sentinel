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
	public boolean hasTime() {
		return true;
	}

	@Override
	public boolean hasLocation() {
		return true;
	}

	@Override
	public boolean hasSender() {
		return true;
	}
}
