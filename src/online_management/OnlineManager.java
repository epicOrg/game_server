package online_management;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Noris
 * @since  2015/03/30
 */

public class OnlineManager {

	private static OnlineManager onlineManager = new OnlineManager();
	private static int onlineUsersNumber = 0;

	private HashMap<String, OnlineUser> onlineUsers = new HashMap<String, OnlineUser>();

	/**
	 * @return the instance of onlineManager
	 */
	public static OnlineManager getInstance() {
		return onlineManager;
	}

	/**
	 * Set the user online.
	 * 
	 * @return hashCode
	 */
	public int setOnline(String username, InetAddress ipAddress) {
		onlineUsers.put(username, new OnlineUser(username, ipAddress));
		onlineUsersNumber++;
		return onlineUsers.get(username).hashCode();
	}

	/**
	 * Set the user offline.
	 * 
	 * @param username
	 * @param hashCode
	 */
	public void setOffline(String username, int hashCode) {
		if (getHashCodeByUsername(username) == hashCode) {
			onlineUsers.remove(username);
			onlineUsersNumber--;
		}
	}

	/**
	 * @param username
	 * @return true if the user is online, false otherwise
	 */
	public boolean checkIfOnline(String username) {
		return onlineUsers.containsKey(username);
	}

	/**
	 * @return the number of online users
	 */
	public static int getOnlineUsersNumber() {
		return onlineUsersNumber;
	}

	/**
	 * @param username
	 * @return IP Address of the user
	 */
	public InetAddress getIpAddressByUsername(String username) {
		return onlineUsers.get(username).getIpAddress();
	}

	/**
	 * 
	 * @param username
	 * @return hashCode of the user
	 */
	public int getHashCodeByUsername(String username) {
		return onlineUsers.get(username).hashCode();
	}

	/**
	 * @param hashCode
	 * @return username
	 */
	public String getUsernameByHashCode(int hashCode) {
		for (Map.Entry<String, OnlineUser> entry : onlineUsers.entrySet()) {
			if (entry.getValue().hashCode() == hashCode())
				return entry.getKey();
		}
		return null;
	}

}
