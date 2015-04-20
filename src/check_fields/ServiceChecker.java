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
	protected JSONObject errors;

	public ServiceChecker(JSONObject errors) {
		this.errors = errors;
	}

	/**
	 * @param username
	 *            username of the user
	 * @param hashCode
	 *            hashcode of the user
	 * @return the OnlineUser object of the user if the hashCode sent by the
	 *         user corresponds to the hashCode generated and saved by the
	 *         server in the login, null otherwise
	 */
	public boolean checkHashCode(String username, int hashCode) {

		try {
			if (hashCode == onlineManager.getHashCodeByUsername(username))
				return true;
		} catch (UserNotOnlineException e1) {
			isUserOnline(username);
			e1.printStackTrace();
		}

		try {
			errors.put(FieldsNames.HASHCODE, FieldsNames.INVALID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean isUserOnline(String username) {
		try {
			if (!onlineManager.checkIfOnline(username)) {
				errors.put(FieldsNames.USERNAME, FieldsNames.OFFLINE);
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public OnlineUser getOnlineUser(String username) throws UserNotOnlineException{
		return onlineManager.getOnlineUserByUsername(username);
	}
}
