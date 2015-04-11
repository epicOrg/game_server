package services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class Login implements Service {

	// private DataManager dataManager = DataManager.getIstance();

	private JSONObject json;

	private String username;
	private String password;
	private InetAddress ipAddress;
	private int hashCode;

	private JSONObject jsonResponse = new JSONObject();
	private boolean fieldsAreOk = true;

	public Login(JSONObject json) {
		super();
		this.json = json;
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
		if (fieldsAreOk)
			saveFields();
		return getResponse().toString();

	}

	private void readFields() {
		try {

			username = json.getString(FieldsNames.USERNAME);
			System.out.println(json.toString());
			password = json.getString(FieldsNames.PASSWORD);
			ipAddress = InetAddress.getByName(json.getString(FieldsNames.IP_ADDRESS));
			System.out.println(ipAddress.toString());
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkFields() {
		// TODO
		return true;
	}

	private void saveFields() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		//hashCode = onlineManager.setOnline(username, ipAddress);
	}

	private JSONObject getResponse() {

		try {

			if (fieldsAreOk == true) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);				
				jsonResponse.put(FieldsNames.HASHCODE, hashCode);
				System.out.println(jsonResponse.toString());
				return jsonResponse;
			}
			return jsonResponse;

		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
}
