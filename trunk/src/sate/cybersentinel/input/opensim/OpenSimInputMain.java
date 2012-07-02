package sate.cybersentinel.input.opensim;

import java.net.UnknownHostException;

public class OpenSimInputMain {
	public static void main(String[] args) {
		try {
			Connection connection = new Connection();
			connection.start();
		} catch (UnknownHostException e) {
			System.out.println("Unable to connectto host.");
		}
	}
}
