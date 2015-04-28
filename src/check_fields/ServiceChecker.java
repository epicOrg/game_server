package check_fields;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.UserNotOnlineException;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class ServiceChecker {

	protected OnlineManager onlineManager = OnlineManager.getInstance();
	protected JSONObject errors = new JSONObject();
	protected boolean noErrors = true;

	public boolean noErrors() {
		return noErrors;
	}

	public void addError() {
		noErrors = false;
	}

	public JSONObject getErrors() {
		return errors;
	}

	/**
	 * @param username
	 *            username of the user
	 * @param hashCode
	 *            hashcode of the user
	 * @return true if the hashCode sent by the user corresponds to the hashCode
	 *         generated and saved by the server in the login, false otherwise
	 * @throws UserNotOnlineException
	 */
	public boolean checkHashCode(String username, int hashCode) {

		try {

			if (hashCode == onlineManager.getHashCodeByUsername(username))
				return true;

		} catch (UserNotOnlineException e) {
			isUserOnline(username);
			e.printStackTrace();
		}

		try {
			errors.put(FieldsNames.HASHCODE, FieldsNames.INVALID);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return noErrors = false;
	}

	/**
	 * Check if the user is online.
	 * 
	 * @param username
	 *            the username of the user
	 * @return true if the username is online, false otherwise
	 */
	public boolean isUserOnline(String username) {
		try {
			if (!onlineManager.checkIfOnline(username)) {
				errors.put(FieldsNames.USERNAME, FieldsNames.OFFLINE);
				return noErrors = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	public OnlineUser getOnlineUser(String username)
			throws UserNotOnlineException {
		return onlineManager.getOnlineUserByUsername(username);
	}

}
