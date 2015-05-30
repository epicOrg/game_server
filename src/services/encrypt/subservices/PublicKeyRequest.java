package services.encrypt.subservices;

import messages.fields_names.EncryptFields;
import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

import connection_encryption.ConnectionEncrypter;
import services.IService;

public class PublicKeyRequest implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		JSONObject jsonResponse = new JSONObject();

		try {

			jsonResponse.put(ServicesFields.SERVICE.toString(), ServicesFields.ENCRYPT);
			jsonResponse.put(EncryptFields.PUBLIC_KEY.toString(),
					ConnectionEncrypter.getPublicKey());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

	@Override
	public String getName() {
		return EncryptFields.PUBLIC_KEY_REQUEST.toString();
	}

}
