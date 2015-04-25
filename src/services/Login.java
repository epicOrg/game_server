package services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.MissingFieldException;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class Login implements IService {

	private JSONObject jsonRequest;

	private InetAddress ipAddress;
	private int hashCode;
	private DataManager dataManager;
	private RegisteredUser user;
	private int port;

	private JSONObject jsonResponse;
	private boolean noErrors = true;

	public Login() {
		dataManager = DataManager.getInstance();
	}

	@Override
	public String start() {

		try {
			readFields();
		} catch (MissingFieldException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		checkFields();
		if (noErrors)
			saveFields();

		generatetResponse();
		return jsonResponse.toString();

	}

	private void readFields() throws MissingFieldException {
		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			user = new RegisteredUser(username, password, null);

			ipAddress = InetAddress.getByName(jsonRequest
					.getString(FieldsNames.IP_ADDRESS));
			port = jsonRequest.getInt(FieldsNames.LOCAL_PORT);

		} catch (JSONException e) {
			throw new MissingFieldException();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {
		noErrors = dataManager.checkPassword(user);
	}

	private void saveFields() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		hashCode = onlineManager.setOnline(user.getUsername(), ipAddress, port);
	}

	private void generatetResponse() {
		try {			
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.LOGIN);

			jsonResponse.put(FieldsNames.USERNAME, user.getUsername());
			jsonResponse.put(FieldsNames.HASHCODE, hashCode);
			if (noErrors) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
			} else {
				jsonResponse.put(FieldsNames.NO_ERRORS, false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
	}
}
