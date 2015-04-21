package performance_tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class used by @see {@link Test02}. It send a message to the server.
 * 
 * @author Noris
 * @date 2015/04/21
 */

class Test_OpenSocket {

	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 7007;

	private Socket socket;
	private PrintWriter writer;

	public void start(String message) throws UnknownHostException, IOException {

		socket = new Socket();
		socket.connect(
				new InetSocketAddress(InetAddress.getByName(SERVER_ADDRESS),
						SERVER_PORT), 5000);
		writer = new PrintWriter(socket.getOutputStream(), true);

		writer.println(message);

		socket.close();
	}

}
