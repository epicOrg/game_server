package check_fields;

import java.util.ArrayList;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import fields_name.FieldsNames;
import services.IService;
import services.Login;

/**
 * A common request checker for all {@link IService}. Before elaborating a
 * request use this class to check if the user is authorized to enjoy a server
 * service. The user must be logged and the provided hashCode must match with
 * the one generated by the server in the login.
 * 
 * @author Micieli
 * @date 2015/05/21
 * @see ClientIdentityCecker
 * @see OnlineManager
 * @see OnlineUser
 * @see Login
 */

public class RequestFieldChecher {

	private ClientIdentityCecker cecker;
	private static ArrayList<String> serviceNotToBeChecked;

	public RequestFieldChecher() {
		super();
		cecker = new ClientIdentityCecker();
	}

	/**
	 * Checks if a request satisfy the requirements.
	 * 
	 * @param request
	 *            the request to check
	 * @return true if the request is valid, false otherwise
	 */
	public boolean checkRequest(JSONObject request) {

		try {
			String serviceName = request.getString(FieldsNames.SERVICE);

			if (serviceNotToBeChecked.contains(serviceName)) {
				return true;
			} else {

				String username = request.getString(FieldsNames.USERNAME);
				int hashCode = request.getInt(FieldsNames.HASHCODE);
				return cecker.isUserOnline(username) && cecker.checkHashCode(username, hashCode);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void setServiceNotToBeChecked(ArrayList<String> serviceNotToBeChecked) {
		RequestFieldChecher.serviceNotToBeChecked = serviceNotToBeChecked;
	}
}
