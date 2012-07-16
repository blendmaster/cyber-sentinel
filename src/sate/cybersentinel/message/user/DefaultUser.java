package sate.cybersentinel.message.user;

public class DefaultUser implements User {
	private String uuid;
	private String name;
	
	public DefaultUser(String uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	@Override
	public int compareTo(User other) {
		return this.uuid.compareTo(other.getUUID());
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getName() {
		return this.name;
	}
}
