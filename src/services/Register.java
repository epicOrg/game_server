package services;

import org.json.JSONException;
import org.json.JSONObject;

public class Register implements Service {
	
	private JSONObject json;
	
	private String username;
	private String password;
	
	public Register(JSONObject json) {
		super();
		this.json = json;
	}
	
	@Override
	public String start() {
		readFields();
		return getResponse(saveFields()).toString();
	}
	
	private void readFields() {
		try {
			
			username = json.getString("username");
			password = json.getString("password");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean saveFields(){
		//TODO
		return true;
	}
	
	private JSONObject getResponse(boolean value) {
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			jsonResponse.put("service", "REGISTER");

			if (value) {
				jsonResponse.put("value", true);
				return jsonResponse;
			}
			
			jsonResponse.put("value", false);
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}

	}
}
