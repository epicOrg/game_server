package services.encrypt.subservices;

import messages.fields_names.EncryptFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/05/29
 */

public class WrappedKey implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		// TODO
		@SuppressWarnings("unused")
		String wrappedKey;

		try {

			wrappedKey = request.getString(EncryptFields.WRAPPED_KEY.toString());

			// TODO DEBUG: wrapped symmetric key (string format)
			// System.out.println("WrappedKey (string): " + wrappedKey);

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
