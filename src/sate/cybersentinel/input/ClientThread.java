package sate.cybersentinel.input;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import sate.cybersentinel.message.AttributeSet;
import sate.cybersentinel.message.Message;

public class ClientThread extends Thread {
	private Socket client;
	
	public ClientThread(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		Deque<Message> messageQueue = new ArrayDeque<>();
		
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(client.getInputStream());

			Session session = (Session) in.readObject();
			AttributeSet attributeSet = session.getAttributeSet();
			
			for(;;) {
				Block block = (Block) in.readObject();
				if(block.getSignal() == Block.Signal.TERMINATE) {
					// TODO Run Analysis
					break;
				}
				else {
					Collection<Message> messages = block.getMessages();
					for(Message m : messages) {
						if(m.conformsTo(attributeSet)) {
							messageQueue.add(m);
						}
						else {
							System.err.println("Error: Message does not conform to attribute set!");
						}
					}
				}
			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
