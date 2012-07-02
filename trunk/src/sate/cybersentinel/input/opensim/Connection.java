package sate.cybersentinel.input.opensim;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.xml.MessageXmlReader;

public class Connection {
	public static final String DEFAULT_HOSTNAME = "virtualdiscoverycenter.net";
	public static final int DEFAULT_PORT = 8019;
	
	private SocketAddress address;
	private CharsetDecoder decoder;
	private MessageXmlReader xmlr;
	
	private static final Logger logger = Logger.getLogger(Connection.class.getName());
	
	public Connection() throws UnknownHostException {
		this(DEFAULT_HOSTNAME, DEFAULT_PORT);
	}
	
	public Connection(String hostname) throws UnknownHostException {
		this(hostname, DEFAULT_PORT);
	}
	
	public Connection(int port) throws UnknownHostException {
		this(DEFAULT_HOSTNAME, port);
	}
	
	public Connection(String hostname, int port) throws UnknownHostException {
		this.address =
				new InetSocketAddress(InetAddress.getByName(hostname), port);
		this.decoder = Charset.forName("UTF-16").newDecoder();
		this.xmlr = new MessageXmlReader();
		logger.setLevel(Level.ALL);
	}
	
	public void start() {
		try(SocketChannel channel = SocketChannel.open()) {
			logger.info("Attempting to connect to address: " + address);
			channel.connect(address);
			logger.info("Successfully connected to address: " + address);
			logger.fine("Channel blocking = " + channel.isBlocking());
			for(;;) {
				ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
				channel.read(lengthBuffer);
				int length = lengthBuffer.getInt();
				logger.fine("About to receive message of length " + length);
				
				ByteBuffer buffer = ByteBuffer.allocate(length);
				channel.read(buffer);
				
				String xml = decoder.decode(buffer).toString();
				logger.fine("Received xml message: " + xml);
				
				Message message = xmlr.readMessage(xml);
				logger.info("Message received: " + message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
