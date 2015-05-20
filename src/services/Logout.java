package services;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

/**
 * Elaborates explicit Logout requests setting the applicant offline
 * 
 * @author Micieli
 * @date 2015/05/17
 */

public class Logout implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		OnlineManager onlineManager = OnlineManager.getInstance();

		try {

			String username = request.getString(FieldsNames.USERNAME);
			int hashCode = request.getInt(FieldsNames.HASHCODE);
			onlineManager.setOffline(username, hashCode);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
