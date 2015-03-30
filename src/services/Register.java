package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import check_fields.RegisterFieldsChecker;

/**
 * @author	Noris
 * @since	2015-03-26
 */

public class Register implements Service {
	
	//private DataManager dataManager = DataManager.getIstance();
	
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
		if ( fieldsAreOk )
			saveFields();
		return getResponse().toString();
		
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
	
	private void checkFields(){
		
		RegisterFieldsChecker registerFieldsChecker = new RegisterFieldsChecker(jsonResponse);
		fieldsAreOk &= registerFieldsChecker.checkUsername(username);
		fieldsAreOk &= registerFieldsChecker.checkPassword(password);
		fieldsAreOk &= registerFieldsChecker.checkEmail(email);
	}
	
	private void saveFields(){
		//TODO Save fields into database
	}
	
	private JSONObject getResponse() {
		
		try {

			if ( fieldsAreOk == true ) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
				return jsonResponse;
			}
			
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}
	}
}
