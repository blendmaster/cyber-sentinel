package sate.cybersentinel.input.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;

public class Connection {
	public static final int DEFAULT_PORT = 6667;
	public static final int OFFICIAL_PORT = 194;
	
	private String hostname;
	private String nickname;
	private int port;
	
	private String password;
	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private Thread thread;
	
	public Connection(String nickname, String hostname) {
		this(nickname, hostname, DEFAULT_PORT);
	}
	
	public Connection(String nickname, String hostname, int port) {
		this.hostname = hostname;
		this.nickname = nickname;
		this.port = port;
		
		this.password = new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public int getPort() {
		return port;
	}
	
	public void start() {
		try {
			socket = new Socket(hostname, port);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			command("PASS " + password);
			command("NICK " + nickname);
			command("USER " + nickname + " 0 * :cybersentinel bot");
			
			thread = new InputThread(socket);
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void command(String command) {
		writer.write(command + "\r\n");
		writer.flush();
		System.out.println("> " + command);
	}
	
	public void join(String channel) {
		command("JOIN " + channel);
	}
	
	public void waitForInput() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
