package check_fields;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class ServiceChecker {
	
	private OnlineManager onlineManager = OnlineManager.getInstance();
	private JSONObject jsonResponse;
	
	public ServiceChecker(JSONObject jsonResponse) {
		this.jsonResponse = jsonResponse;
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
	public OnlineUser checkHashCode(String username, int hashCode) {
		try {

			if (!onlineManager.checkIfOnline(username)) {
				jsonResponse.put(FieldsNames.USERNAME, FieldsNames.OFFLINE);
				return null;
			}

			if (hashCode == onlineManager.getHashCodeByUsername(username))
				return onlineManager.getOnlineUserByUsername(username);

			jsonResponse.put(FieldsNames.HASHCODE, FieldsNames.INVALID);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
