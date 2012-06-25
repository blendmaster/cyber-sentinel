package sate.cybersentinel.input.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputThread extends Thread {
	private BufferedReader reader;
	
	public InputThread(Socket socket) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			for(;;) {
				String l = reader.readLine();
				if(l == null) {
					System.err.println("Something went really wrong with the IRC connection");
					System.exit(0);
				}
				System.out.println(l);
				
				int i = l.indexOf("PRIVMSG");
				if(i != -1) {
					String s = l.substring(i + 8);
					String[] split = s.split(":");
					String text = split[1];
					System.out.println("---- " + text);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
