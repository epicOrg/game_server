package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * If the Service selected from the client not exists, it generate an Unknown
 * Service message.
 * 
 * @author Noris
 * @date 2015/03/26
 */

public class Unknown implements IService {

	@Override
	public void setRequest(JSONObject request) {
	}

	@Override
	public JSONObject start() {
		return getResponse();
	}

	private JSONObject getResponse() {

		JSONObject jsonResponse = new JSONObject();

		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.UNKNOWN);
		} catch (JSONException e) {
		}

		return jsonResponse;
	}

}
