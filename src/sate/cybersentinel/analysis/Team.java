package sate.cybersentinel.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Team {
	public String name;
	public Set<String> members;
	public String lead;
	
	public Team(String name, String lead, Set<String> hashSet) {
		this.name = name;
		this.members = hashSet;
		this.lead = lead;
	}
	
	public String toString() {
		Map<String, String> m = new HashMap<>();
		m.put("name", name);
		m.put("members", members.toString());
		m.put("lead", lead);
		return m.toString();
	}
}
