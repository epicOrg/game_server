package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import check_fields.RegisterFieldsChecker;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class Register implements Service {

	private JSONObject json;

	private String username;
	private String password;
	private String email;

	private JSONObject jsonResponse = new JSONObject();
	private boolean fieldsAreOk = true;

	public Register(JSONObject json) {
		super();
		this.json = json;
	}

	@Override
	public String start() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.REGISTER);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readFields();
		checkFields();
		if (fieldsAreOk)
			saveFields();
		
		generateResponse();
		return jsonResponse.toString();

	}

	private void readFields() {

		try {

			username = json.getString(FieldsNames.USERNAME);
			password = json.getString(FieldsNames.PASSWORD);
			email = json.getString(FieldsNames.EMAIL);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {

		RegisterFieldsChecker registerFieldsChecker = new RegisterFieldsChecker(
				jsonResponse);
		fieldsAreOk &= registerFieldsChecker.checkUsername(username);
		fieldsAreOk &= registerFieldsChecker.checkPassword(password);
		fieldsAreOk &= registerFieldsChecker.checkEmail(email);
	}

	private void saveFields() {
		// TODO Save fields into database
	}

	private void generateResponse() {

		try {

			if (fieldsAreOk == true) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
			}

		} catch (JSONException e) {
			// TODO
		}
	}

}
