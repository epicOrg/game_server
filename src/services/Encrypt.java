package services;

import org.json.JSONException;
import org.json.JSONObject;

import connection_encryption.ConnectionEncrypter;
import fields_names.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class Encrypt implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	@Override
	public JSONObject start(JSONObject jsonRequest) {
		this.jsonRequest = jsonRequest;
		jsonResponse = new JSONObject();
		runService();
		generateResponse();

		return jsonResponse;
	}

	private void runService() {

		try {

			switch (jsonRequest.getString(FieldsNames.SERVICE_TYPE)) {

			case FieldsNames.PUBLIC_KEY_REQUEST:
				jsonResponse.put(FieldsNames.PUBLIC_KEY,
						ConnectionEncrypter.getPublicKey()); // TODO
				break;
			}

		} catch (JSONException e) {
		}

	}

	private void generateResponse() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.ENCRYPT);
			jsonResponse.put(FieldsNames.NO_ERRORS, true);

		} catch (JSONException e) {
		}
	}

	@Override
	public String getName() {
		return FieldsNames.ENCRYPT;
	}

}
