package sate.cybersentinel.message.filter;

import sate.cybersentinel.message.AttributeSet;
import sate.cybersentinel.message.Message;

public class AttributeFilter extends FilterBase {
	private AttributeSet attributes;
	
	public AttributeFilter(AttributeSet attributes) {
		this.attributes = attributes;
	}

	@Override
	protected boolean check(Message message) {
		return message.conformsTo(attributes);
	}
}
