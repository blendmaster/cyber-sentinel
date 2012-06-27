package sate.cybersentinel.input.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sate.cybersentinel.input.Block;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.MutableMessage;

public class InputThread extends Thread {
	/** Reads from IRC */
	private BufferedReader reader;
	/** Writes to IRC */
	private PrintWriter writer;
	
	/** Writes to Analysis */
	private ObjectOutputStream output;
	
	public InputThread(Socket socket, Socket analysisSocket) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//output = new ObjectOutputStream(analysisSocket.getOutputStream());
	}
	
	@Override
	public void run() {
		try {
			List<Message> messages = new ArrayList<Message>();
			for(;;) {
				String l = reader.readLine();
				if(l == null) {
					System.err.println("Something went really wrong with the IRC connection");
					System.exit(0);
				}
				System.out.println(l);
				
				int i = l.indexOf("PRIVMSG");
				if(i >= 0) {
					String s = l.substring(i + 8);
					int i2 = s.indexOf(":");
					if(i2 < 0) {
						continue;
					}
					
					String text = s.substring(i2 + 1);
					int i3 = l.indexOf("!");
					if(i3 < 0) {
						continue;
					}
					
					String sender = l.substring(1, i3);
					Date time = new Date();
					
					MutableMessage m = new MutableMessage();
					m.setContents(text);
					m.setSender(sender);
					m.setTime(time);
					messages.add(m);
					
					if(messages.size() == 10) {
						List<Message> old = messages;
						messages = new ArrayList<>();
						
						Block block = Block.messages(old);
						//output.writeObject(block);
					}
					
					System.out.println("-- " + sender + " -- " + text);
				}
				
				i = l.indexOf("PING");
				if(i >= 0) {
					String daemon = l.substring(i + 5);
					System.out.println("><><> " + daemon);
					writer.write("PONG " + daemon + "\r\n");
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
