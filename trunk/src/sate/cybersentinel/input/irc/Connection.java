package sate.cybersentinel.input.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;

import sate.cybersentinel.input.InputListener;
import sate.cybersentinel.input.Session;
import sate.cybersentinel.message.AttributeSet;

public class Connection {
	public static final int DEFAULT_PORT = 6667;
	public static final int OFFICIAL_PORT = 194;
	
	private String hostname;
	private String nickname;
	private int port;
	
	private String password;
	
	private Socket socket;
	private PrintWriter writer;
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
			
			command("PASS " + password);
			command("NICK " + nickname);
			command("USER " + nickname + " 0 * :cybersentinel bot");
			
			//Socket analysisSocket = new Socket("localhost", InputListener.DEFAULT_PORT);
			AttributeSet attributes = new AttributeSet() {
				private static final long serialVersionUID = 5163806881113186316L;
				
				@Override
				public boolean hasChannel() {
					return false;
				}
				
				@Override
				public boolean hasContents() {
					return true;
				}
				
				@Override
				public boolean hasLocation() {
					return false;
				}
				
				@Override
				public boolean hasSenderName() {
					return false;
				}
				
				@Override
				public boolean hasSenderUUID() {
					return true;
				}
				
				@Override
				public boolean hasReceiverName() {
					return false;
				}
				
				@Override
				public boolean hasReceiverUUID() {
					return false;
				}
				
				@Override
				public boolean hasTime() {
					return true;
				}
			};
			
			Session session = new Session("irc", attributes, "", 0);
			//new ObjectOutputStream(analysisSocket.getOutputStream()).writeObject(session);
			
			thread = new InputThread(socket, /*analysisSocket*/ null);
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
