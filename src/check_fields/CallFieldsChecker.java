package check_fields;

import java.net.InetAddress;

import online_management.OnlineManager;
import online_management.OnlineUser;

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
	 * @param caller
	 *            username of the caller
	 * @param hashCode
	 *            hashcode of the caller
	 * @return the OnlineUser object of the user if the hashCode sent by the
	 *         user corresponds to the hashCode generated and saved by the
	 *         server in the login, null otherwise
	 */
	public OnlineUser checkHashCode(String caller, int hashCode) {
		try {

			if (!onlineManager.checkIfOnline(caller)) {
				json.put(FieldsNames.CALLER, FieldsNames.OFFLINE);
				return null;
			}

			if (hashCode == onlineManager.getHashCodeByUsername(caller))
				return onlineManager.getOnlineUserByUsername(caller);

			json.put(FieldsNames.HASHCODE, FieldsNames.INVALID);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param callee
	 *            username of the callee
	 * @return if the callee is online its IP Address, otherwise null
	 */
	public InetAddress checkIfCalleeIsOnline(String callee) {

		if (onlineManager.checkIfOnline(callee))
			return onlineManager.getIpAddressByUsername(callee);

		try {

			json.put(FieldsNames.CALLEE, FieldsNames.OFFLINE);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param port
	 *            the random port number choose by the caller
	 * @return true if the port is valid, false otherwise
	 */
	public boolean checkPortLegality(int port) {
		if (port > 1023)
			return true;

		try {

			json.put(FieldsNames.PORT, FieldsNames.INVALID);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
