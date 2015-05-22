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
	private PrintWriter writer;
	private GregorianCalendar connectionDate;
	private volatile boolean polled;

	public OnlineUser(String username, InetAddress ipAddress) {
		super();
		this.username = username;
		this.ipAddress = ipAddress;
		connectionDate = new GregorianCalendar();
		polled = true;
	}

	public String getUsername() {
		return username;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
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

	public boolean isPolled() {
		return polled;
	}

	public void setPolled(boolean polled) {
		this.polled = polled;
	}
}
