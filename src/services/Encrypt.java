package services;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import connection_encryption.ConnectionEncrypter;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class Encrypt implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	@Override
	public void setRequest(JSONObject jsonRequest) {
		this.jsonRequest = jsonRequest;
		jsonResponse = new JSONObject();
	}

	@Override
	public JSONObject start() {

		runService();
		generateResponse();

		return jsonResponse;
	}

	private void runService() {

		try {

			switch (jsonRequest.getString(FieldsNames.SERVICE_TYPE)) {

			case FieldsNames.ENCRYPT_GET_KEY:
				jsonResponse.put(FieldsNames.ENCRYPT_PUBLIC_KEY,
						ConnectionEncrypter.getPublicKey()); //TODO
				break;

			case FieldsNames.ENCRYPT_SET_KEY:
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

}
