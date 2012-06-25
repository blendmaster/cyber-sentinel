package sate.cybersentinel.input.irc;

public class Main {
	public static void main(String[] args) {
		Connection c = new Connection("cybersen", "irc.freenode.net");
		c.start();
		c.join("#cybersentinel");
		c.waitForInput();
	}
}
