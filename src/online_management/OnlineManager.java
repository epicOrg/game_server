package online_management;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author	Noris
 * @since	2015-03-30
 */

public class OnlineManager {
	
	private static OnlineManager onlineManager = new OnlineManager();
	private static int onlineUsersNumber = 0;
	
	private ArrayList<OnlineUser> onlineUsers = new ArrayList<OnlineUser>();
	
	/**
	 * @return the instance of onlineManager
	 */
	public static OnlineManager getInstance() {
		return onlineManager;
	}

	/**
	 * Set the user online.
	 * 
	 * @return user hashCode
	 */
	public int setOnline(String username, InetAddress ipAddress) {
		onlineUsers.add(new OnlineUser(username, ipAddress));
		return onlineUsers.get(onlineUsersNumber++).hashCode();
	}
	
	/**
	 * Set the user offline.
	 * 
	 * @param user hashCode
	 */
	public void setOffline(int hashCode) {
		onlineUsers.remove(getIndexByHashCode(hashCode));
		onlineUsersNumber--;
	}
	
	/**
	 * @param username
	 * @return the hashCode of the user, or -1 if the user is not online
	 */
	public int getHashCodeByUsername(String username) {
		for (int i = 0; i < onlineUsers.size(); i++) {
			if ( onlineUsers.get(i).getUsername().equals(username) )
				return onlineUsers.get(i).hashCode();
		}
		return -1;
	}
	
	/**
	 * @param username
	 * @return the index of the user in the ArrayList, or -1 if the user is not online
	 */
	public int getIndexByUsername(String username) {
		for (int i = 0; i < onlineUsers.size(); i++) {
			if ( onlineUsers.get(i).getUsername().equals(username) )
				return i;
		}
		return -1;
	}
	
	/**
	 * @param hashCode
	 * @return the username of the user with hashCode, or null if the user is not online
	 */
	public String getUsernameByHashCode(int hashCode) {
		for (int i = 0; i < onlineUsers.size(); i++) {
			if ( onlineUsers.get(i).hashCode() == hashCode )
				return onlineUsers.get(i).getUsername();
		}
		return null;
	}
	
	/**
	 * @param hashCode
	 * @return the index of the user with hashCode in the ArrayListor, or -1 if
	 *         the user is not online
	 */
	public int getIndexByHashCode(int hashCode) {
		for (int i = 0; i < onlineUsers.size(); i++) {
			if ( onlineUsers.get(i).hashCode() == hashCode )
				return i;
		}
		return -1;
	}
	
}
