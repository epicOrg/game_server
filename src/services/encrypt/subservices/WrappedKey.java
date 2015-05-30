package services.encrypt.subservices;

import messages.fields_names.EncryptFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;

public class WrappedKey implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		String wrappedKey;

		try {
			wrappedKey = request.getString(EncryptFields.WRAPPED_KEY.toString());
			System.out.println("WrappedKey STR: " + wrappedKey);
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
