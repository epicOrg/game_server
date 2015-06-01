package services;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import messages.fields_names.CommonFields;
import messages.fields_names.ServicesFields;
import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import data_management.UsersDataManager;
import data_management.RegisteredUser;

/**
 * <code>Login</code> service allows the client to access to others services.
 * The {@link OnlineManager} provides functions to manages {@link OnlineUser}.
 * Before logging in an user must be registered. The client has to provides his
 * credentials (username and password) to be correctly identified,
 * 
 * @author Noris
 * @date 2015/03/26
 * @see Register
 * @see RegisteredUser
 * @see OnlineManager
 * @see OnlineUser
 */

public class Login implements IService {

	private JSONObject jsonRequest;

	private InetAddress ipAddress;
	private int hashCode;
	private PrintWriter printWriter;
	private RegisteredUser user;

	public Login(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	@Override
	public JSONObject start(JSONObject request) {

		this.jsonRequest = request;

		readFields();

		boolean noErrors = checkFields();
		if (noErrors)
			setUserOnline();

		return generateResponse(noErrors);

	}

	private void readFields() {

		try {

			String username = jsonRequest.getString(CommonFields.USERNAME.toString());
			String password = jsonRequest.getString(CommonFields.PASSWORD.toString());
			user = new RegisteredUser(username, password, null);
			ipAddress = InetAddress.getByName(jsonRequest.getString(CommonFields.IP_ADDRESS
					.toString()));

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkFields() {

		OnlineManager onlineManager = OnlineManager.getInstance();

		if (onlineManager.checkIfOnline(user.getUsername())) {
			return false;
		}

		return UsersDataManager.getInstance().checkPassword(user);
	}

	private void setUserOnline() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		hashCode = onlineManager.setOnline(user.getUsername(), ipAddress, printWriter);
	}

	private JSONObject generateResponse(boolean noErrors) {

		JSONObject response = new JSONObject();

		try {
			
			response.put(ServicesFields.SERVICE.toString(), ServicesFields.LOGIN.toString());
			response.put(CommonFields.USERNAME.toString(), user.getUsername());
			response.put(CommonFields.HASHCODE.toString(), hashCode);
			response.put(CommonFields.NO_ERRORS.toString(), noErrors);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public String getName() {
		return ServicesFields.LOGIN.toString();
	}
}
