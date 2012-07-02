package sate.cybersentinel.input.opensim;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class OpenSimInputMain {
	private static final Logger logger = Logger.getLogger(OpenSimInputMain.class.getName());
	
	public static void main(String[] args) {
		try(InputStream in = new FileInputStream("logging.properties")) {
			LogManager.getLogManager().readConfiguration(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Booting up OpenSimInputMain");
		logger.config("String[] args = " + Arrays.toString(args));
		
		String hostname = Connection.DEFAULT_HOSTNAME;
		int port = Connection.DEFAULT_PORT;
		if(args.length >= 1) {
			hostname = args[0];
		}
		if(args.length >= 2) {
			port = Integer.parseInt(args[1]);
		}
		
		try {
			Connection connection = new Connection(hostname, port);
			connection.start();
		} catch (UnknownHostException e) {
			logger.severe("Unable to connect to host.");
		}
	}
}
