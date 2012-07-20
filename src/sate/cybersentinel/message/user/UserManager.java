package sate.cybersentinel.message.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sate.cybersentinel.message.Message;

public final class UserManager {
	private UserManager() { }
	
	private static Map<String, User> userMap = new HashMap<String, User>();
	
	/**
	 * Gets the user with a specified uuid. If no such user currently exists in the
	 * collection of users, a new one is added.
	 * @param uuid The UUID of the user
	 * @param name The name to be used in the case that a new user is created
	 * @return
	 */
	public static User getUserOrCreate(String uuid, String name) {
		if(userMap.containsKey(uuid)) {
			return userMap.get(uuid);
		}
		else {
			User user = new DefaultUser(uuid, name);
			userMap.put(uuid, user);
			return user;
		}
	}
	
	public static User getUser(String uuid) {
		return userMap.get(uuid);
	}
	
	public static Set<User> getAllUsers() {
		Set<User> s = new HashSet<User>();
		for(User u : userMap.values()) {
			s.add(u);
		}
		
		return s;
	}
	
	public static Set<String> getAllUUIDs() {
		return userMap.keySet();
	}
	
	/**
	 * Ensures that all users are in the userMap for getAllUsers/getAllUUIDs
	 * @param messages
	 */
	public static void process(Iterable<Message> messages) {
		for(Message m : messages) {
			m.getUser();
			if(m.getAttributeSet().hasReceiverUUID()) {
				if(m.getAttributeSet().hasReceiverName()) {
					getUserOrCreate(m.getReceiverUUID(), m.getReceiverName());
				}
				else {
					getUserOrCreate(m.getReceiverUUID(), m.getReceiverUUID());
				}
			}
		}
	}
}
