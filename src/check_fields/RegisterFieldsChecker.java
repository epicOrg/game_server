package check_fields;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/03/28
 */

public class RegisterFieldsChecker {

	private JSONObject json;
	private JSONObject errors;

	public RegisterFieldsChecker(JSONObject json, JSONObject errors) {
		super();
		this.json = json;
		this.errors = errors;
	}

	/**
	 * Check if the user name is valid.
	 * 
	 * @param username
	 * @return true if the user name is valid, false if it's invalid
	 */
	public boolean checkUsername(String username) {

		boolean fieldIsOk = true;

		try {

			JSONArray usernameErrors = new JSONArray();

			if (username.length() < FieldsValues.USERNAME_MIN_LENGTH) {
				usernameErrors.put(FieldsNames.SHORT);
				fieldIsOk = false;
			}

			else if (username.length() > FieldsValues.USERNAME_MAX_LENGTH) {
				usernameErrors.put(FieldsNames.LONG);
				fieldIsOk = false;
			}

			if (username.matches(FieldsValues.USERNAME_FORBIDDEN_CHARS)) {
				usernameErrors.put(FieldsNames.INVALID_CHAR);
				fieldIsOk = false;
			}

			if (!fieldIsOk){
				errors.put(FieldsNames.USERNAME, usernameErrors);
				json.put(FieldsNames.ERRORS, errors);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fieldIsOk;

	}

	/**
	 * Check if the user password is valid.
	 * 
	 * @param user password
	 * @return true if the password is valid, false if it's invalid
	 */
	public boolean checkPassword(String password) {

		boolean fieldIsOk = true;

		try {

			JSONArray passwordErrors = new JSONArray();

			if (password.length() < FieldsValues.PASSWORD_MIN_LENGTH) {
				passwordErrors.put(FieldsNames.SHORT);
				fieldIsOk = false;
			}

			else if (password.length() > FieldsValues.PASSWORD_MAX_LENGTH) {
				passwordErrors.put(FieldsNames.LONG);
				fieldIsOk = false;
			}

			if (!password.matches(FieldsValues.PASSWORD_NEEDED_CHARS)) {
				passwordErrors.put(FieldsNames.INVALID);
				fieldIsOk = false;
			}

			if (!fieldIsOk){
				errors.put(FieldsNames.PASSWORD, passwordErrors);
				json.put(FieldsNames.ERRORS, errors);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fieldIsOk;

	}

	/**
	 * Check if an email address is valid.
	 * 
	 * @param email address
	 * @return true if the email is valid, false if it's invalid
	 * @see <a href="http://goo.gl/1G43mb">Apache Email Validator</a>
	 */
	public boolean checkEmail(String email) {

		EmailValidator emailValidator = EmailValidator.getInstance();

		boolean fieldIsOk = true;

		try {

			JSONArray emailErrors = new JSONArray();

			if (!emailValidator.isValid(email)) {
				emailErrors.put(FieldsNames.INVALID);
				fieldIsOk = false;
			}

			if (email.endsWith("@example.com")) {
				// TODO Add invalid domains
				emailErrors.put(FieldsNames.INVALID_DOMAIN);
				fieldIsOk = false;
			}

			if (!fieldIsOk){
				errors.put(FieldsNames.EMAIL, emailErrors);
				json.put(FieldsNames.ERRORS, errors);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fieldIsOk;

	}

}
