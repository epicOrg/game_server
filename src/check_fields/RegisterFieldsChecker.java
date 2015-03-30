package check_fields;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author	Noris
 * @since	2015-03-28
 */

public class RegisterFieldsChecker {
	
	JSONObject json;
	
	public RegisterFieldsChecker(JSONObject json) {
		super();
		this.json = json;
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
			
			if ( username.length() < 5 ) {
				usernameErrors.put(FieldsNames.SHORT);
				fieldIsOk = false;
			}
			
			else if ( username.length() > 20 ) {
				usernameErrors.put(FieldsNames.LONG);
				fieldIsOk = false;
			}
			
			if ( username.contains(" ") ) {
				usernameErrors.put(FieldsNames.INVALID_CHAR);
				fieldIsOk = false;
			}
			
			json.put(FieldsNames.USERNAME, usernameErrors);
			
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
			
			if ( password.length() < 8) {
				passwordErrors.put(FieldsNames.SHORT);
				fieldIsOk = false;
			}
			
			else if ( password.length() > 30) {
				passwordErrors.put(FieldsNames.LONG);
				fieldIsOk = false;
			}
			
			json.put(FieldsNames.PASSWORD, passwordErrors);
			
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
	 * @see <a href="http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/EmailValidator.html">Apache Email Validator</a>
	 */
	public boolean checkEmail(String email) {
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		
		boolean fieldIsOk = true;
		
		try {
			
			JSONArray emailErrors = new JSONArray();
			
			if ( !emailValidator.isValid(email) ) {
				emailErrors.put(FieldsNames.INVALID_EMAIL);
				fieldIsOk = false;
			}
			
			if ( email.endsWith("@mailnesia.com") ) {
				//TODO Add invalid domains
				emailErrors.put(FieldsNames.INVALID_DOMAIN);
				fieldIsOk = false;
			}
			
			json.put(FieldsNames.EMAIL, emailErrors);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fieldIsOk;
		
	}
	
}
