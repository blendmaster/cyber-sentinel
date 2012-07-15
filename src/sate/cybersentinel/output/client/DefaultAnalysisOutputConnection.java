package sate.cybersentinel.output.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import sate.cybersentinel.output.protocol.AnalysisData;
import sate.cybersentinel.util.ImpossibleCodeExecutionException;
import sate.cybersentinel.util.ProtocolException;

public class DefaultAnalysisOutputConnection implements
		AnalysisOutputConnection {
	private SocketChannel channel;

	public DefaultAnalysisOutputConnection(SocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public AnalysisData receiveData() throws IOException {
		ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
		channel.read(lengthBuffer);
		lengthBuffer.flip();
		int length = lengthBuffer.getInt();

		ByteBuffer buffer = ByteBuffer.allocate(length);
		channel.read(buffer);
		buffer.flip();
		byte[] bytes = buffer.array();

		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteInputStream);

		try {
			Object object = objectInputStream.readObject();

			if (object instanceof AnalysisData) {
				return (AnalysisData) object;
			} else {
				throw new ProtocolException(
						"Received object that was not AnalysisData");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

			throw new ImpossibleCodeExecutionException();
		}
	}
}
