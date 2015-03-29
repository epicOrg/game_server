package services;

import game_server.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author	Noris
 * @since	2015-03-26
 */

public class Login implements Service {
	
	private DataManager dataManager = DataManager.getIstance();
	
	private JSONObject json;
	
	private String username;
	private String password;
	
	private boolean value;
	private String error;
	
	public Login(JSONObject json) {
		super();
		this.json = json;
	}
	
	@Override
	public String start() {
		readFields();
		checkFields();
		saveFields();
		return getResponse().toString();
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
	
	private boolean checkFields(){
		//TODO
		return true;
	}
	
	private void saveFields(){
		dataManager.setLogged(username, hashCode());
	}
	
	private JSONObject getResponse() {
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			jsonResponse.put("service", "LOGIN");

			if (value) {
				jsonResponse.put("value", true);
				jsonResponse.put("hash", hashCode());
				return jsonResponse;
			}
			
			jsonResponse.put("value", false);
			jsonResponse.put("error", error);
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}
	}
	
	@Override
	public int hashCode() {
		//TODO
		return username.hashCode();
	}
}
