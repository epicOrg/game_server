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
	private GregorianCalendar connectionTime;
	
	public OnlineUser(String username, InetAddress ipAddress) {
		super();
		this.username = username;
		this.ipAddress = ipAddress;
		connectionTime = new GregorianCalendar();
	}	
	
	public String getUsername() {
		return username;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public GregorianCalendar getConnectionTime() {
		return connectionTime;
	}

	@Override
	public int hashCode() {
		return username.hashCode() * ipAddress.hashCode() * connectionTime.hashCode();
	}

}
