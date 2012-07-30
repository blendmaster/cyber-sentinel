package sate.cybersentinel.analysis;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TeamList {
	private List<Team> teams;
	
	public TeamList() {
		this.teams = new ArrayList<>();
		this.teams.add(new Team("STS3", "Priya Chawla", Sets.newHashSet(new String[] {
			"Priya Chawla", "Sharlene Dong", "Luke Wunderlich", "Eliza Straughter",
			"Tyler Maschino"
		})));
		this.teams.add(new Team("Guardian Angel", "Matt McCartney", Sets.newHashSet(new String[] {
			"Matt McCartney", "Evan Baker", "Zac Audette", "Michael Fox", "Matt Bauman",
			"Oliver Ceccopieri", "Nick Costa"
		})));
		this.teams.add(new Team("Swiss Army Knife", "Shane Smith", Sets.newHashSet(new String[] {
			"Shane Smith", "Ben Rhoads", "Megan Kramer", "Brady Schoeffler", "Brian Caldron",
			"Mike Zoller", "Michael Junker", "Elizabther Worsham", "Nathan Kidder"
		})));
		this.teams.add(new Team("Tube Launched QR", "Jeff Blanford", Sets.newHashSet(new String[] {
			"Jeff Blanford", "Jeremy Lee", "Nick Woodward", "Alex Kozhemiakov", "David Bian"
		})));
		this.teams.add(new Team("Tube Launched MAV", "Jeff Blanford", Sets.newHashSet(new String[] {
			"Jeff Blanford", "Eli Collinson", "Sean VandenAvond, Chris Sneeder"	
		})));
		this.teams.add(new Team("Holodeck", "Luke Stork", Sets.newHashSet(new String[] {
			"Luke Stork, Vilcya Wirantana", "Maulik Patel"	
		})));
		this.teams.add(new Team("Eagle Eye", "Robert Smayda", Sets.newHashSet(new String[] {
			"Robert Smayda", "Tayler White", "Keith Batesole", "Alex Meyer"	
		})));
		this.teams.add(new Team("VGC", "Mark Seibert", Sets.newHashSet(new String[] {
			"Mark Seibert", "Tyler Muryn", "Michael Craft", "Madalyn Sowar", "Judith Vargo",
			"Joseph Scupski", "Jared Hance", "Maureen Basil", "Alex Comer", "Keith Batesole",
			"Travis Tidball"
		})));
		this.teams.add(new Team("CASSI", "Tyler Moody", Sets.newHashSet(new String[] {
			"Tyler Moody", "Dan Bray", "Derek Chiders"	
		})));
		this.teams.add(new Team("3D Music Box", "Brian Breitsch", Sets.newHashSet(new String[] {
			"Brian Breitsch", "Max Mraz", "Nabil Rajabian-Schwart", "Dan Bray",
			"Lucas Boswell"	
		})));
		this.teams.add(new Team("Tricorder", "Tyler Feitshans", Sets.newHashSet(new String[] {
			"Tyler Feitshans", "Grave Crumrine", "Luke Miller", "Justin Trnavsky",
			"Nick Braun", "Eric Hermann", "Isaac Brewster", "Nathan Weinie", "Aditya Pujari",
			"Andrew Hittle", "Lucas Boswell", "Lujack Prater", "Ian Wolford", "Anmol Nigam"
		})));
		this.teams.add(new Team("OB1", "Lydia Divine", Sets.newHashSet(new String[] {
			"Lydian Divine", "Shivam Sharodia", "Alex Williams"	
		})));
		this.teams.add(new Team("DragonFire", "Jacob Banasek", Sets.newHashSet(new String[] {
			"Jacob Banasek", "Alex Moreland", "Bryan Rich", "Jason Hall"	
		})));
		this.teams.add(new Team("AVATAR", "Michael Duncan", Sets.newHashSet(new String[] {
			"Michael Duncan", "Jason Kohl", "Ashutosh Gupta", "Jamie Gantert", "Alex Rouse",
			"Brian Farrell", "William Griffin"
		})));
		this.teams.add(new Team("Humanoid Robotics", "John Dulin", Sets.newHashSet(new String[] {
			"John Dulin"	
		})));
		this.teams.add(new Team("Turtle Bot", "Mayank Ekbote", Sets.newHashSet(new String[] {
				"Mayank Ekbote"
		})));
		this.teams.add(new Team("TeleRobotics", "Nir Wiener", Sets.newHashSet(new String[] {
			"Nir Wiener", "T.J. Melanson", "Jennifer Yim", "Clark Dalton", "Jamez Ellis"	
		})));
		this.teams.add(new Team("Nanotech Academy", "David Brendel", Sets.newHashSet(new String[] {
			"David Brendel", "Dawnl Musil", "Katie Zellmer"	
		})));
		this.teams.add(new Team("Dan Lehman", "New Albany PTI", Sets.newHashSet(new String[] {
			"Dan Lehman", "Nate Lehman"	
		})));
		this.teams.add(new Team("SMART Care", "Brittany Fouts", Sets.newHashSet(new String[] {
			"Brittany Fouts", "Monica Lesli", "Chris Menart"
		})));
		this.teams.add(new Team("Pocket VDC", "James West", Sets.newHashSet(new String[] {
			"James West", "Aaron Baker", "Sarah-Ann Biount", "Justin Edse", "Jordan Komnick"	
		})));
		this.teams.add(new Team("DECA & MVCTC Projects", "Sterling Lutes", Sets.newHashSet(new String[] {
			"Sterling Lutes", "Chris Madison", "Corey Sobke"	
		})));
	}
	
	public List<Team> getTeams() {
		return this.teams;
	}
}
