package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author	Noris
 * @since	2015-03-26
 */

public class Unknown implements Service {

	@Override
	public String start() {
		return getResponse().toString();
	}
	
	private JSONObject getResponse() {
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.UNKNOWN);
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}

	}

}
