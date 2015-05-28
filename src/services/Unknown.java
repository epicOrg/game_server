package services;

import org.json.JSONException;
import org.json.JSONObject;

import fields_names.FieldsNames;
import fields_names.ServicesFields;

/**
 * Default Service started when the Requested service from the client isn't supported.
 * 
 * @author Noris
 * @date 2015/03/26
 */

public class Unknown implements IService {


	@Override
	public JSONObject start(JSONObject request) {
		JSONObject response = new JSONObject();

		try {
			response.put(ServicesFields.SERVICE.toString(), ServicesFields.UNKNOWN.toString());
		} catch (JSONException e) {
		}

		return response;
	}

	@Override
	public String getName() {
		return ServicesFields.UNKNOWN.toString();
	}
}
