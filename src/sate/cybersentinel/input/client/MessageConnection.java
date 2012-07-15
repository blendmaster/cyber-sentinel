package sate.cybersentinel.input.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.logging.Logger;

import sate.cybersentinel.input.protocol.Block;
import sate.cybersentinel.input.protocol.Session;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.output.client.AnalysisOutputConnection;
import sate.cybersentinel.output.client.DefaultAnalysisOutputConnection;
import sate.cybersentinel.util.ProtocolException;

/**
 * A connection to the "gamma" server that does the analysis. Used
 * to send sessions and blocks to the analysis machine; the server complement to
 * this is InputListener.
 * 
 * This is intended to be used by all input drivers to send their session and their
 * messages.
 * 
 * Also handles the reception of output to be sent to the output driver.
 * 
 * Based on java.nio for efficiency. Blocks are sent by first encoding the length
 * as a 4-bit integer and then sending the block itself to the server.
 * 
 * @author Jared Hance
 */
public class MessageConnection {
	private static final Logger logger = Logger.getLogger(MessageConnection.class.getName());
	
	private SocketAddress address;
	private SocketChannel channel;
	
	private boolean sentSession = false;
	private boolean sentAbort = false;
	
	public MessageConnection(String hostname, int port) throws UnknownHostException {
		logger.config("Hostname = " + hostname);
		logger.config("Port = " + port);
		
		this.address = new InetSocketAddress(InetAddress.getByName(hostname), port);
	}
	
	public void connect() {
		logger.info("Attemping to connect to address: " + address);
		try {
			this.channel = SocketChannel.open(address);
		} catch (IOException e) {
			logger.severe("Failed to connect to address: " + address);
			e.printStackTrace();
		}
		logger.info("Connected to address: " + address);
	}
	
	private void sendObject(Serializable object) throws IOException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] bytes = byteOutputStream.toByteArray();
		int length = bytes.length;
		ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
		ByteBuffer buffer = ByteBuffer.allocate(length);
		
		lengthBuffer.putInt(4);
		lengthBuffer.flip();
		
		buffer.put(bytes);
		buffer.flip();
		
		channel.write(lengthBuffer);
		channel.write(buffer);
	}
	
	/**
	 * Sends a collection of messages in a single block.
	 * @param messages A collection of messages to send
	 * @throws IOException
	 */
	public void sendMessages(Collection<Message> messages) throws IOException {
		if(!sentSession) {
			throw new ProtocolException("Sent messages before session");
		}
		if(sentAbort) {
			throw new ProtocolException("Sent messages after abort");
		}
		
		Block block = Block.messages(messages);
		
		logger.info("Sending block: " + block);
		sendObject(block);
	}
	
	/**
	 * This method should be called once, and exactly once, before sending any
	 * messages.
	 * @param session
	 * @throws IOException 
	 */
	public void sendSession(Session session) throws IOException {
		if(sentSession) {
			throw new ProtocolException("Sent more than one session");
		}
		if(sentAbort) {
			throw new ProtocolException("Sent session after abort");
		}
		
		logger.info("Sending session: " + session);
		sendObject(session);
		sentSession = true;
	}
	
	/**
	 * Sends the done signal, telling the server to run the analysis.
	 */
	public void sendDone() throws IOException {
		if(!sentSession) {
			throw new ProtocolException("Sent done before sending session");
		}
		if(sentAbort) {
			throw new ProtocolException("Send done after sending abort");
		}
		
		sendObject(Block.done());
	}
	
	public void sendAbort(String message) throws IOException {
		if(sentAbort) {
			throw new ProtocolException("Sent abort more than once");
		}
		
		logger.warning("Sending abort: " + message);
		sentAbort = true;
		sendObject(Block.abort(message));
	}
}
