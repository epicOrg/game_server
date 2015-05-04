package check_fields;

import org.json.JSONException;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class CallChecker extends ServiceChecker {

	/**
	 * @param callee
	 *            username of the callee
	 * @return if the callee is online its IP Address, otherwise null
	 */
	public boolean checkIfCalleeIsOnline(String callee) {

		if (onlineManager.checkIfOnline(callee))
			return true;

		try {

			errors.put(FieldsNames.CALLEE, FieldsNames.OFFLINE);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return noErrors = false;
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

			errors.put(FieldsNames.AUDIO_PORT_CLIENT, FieldsNames.INVALID);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return noErrors = false;
	}

}
