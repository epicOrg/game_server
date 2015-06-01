package check_fields;

import online_management.OnlineManager;
import online_management.OnlineUser;
import services.Login;
import exceptions.UserNotOnlineException;

/**
 * <code>ClientIdentityCecker</code> checks the user real identity according to
 * his username and his hascode, that the user must send every time in his
 * request.
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/04/19
 * @see Login
 * @see OnlineManager
 * @see OnlineUser
 */

public class ClientIdentityCecker {

	private OnlineManager onlineManager = OnlineManager.getInstance();

	/**
	 * Check if the hashCode given matches with one provided during the login.
	 * 
	 * @param username
	 *            username of the user
	 * @param hashCode
	 *            hashcode of the user
	 * 
	 * @return true if the hashCode corresponds to the one saved in the server,
	 *         false otherwise
	 * @throws UserNotOnlineException
	 *             if the user doesn't result to be online
	 * @see OnlineManager#getHashCodeByUsername(String)
	 */
	public boolean checkHashCode(String username, int hashCode) {

		try {

			if (hashCode == onlineManager.getHashCodeByUsername(username))
				return true;

		} catch (UserNotOnlineException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	/**
	 * Check if the user is online.
	 * 
	 * @param username
	 *            the username of the user
	 * 
	 * @return true if the user is online, false otherwise
	 * @see OnlineManager#checkIfOnline(String)
	 */
	public boolean isUserOnline(String username) {
		
		return onlineManager.checkIfOnline(username);
	}
}
