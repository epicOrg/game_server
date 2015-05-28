package services;

import org.json.JSONException;
import org.json.JSONObject;

import connection_encryption.ConnectionEncrypter;
import fields_names.CommonFields;
import fields_names.EncryptFields;
import fields_names.FieldsNames;
import fields_names.ServicesFields;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class Encrypt implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;
	
	private static final String PUBLIC_KEY_REQUEST = "PUBLIC_KEY_REQUEST";
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

			switch (jsonRequest.getString(ServicesFields.SERVICE_TYPE.toString())) {

			case PUBLIC_KEY_REQUEST:
				jsonResponse.put(EncryptFields.PUBLIC_KEY.toString(),
						ConnectionEncrypter.getPublicKey()); // TODO
				break;
			}

		} catch (JSONException e) {
		}

	}

	private void generateResponse() {

		try {

			jsonResponse.put(ServicesFields.SERVICE.toString(), ServicesFields.ENCRYPT.toString());
			jsonResponse.put(CommonFields.NO_ERRORS.toString(), true);

		} catch (JSONException e) {
		}
	}

	@Override
	public String getName() {
		return ServicesFields.ENCRYPT.toString();
	}

}
