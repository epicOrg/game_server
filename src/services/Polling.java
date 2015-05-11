package services;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

public class Polling implements IService {
	
	private JSONObject request;

	@Override
	public JSONObject start() {
		
		String username = null;
		try {
			username = request.getString(FieldsNames.USERNAME);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OnlineManager onlineManager = OnlineManager.getInstance();
		try {
			OnlineUser user = onlineManager.getOnlineUserByUsername(username);
			user.setPolled(true);
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public void setRequest(JSONObject request) {
		this.request = request;
	}

}
