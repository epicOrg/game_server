package voip;

import java.io.IOException;
import java.net.ServerSocket;

public class NetUtils {
	
	public static final String MY_IP = "10.42.01";

	public static int findFreePort() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);

			int port = socket.getLocalPort();
			try {

				socket.close();

			} catch (IOException e) {
				// Ignore IOException on close()
			}
			return port;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new IllegalStateException(
				"Could not find a free TCP/IP port to start embedded Jetty HTTP Server on");
	}
}
