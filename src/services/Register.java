package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import check_fields.RegisterFieldsChecker;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public class Register implements Service {

	private JSONObject jsonRequest;
	private DataManager dataManager;

	private RegisteredUser registeredUser;

	private JSONObject jsonResponse = new JSONObject();
	private boolean noErrors = true;

	public Register(JSONObject json) {
		super();
		this.jsonRequest = json;
		dataManager = DataManager.getInstance();
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
		if (noErrors)
			saveFields();

		generateResponse();
		return jsonResponse.toString();

	}

	private void readFields() {

		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			String email = jsonRequest.getString(FieldsNames.EMAIL);
			registeredUser = new RegisteredUser(username, password, email);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {

		JSONObject errors = new JSONObject();
		RegisterFieldsChecker registerFieldsChecker = new RegisterFieldsChecker(
				errors);

		noErrors &= registerFieldsChecker.checkUsername(registeredUser
				.getUsername());
		noErrors &= registerFieldsChecker.checkPassword(registeredUser
				.getPassword());
		noErrors &= registerFieldsChecker.checkEmail(registeredUser.getEmail());
		if (noErrors) {
			checkAlradyUsed(errors);
		}

		try {

			if (!noErrors)
				jsonResponse.put(FieldsNames.ERRORS, errors);

		} catch (JSONException e) {
			// TODO
		}
	}

	private void checkAlradyUsed(JSONObject errors) {

		if (!dataManager.checkEmail(registeredUser.getEmail())) {
			JSONArray emailErrors = new JSONArray();
			emailErrors.put(FieldsNames.ALREADY_USED);
			try {
				errors.put(FieldsNames.EMAIL, emailErrors);
				jsonResponse.put(FieldsNames.ERRORS, errors);
			} catch (JSONException e) {
			}

			noErrors = false;
		}

		if (!dataManager.checkUsername(registeredUser.getUsername())) {
			JSONArray usernameErrors = new JSONArray();
			usernameErrors.put(FieldsNames.ALREADY_USED);
			try {
				errors.put(FieldsNames.USERNAME, usernameErrors);
				jsonResponse.put(FieldsNames.ERRORS, errors);
			} catch (JSONException e) {
				// TODO
			}

			noErrors = false;
		}

	}

	private void saveFields() {

		try {

			dataManager.saveRegistrationFields(registeredUser);

		} catch (RegistrationFailedException e) {
			try {

				jsonResponse.put(FieldsNames.SERVER_ERROR, true);

			} catch (JSONException e1) {
				// TODO
			}

			noErrors = false;
		}
	}

	private void generateResponse() {

		try {
			jsonResponse.put(FieldsNames.NO_ERRORS, noErrors);
		} catch (JSONException e) {
			// TODO
		}
	}

}
