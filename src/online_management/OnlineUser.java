package online_management;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.GregorianCalendar;

/**
 * This class is used to identifies an online user, that is an user connected to
 * the server. Every user has a different name from the others and, almost
 * certainly, a different HashCode. This code is generated in the login phase,
 * and it's used to check if a message sent by an user comes really by him.
 * 
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

	/**
	 * @return the date on which the user has logged in
	 */
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
		return username.hashCode() * ipAddress.hashCode() * connectionDate.hashCode();
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
