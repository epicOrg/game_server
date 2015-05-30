package online_management;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import services.Login;
import exceptions.UserNotOnlineException;

/**
 * This class is responsible for the online users managing. A client login
 * message is decoded by the {@link Login} service who, with its method
 * <code>setUserOnline</code>, calls the <code>setOnline</code> method of this
 * class. Then the user is added to <code>onlineUsers</code> HashMap, that
 * contains all the online users at any moment.
 * 
 * @author Noris
 * @date 2015/03/30
 * @see OnlineUser
 */

public class OnlineManager {

	private static OnlineManager onlineManager = new OnlineManager();
	private HashMap<String, OnlineUser> onlineUsers = new HashMap<String, OnlineUser>();
	private HashMap<String, PollingThread> pollingTreads = new HashMap<>();

	private OnlineManager() {
	}

	/**
	 * @return the instance of onlineManager
	 */
	public static OnlineManager getInstance() {
		return onlineManager;
	}

	/**
	 * Set the user online and starts the {@link PollingThread}.
	 * 
	 * @return user's hashCode
	 * @see OnlineUser
	 */
	public int setOnline(String username, InetAddress ipAddress, PrintWriter printWriter) {

		OnlineUser onlineUser = new OnlineUser(username, ipAddress);
		PollingThread pollingThread = new PollingThread(onlineUser);
		pollingThread.start();
		pollingTreads.put(username, pollingThread);
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
		
		if (getHashCodeByUsername(username) == hashCode){
			pollingTreads.get(username).shutdown();
			pollingTreads.remove(username);
			onlineUsers.remove(username);
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
	public int getOnlineUsersNumber() {
		return onlineUsers.size();
	}

	/**
	 * @param username
	 * @return the {@link OnlineUser} instance object of the user
	 * @throws UserNotOnlineException
	 */
	public OnlineUser getOnlineUserByUsername(String username) throws UserNotOnlineException {
		if (!checkIfOnline(username))
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
		if (!checkIfOnline(username))
			throw new UserNotOnlineException();
		return onlineUsers.get(username).hashCode();
	}

	/**
	 * @param hashCode
	 * @return the username of the user
	 */
	public String getUsernameByHashCode(int hashCode) {
		for (Map.Entry<String, OnlineUser> entry : onlineUsers.entrySet()) {
			if (entry.getValue().hashCode() == hashCode)
				return entry.getValue().getUsername();
		}
		return null;
	}
}
