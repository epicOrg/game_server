package check_fields;

import messages.fields_names.CommonFields;
import messages.fields_names.RegisterFields;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.Register;
import data_management.UsersDataManager;

/**
 * Contains methods for check registration fields in client request to create an
 * account on the server. The data choose by the user must be compatible with
 * the ones defined in {@link FieldsValues}.
 * 
 * @author Noris
 * @date 2015/03/28
 * @see Register
 */

public class RegisterChecker {

	private JSONObject errors = new JSONObject();
	private boolean noErrors = true;

	/**
	 * Checks if the user name is valid.
	 * 
	 * @param username
	 * @return true if the user name is valid, false if it's invalid
	 * @see FieldsValues#USERNAME_FORBIDDEN_CHARS
	 * @see FieldsValues#USERNAME_NEEDED_CHARS
	 * @see FieldsValues#USERNAME_MAX_LENGTH
	 * @see FieldsValues#USERNAME_MIN_LENGTH
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
				usernameErrors.put(RegisterFields.REGISTER_INVALID_CHAR
						.toString());
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
	 * Checks if the user password is valid.
	 * 
	 * @param user
	 *            password
	 * @return true if the password is valid, false if it's invalid
	 * @see FieldsValues#PASSWORD_NEEDED_CHARS
	 * @see FieldsValues#PASSWORD_MAX_LENGTH
	 * @see FieldsValues#PASSWORD_MIN_LENGTH
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
				passwordErrors.put(RegisterFields.REGISTER_INVALID_CHAR
						.toString());
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
	 * Checks if an e-mail address is valid.
	 * 
	 * @param email
	 *            address
	 * @return true if the e-mail is valid, false otherwise
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
				emailErrors.put(RegisterFields.REGISTER_INVALID_DOMAIN
						.toString());
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
	 * Checks if exist another user registered with the same username.
	 * 
	 * @param username
	 *            the username to check
	 * @return true if the username is available, false otherwise
	 * @see UsersDataManager#checkUsername(String)
	 */
	public boolean checkAlreadyUsedUsername(String username) {

		UsersDataManager dataManager = UsersDataManager.getInstance();

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
	 * Checks if exist another user registered with the same e-mail.
	 * 
	 * @param email
	 *            the e-mail to check
	 * @return true if the aren't user registered with the given e-mail
	 * @see UsersDataManager#checkEmail(String)
	 */
	public boolean checkAlreadyUsedEmail(String email) {

		UsersDataManager dataManager = UsersDataManager.getInstance();

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

	/**
	 * Add an error to the current registration session.
	 */
	public void addError() {
		this.noErrors = false;
	}

	/**
	 * @return a {@link JSONObject} containing the registration errors.
	 */
	public JSONObject getErrors() {
		return errors;
	}

	/**
	 * @return true if the registration has produce at least an error, false
	 *         otherwise
	 */
	public boolean noErrors() {
		return noErrors;
	}

}
