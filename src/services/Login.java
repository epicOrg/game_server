package services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;
import data_management.RegisteredUser;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class Login implements Service {

	private JSONObject jsonRequest;

	private InetAddress ipAddress;
	private int hashCode;
	private DataManager dataManager;
	private RegisteredUser user;

	private JSONObject jsonResponse = new JSONObject();
	private boolean noErrors = true;

	public Login(JSONObject jsonRequest) {
		super();
		this.jsonRequest = jsonRequest;
		dataManager = DataManager.getInstance();
	}

	@Override
	public String start() {

		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readFields();
		checkFields();
		if (noErrors)
			saveFields();

		generatetResponse();
		return jsonResponse.toString();

	}

	private void readFields() {
		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			user = new RegisteredUser(username, password, null);

			ipAddress = InetAddress.getByName(jsonRequest
					.getString(FieldsNames.IP_ADDRESS));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		hashCode = onlineManager.setOnline(user.getUsername(), ipAddress);
	}

	private void generatetResponse() {

		try {

			if (noErrors) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
				jsonResponse.put(FieldsNames.HASHCODE, hashCode);
			} else {
				jsonResponse.put(FieldsNames.NO_ERRORS, false);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
