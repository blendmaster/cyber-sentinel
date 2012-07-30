package sate.cybersentinel.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import sate.cybersentinel.analysis.Graph.GraphStatsCollection;
import sate.cybersentinel.message.user.User;

public class TeamAnalysis {
	private GraphStatsCollection stats;
	private List<Team> associatedTeams;
	private List<Set<User>> modularities;
	
	public TeamAnalysis(GraphStatsCollection stats) {
		this.stats = stats;
		
		List<Team> teams = new TeamList().getTeams(); 

		this.modularities = stats.getModularityCommunity();
		this.associatedTeams = new ArrayList<>(modularities.size());
		
		for(Set<User> users : modularities) {
			Set<String> names = new HashSet<>();
			for(User u : users) {
				names.add(u.getName());
			}
			double greatestIntersection = -1;
			Team bestTeam = null;
			
			for(Team team : teams) {
				int intersection = Sets.intersection(names, team.members).size();
				double weightedIntersection = intersection / team.members.size();
				if(weightedIntersection > greatestIntersection) {
					greatestIntersection = weightedIntersection;
					bestTeam = team;
				}
			}
			
			associatedTeams.add(bestTeam);
		}
	}
	
	public List<Team> getAssociatedTeams() {
		return associatedTeams;
	}
	
	public Map<Set<User>, Team> getAssociationMap() {
		Map<Set<User>, Team> result = new HashMap<>();
		for(int i = 0; i < associatedTeams.size(); i++) {
			result.put(modularities.get(i), associatedTeams.get(i));
		}
		return result;
	}
}
