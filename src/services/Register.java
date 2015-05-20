
package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import check_fields.RegisterChecker;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.MissingFieldException;
import exceptions.RegistrationFailedException;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public class Register implements IService {

	private DataManager dataManager;

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private RegisteredUser registeredUser;

	private RegisterChecker registerChecker;

	public Register() {
		dataManager = DataManager.getInstance();
	}

	@Override
	public JSONObject start(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		registerChecker = new RegisterChecker();	

		readFields();

		checkFields();
		if (registerChecker.noErrors())
			saveFields();

		generateResponse();
		return jsonResponse;

	}

	private void readFields() {

		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			String email = jsonRequest.getString(FieldsNames.EMAIL);
			registeredUser = new RegisteredUser(username, password, email);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void checkFields() {

		registerChecker.checkUsername(registeredUser.getUsername());
		registerChecker.checkPassword(registeredUser.getPassword());
		registerChecker.checkEmail(registeredUser.getEmail());

		if (registerChecker.noErrors()) {
			registerChecker.checkAlreadyUsedUsername(dataManager,registeredUser.getUsername());
			registerChecker.checkAlreadyUsedEmail(dataManager,	registeredUser.getEmail());
		}
	}

	private void saveFields() {

		try {

			dataManager.saveRegistrationFields(registeredUser);

		} catch (RegistrationFailedException e) {
			try {

				jsonResponse.put(FieldsNames.SERVER_ERROR, true);
				registerChecker.addError();

			} catch (JSONException e1) {
				e.printStackTrace();
			}
		}
	}

	private void generateResponse() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
			jsonResponse.put(FieldsNames.NO_ERRORS, registerChecker.noErrors());
			jsonResponse.put(FieldsNames.ERRORS, registerChecker.getErrors());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
