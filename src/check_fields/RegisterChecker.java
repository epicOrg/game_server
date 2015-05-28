package check_fields;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.Register;
import data_management.DataManager;
import fields_names.CommonFields;
import fields_names.RegisterFields;

/**
 * Contains methods for check registration fields sending in client request to
 * create an account on the server. The data choose by the user must be
 * compatible with the ones defined in {@link FieldsValues}.
 * 
 * @author Noris
 * @date 2015/03/28
 * @see Register
 */

public class RegisterChecker {

	private JSONObject errors = new JSONObject();
	private boolean noErrors = true;

	/**
	 * Check if the username is valid.
	 * 
	 * @param username
	 * @return true if the username is valid, false if it's invalid
	 */
	public boolean checkUsername(String username) {

		boolean fieldIsOk = true;

		try {

			JSONArray usernameErrors = new JSONArray();

			if (username.length() < FieldsValues.USERNAME_MIN_LENGTH) {
				usernameErrors.put(RegisterFields.REGISTER_SHORT.toString());
				fieldIsOk = false;
			}

			else if (username.length() > FieldsValues.USERNAME_MAX_LENGTH) {
				usernameErrors.put(RegisterFields.REGISTER_LONG.toString());
				fieldIsOk = false;
			}

			if (username.matches(FieldsValues.USERNAME_FORBIDDEN_CHARS)) {
				usernameErrors.put(RegisterFields.REGISTER_INVALID_CHAR.toString());
				fieldIsOk = false;
			}

			if (!fieldIsOk) {
				errors.put(CommonFields.USERNAME.toString(), usernameErrors);
				noErrors = false;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return fieldIsOk;

	}

	/**
	 * Check if the user password is valid.
	 * 
	 * @param user
	 *            password
	 * @return true if the password is valid, false if it's invalid
	 */
	public boolean checkPassword(String password) {

		boolean fieldIsOk = true;

		try {

			JSONArray passwordErrors = new JSONArray();

			if (password.length() < FieldsValues.PASSWORD_MIN_LENGTH) {
				passwordErrors.put(RegisterFields.REGISTER_SHORT.toString());
				fieldIsOk = false;
			}

			else if (password.length() > FieldsValues.PASSWORD_MAX_LENGTH) {
				passwordErrors.put(RegisterFields.REGISTER_LONG.toString());
				fieldIsOk = false;
			}

			if (!password.matches(FieldsValues.PASSWORD_NEEDED_CHARS)) {
				passwordErrors.put(RegisterFields.REGISTER_INVALID_CHAR.toString());
				fieldIsOk = false;
			}

			if (!fieldIsOk) {
				errors.put(CommonFields.PASSWORD.toString(), passwordErrors);
				noErrors = false;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return fieldIsOk;

	}

	/**
	 * Check if an email address is valid.
	 * 
	 * @param email
	 *            address
	 * @return true if the email is valid, false if it's invalid
	 * @see <a href="http://goo.gl/1G43mb">Apache Email Validator</a>
	 */
	public boolean checkEmail(String email) {

		EmailValidator emailValidator = EmailValidator.getInstance();

		boolean fieldIsOk = true;

		try {

			JSONArray emailErrors = new JSONArray();

			if (!emailValidator.isValid(email)) {
				emailErrors.put(CommonFields.INVALID.toString());
				fieldIsOk = false;
			}

			if (email.endsWith("@example.com")) {
				// TODO Add invalid domains
				emailErrors.put(RegisterFields.REGISTER_INVALID_DOMAIN.toString());
				fieldIsOk = false;
			}

			if (!fieldIsOk) {
				errors.put(RegisterFields.EMAIL.toString(), emailErrors);
				noErrors = false;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return fieldIsOk;

	}

	/**
	 * 
	 * Checks if exist another user registered with the same username
	 * 
	 * @param username
	 *            the username to check
	 * @return true if the username is available, false otherwise
	 */
	public boolean checkAlreadyUsedUsername(String username) {
		DataManager dataManager = DataManager.getInstance();
		if (!dataManager.checkUsername(username)) {
			JSONArray usernameErrors = new JSONArray();
			usernameErrors.put(RegisterFields.REGISTER_ALREADY_USED.toString());

			try {
				errors.put(CommonFields.USERNAME.toString(), usernameErrors);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return noErrors = false;
		}

		return true;
	}

	/**
	 * 
	 * Checks if exist another user registered with the same email
	 * 
	 * @param email
	 *            the email to check
	 * @return true if the aren't user registered with the given email
	 */
	public boolean checkAlreadyUsedEmail(String email) {
		DataManager dataManager = DataManager.getInstance();
		if (!dataManager.checkEmail(email)) {
			JSONArray emailErrors = new JSONArray();
			emailErrors.put(RegisterFields.REGISTER_ALREADY_USED.toString());

			try {
				errors.put(RegisterFields.EMAIL.toString(), emailErrors);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return noErrors = false;
		}

		return true;
	}

	public void addError() {
		this.noErrors = false;
	}

	public JSONObject getErrors() {
		return errors;
	}

	public boolean noErrors() {
		return noErrors;
	}

}
