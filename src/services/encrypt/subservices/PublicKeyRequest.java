package services.encrypt.subservices;

import org.json.JSONException;
import org.json.JSONObject;

import connection_encryption.ConnectionEncrypter;
import fields_names.EncryptFields;
import fields_names.ServicesFields;
import services.IService;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/05/29
 */

public class PublicKeyRequest implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		JSONObject jsonResponse = new JSONObject();

		try {

			jsonResponse.put(ServicesFields.SERVICE.toString(), ServicesFields.ENCRYPT.toString());
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
