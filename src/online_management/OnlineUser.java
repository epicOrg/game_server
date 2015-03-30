package online_management;

import java.net.InetAddress;
import java.util.GregorianCalendar;

/**
 * @author	Noris
 * @since	2015-03-30
 */

public class OnlineUser {
	
	private String username;
	private InetAddress ipAddress;
	private GregorianCalendar connectionDate;
	
	public OnlineUser(String username, InetAddress ipAddress) {
		super();
		this.username = username;
		this.ipAddress = ipAddress;
		connectionDate = new GregorianCalendar();
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
	 * @return connection time in milliseconds
	 */
	public long getConnectionTime() {
		GregorianCalendar now = new GregorianCalendar();
		return now.getTimeInMillis() - connectionDate.getTimeInMillis();
	}

	@Override
	public int hashCode() {
		return username.hashCode() * ipAddress.hashCode() * connectionDate.hashCode();
	}

}
