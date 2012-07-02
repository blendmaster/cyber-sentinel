package sate.cybersentinel.input;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import sate.cybersentinel.message.Message;

public class InputListener {
	public static final int DEFAULT_PORT = 275;
	
	private static final Logger logger = Logger.getLogger(InputListener.class.getName());

	private int port;

	public InputListener() {
		this(DEFAULT_PORT);
	}

	public InputListener(int port) {
		this.port = port;
		logger.config("Port = " + port);
	}

	public void listen() {
		try (ServerSocketChannel server = ServerSocketChannel.open()) {
			server.socket().bind(new InetSocketAddress(port));
			server.configureBlocking(false);
			logger.info("Server started bound to port " + port);
			logger.fine("Server blocking = " + server.isBlocking());

			Selector selector = Selector.open();
			SelectionKey serverKey = server.register(selector,
					SelectionKey.OP_ACCEPT);

			for (;;) {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();

				for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
					SelectionKey key = i.next();
					i.remove();

					if (key == serverKey && key.isAcceptable()) {
						logger.info("Selector indicates that a client is ready to be accepted");
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						logger.info("Found a new client at address: " + client.getRemoteAddress());

						SelectionKey clientKey = client.register(selector,
								SelectionKey.OP_READ);
						clientKey.attach(ClientState.sessionLength());
					} else if (key.isReadable()) {
						handleKey(key);
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void handleKey(SelectionKey key)
			throws IOException, ClassNotFoundException {
		SocketChannel client = (SocketChannel) key.channel();
		ClientState state = (ClientState) key.attachment();
		int length;
		
		logger.info("Selector indicates that data can be read from address: " + client.getRemoteAddress());

		switch (state.getReceptionType()) {
		case BLOCK:
			length = state.getLength();
			Block block = (Block) receiveObject(client, length);
			
			if (block == null) {
				client.close();
				key.cancel();
			} else {
				for(Message message : block.getMessages()) {
					if(!message.conformsTo(state.getSession().getAttributeSet())) {
						System.err.println("Warning, invalid message, ignoring: " + message);
					}
				}
				System.out.println(block.getMessages());
				key.attach(ClientState.blockLength(state.getSession()));
			}

			break;
		case BLOCK_LENGTH:
			length = receiveLength(client);
			
			if (length == -1) {
				client.close();
				key.cancel();
			} else {
				key.attach(ClientState.block(state.getSession(), length));
			}

			break;
		case SESSION:
			length = state.getLength();
			Session session = (Session) receiveObject(client, length);
			if (session == null) {
				client.close();
				key.cancel();
			} else {
				key.attach(ClientState.blockLength(session));
			}

			break;
		case SESSION_LENGTH:
			length = receiveLength(client);
			if (length == -1) {
				client.close();
				key.cancel();
			} else {
				key.attach(ClientState.session(length));
			}

			break;
		}
	}

	private int receiveLength(SocketChannel client) throws IOException {
		ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

		int bytesRead = client.read(lengthBuffer);
		if (bytesRead != 4) {
			System.err
					.println("Couldn't read length from client, closing connection.");
			return -1;
		}

		return lengthBuffer.getInt();
	}

	private Object receiveObject(SocketChannel client, int length)
			throws IOException, ClassNotFoundException {
		ByteBuffer buffer = ByteBuffer.allocate(length);

		int bytesRead = client.read(buffer);
		if (bytesRead == -1) {
			System.err
					.println("Couldn't read any data from client, closing connection.");
			return null;
		}

		if (bytesRead < length) {
			System.err
					.println("Didn't read enough bytes, continuing anyway...");
		}

		byte[] bytes = new byte[bytesRead];
		buffer.put(bytes);
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteInputStream);
		return objectInputStream.readObject();
	}
}
