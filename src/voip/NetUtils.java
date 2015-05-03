package voip;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;

import javax.imageio.stream.ImageInputStream;

public class NetUtils {
	
	public static final String MY_IP = "10.42.01";
	private static ArrayList<Integer> inUse = new ArrayList<>();

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
			
			if (inUse.contains(port)) {
				return findFreePort();
			}
			inUse.add(port);
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
				"Could not find a free TCP/IP port");
	}
	
	public static void releasePort(int port){
		Integer portOjb = new Integer(port);
		inUse.remove(portOjb);
	}
}
