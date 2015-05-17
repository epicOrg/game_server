package services;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

public class LogOut implements IService {
	
	private JSONObject request;

	@Override
	public JSONObject start() {
		
		OnlineManager onlineManager = OnlineManager.getInstance();	
		
		try {
			String username = request.getString(FieldsNames.USERNAME);
			int hashCode = request.getInt(FieldsNames.HASHCODE);			
			
			onlineManager.setOffline(username, hashCode);			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
