package services.encrypt.subservices;

import org.json.JSONException;
import org.json.JSONObject;

import fields_names.EncryptFields;
import services.IService;

public class WrappedKey implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		String symmetricKey;

		try {
			symmetricKey = request.getString(EncryptFields.WRAPPED_KEY.toString());
			System.out.println("Chiave: " + symmetricKey);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		return EncryptFields.WRAPPED_KEY.toString();
	}

}
