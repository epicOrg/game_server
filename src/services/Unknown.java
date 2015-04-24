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
public class Unknown implements Service {

	@Override
	public String start() {
		return getResponse().toString();
	}

	private String getResponse() {

		JSONObject jsonResponse = new JSONObject();

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.UNKNOWN);
			return jsonResponse.toString();

		} catch (JSONException e) {
			return "{\"" + FieldsNames.SERVICE + "\":\"" + FieldsNames.UNKNOWN
					+ "\"}";
		}

	}

}
