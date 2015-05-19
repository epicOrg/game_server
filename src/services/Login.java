package services;

import java.io.PrintWriter;
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
	private PrintWriter printWriter;
	private DataManager dataManager;
	private RegisteredUser user;

	private JSONObject jsonResponse;
	private boolean noErrors = true;

	public Login(PrintWriter printWriter) {
		this.printWriter = printWriter;
		
		dataManager = DataManager.getInstance();
	}

	@Override
	public JSONObject start() {

		try {
			readFields();
		} catch (MissingFieldException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		checkFields();
		if (noErrors)
			saveFields();

		generateResponse();
		return jsonResponse;

	}

	private void readFields() throws MissingFieldException {
		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			user = new RegisteredUser(username, password, null);

			ipAddress = InetAddress.getByName(jsonRequest
					.getString(FieldsNames.IP_ADDRESS));

		} catch (JSONException e) {
			throw new MissingFieldException();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		if(onlineManager.checkIfOnline(user.getUsername())){
			noErrors = false;
			return;
		}
		noErrors = dataManager.checkPassword(user);
	}

	private void saveFields() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		hashCode = onlineManager.setOnline(user.getUsername(), ipAddress, printWriter);
	}

	private void generateResponse() {
		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.LOGIN);

			jsonResponse.put(FieldsNames.USERNAME, user.getUsername());
			jsonResponse.put(FieldsNames.HASHCODE, hashCode);
			jsonResponse.put(FieldsNames.NO_ERRORS, noErrors);
			
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
