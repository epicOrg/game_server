package services;

import java.security.Key;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

public class Crypt {
	
	private JSONObject jsonRequest;
	private JSONObject jsonResponse;
	
	private Key clientPublicKey;
	
	public Crypt() {
	}
	
	public String start() {
		jsonResponse = new JSONObject();

		
		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		} catch (JSONException e) {
		}
		
		return null;
		
	}
	
//	private void readFields() {
//
//		try {
//
//			clientPublicKey = (Key) jsonRequest.getString(FieldsNames.USERNAME);
//			password = json.getString(FieldsNames.PASSWORD);
//			email = json.getString(FieldsNames.EMAIL);
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		clientPublicKey.
//	}
	
	

}
