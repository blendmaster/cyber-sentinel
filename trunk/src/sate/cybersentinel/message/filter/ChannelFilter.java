package sate.cybersentinel.message.filter;

import sate.cybersentinel.message.Message;

public class ChannelFilter extends FilterBase {
	/**
	 * If a 'Message' has a channel, it should probably be nonzero. In almost all cases,
	 * a nonzero channel indicates that scripts are communicating rather than people, so
	 * we should probably ignore them.
	 * 
	 * @param message The message to check
	 */
	@Override
	protected boolean check(Message message) {
		return !(message.getAttributeSet().hasChannel() && message.getChannel() == 0);
	}
}
