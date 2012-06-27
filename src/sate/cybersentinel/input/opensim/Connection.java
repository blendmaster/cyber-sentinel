package sate.cybersentinel.input.opensim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.xml.MessageXmlReader;

public class Connection {
	public static final String DEFAULT_HOSTNAME = "virtualdiscoverycenter.net";
	public static final int DEFAULT_PORT = 8019;
	
	private String hostname;
	private int port;
	
	private MessageXmlReader xmlr;
	
	public Connection() {
		this(DEFAULT_HOSTNAME, DEFAULT_PORT);
	}
	
	public Connection(String hostname) {
		this(hostname, DEFAULT_PORT);
	}
	
	public Connection(int port) {
		this(DEFAULT_HOSTNAME, port);
	}
	
	public Connection(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		this.xmlr = new MessageXmlReader();
	}
	
	public void start() {
		try {
			Socket s = new Socket(hostname, port);
			InputStream inputStream = s.getInputStream();
			BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
			
			for(;;) {
				String xml = input.readLine();
				Message message = xmlr.readMessage(xml);
				System.out.println(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
