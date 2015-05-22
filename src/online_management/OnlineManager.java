package online_management;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import exceptions.UserNotOnlineException;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class OnlineManager {

	private static OnlineManager onlineManager = new OnlineManager();
	private HashMap<String, OnlineUser> onlineUsers = new HashMap<String, OnlineUser>();
	
	private OnlineManager(){}

	/**
	 * @return the instance of onlineManager
	 */
	public static OnlineManager getInstance() {
		return onlineManager;
	}

	/**
	 * Set the user online and starts the {@link PollingThread}
	 * 
	 * @return 			user's hashCode
	 * @see OnlineUser
	 */
	public int setOnline(String username, InetAddress ipAddress, PrintWriter printWriter) {
		OnlineUser onlineUser = new OnlineUser(username, ipAddress);
		PollingThread pollingThread = new PollingThread(onlineUser);
		//pollingThread.start();
		onlineUser.setOutStream(printWriter);
		onlineUsers.put(username, onlineUser);
		
		return onlineUsers.get(username).hashCode();
	}

	/**
	 * Set the user offline.
	 * 
	 * @param username
	 * @param hashCode
	 * @throws UserNotOnlineException 
	 */
	public void setOffline(String username, int hashCode) throws UserNotOnlineException {
		if (getHashCodeByUsername(username) == hashCode)
			onlineUsers.remove(username);
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
	public int getOnlineUsersNumber() {
		return onlineUsers.size();
	}

	/**
	 * @param username
	 * @return the OnlineUser instance object of the user
	 * @throws UserNotOnlineException 
	 */
	public OnlineUser getOnlineUserByUsername(String username) throws UserNotOnlineException {
		if(!checkIfOnline(username))
			throw new UserNotOnlineException();
		return onlineUsers.get(username);
	}

	/**
	 * @param username
	 * @return IP Address of the user
	 */
	public InetAddress getIpAddressByUsername(String username) {
		return onlineUsers.get(username).getIpAddress();
	}

	/**
	 * @param username
	 * @return hashCode of the user
	 * @throws UserNotOnlineException 
	 */
	public int getHashCodeByUsername(String username) throws UserNotOnlineException {
		if(!checkIfOnline(username))
			throw new UserNotOnlineException();
		return onlineUsers.get(username).hashCode();
	}

	/**
	 * @param hashCode
	 * @return username
	 */
	public String getUsernameByHashCode(int hashCode) {
		for (Map.Entry<String, OnlineUser> entry : onlineUsers.entrySet()) {
			if (entry.getValue().hashCode() == hashCode)
				return entry.getValue().getUsername();
		}
		return null;
	}
}
