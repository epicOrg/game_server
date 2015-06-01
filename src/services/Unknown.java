package services;

import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Default service started when the service requested from the client is not
 * supported by the server.
 * 
 * @author Noris
 * @date 2015/03/26
 */

public class Unknown implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		JSONObject response = new JSONObject();

		try {
			response.put(ServicesFields.SERVICE.toString(),
					ServicesFields.UNKNOWN.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public String getName() {
		return ServicesFields.UNKNOWN.toString();
	}
}
