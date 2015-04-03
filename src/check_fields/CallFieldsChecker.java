package check_fields;

import java.net.InetAddress;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class CallFieldsChecker {

	private OnlineManager onlineManager = OnlineManager.getInstance();
	private JSONObject json;

	public CallFieldsChecker(JSONObject json) {
		this.json = json;
	}

	/**
	 * 
	 * @param username of the caller
	 * @param hashCode of the caller
	 * @return true if the hashCode sent by the user corresponds to the hashCode
	 *         generated and saved by the server in the login, false otherwise
	 */
	public boolean checkHashCode(String username, int hashCode) {
		if (hashCode == onlineManager.getHashCodeByUsername(username))
			return true;

		try {

			json.put(FieldsNames.HASHCODE, FieldsNames.INVALID);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @param username of the callee
	 * @return if the callee is online its IP Address, otherwise null
	 */
	public InetAddress checkIfCalleeIsOnline(String callee) {
		return onlineManager.getIpAddressByUsername(callee);
	}
}
