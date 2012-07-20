package sate.cybersentinel.message.user;

public class DefaultUser implements User {
	private String uuid;
	private String name;
	
	public DefaultUser(String uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof User) {
			return this.uuid.equals(((User) other).getUUID());
		}
		return false;
	}

	@Override
	public int compareTo(User other) {
		return this.uuid.compareTo(other.getUUID());
	}
	
	@Override
	public int hashCode() {
		return this.uuid.hashCode();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getName() {
		return this.name;
	}
}
