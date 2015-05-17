package voip;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author Luca
 * @date 2015/04/28
 */

public class NetUtils {

	public static final String MY_IP = "10.42.0.1";
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
				e.printStackTrace();
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
		throw new IllegalStateException("Could not find a free TCP/IP port");
	}

	public static void releasePort(int port) {
		Integer portOjb = new Integer(port);
		inUse.remove(portOjb);
	}

	public static String getIpByInetAddress(InetAddress address) {

		return address.getHostAddress();

	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
					.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
						.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

}
