package sate.cybersentinel.input;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InputListener {
	public static final int DEFAULT_PORT = 275;
	
	private ServerSocket server;
	
	public InputListener() {
		this(DEFAULT_PORT);
	}
	
	public InputListener(int port) {
		try {
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		for(;;) {
			Socket client = null;
			try {
				client = this.server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Thread t = new ClientThread(client);
			t.start();
		}
	}
}
