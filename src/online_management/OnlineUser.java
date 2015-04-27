package online_management;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.GregorianCalendar;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class OnlineUser {

	private String username;
	private InetAddress ipAddress;
	private int port;
	private PrintWriter writer;
	private GregorianCalendar connectionDate;

	public OnlineUser(String username, InetAddress ipAddress,int port) {
		super();
		this.username = username;
		this.ipAddress = ipAddress;
		this.port = port;
		connectionDate = new GregorianCalendar();
	}

	public String getUsername() {
		return username;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}
	
	public int getPort() {
		return port;
	}

	public GregorianCalendar getConnectionDate() {
		return connectionDate;
	}

	/**
	 * @return the connection time in milliseconds
	 */
	public long getConnectionTime() {
		GregorianCalendar now = new GregorianCalendar();
		return now.getTimeInMillis() - connectionDate.getTimeInMillis();
	}

	/**
	 * @return a different value for every online user
	 */
	@Override
	public int hashCode() {
		return username.hashCode() * ipAddress.hashCode()
				* connectionDate.hashCode();
	}

	public void setOutStream(PrintWriter printWriter) {
		this.writer = printWriter;		
	}
	
	public PrintWriter getOutStream() {
		return writer;
	}

}
