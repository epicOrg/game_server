package connection_encryption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fields_names.EncryptFields;
import fields_names.ServicesFields;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class SecureConnectionApplicator {

	private ConnectionEncrypter connectionEncrypter = new ConnectionEncrypter();

	private static final boolean REFUSE_UNENCRYPTED_REQUEST = true;

	public JSONObject decrypt(String request) throws JSONException {

		// If the request is not encrypted...
		if (isJSONObject(request)) {

			JSONObject jsonRequest = new JSONObject(request);

			// ...it can be an encryption establishment message...
			if (jsonRequest.getString(ServicesFields.SERVICE.toString()).equals(
					ServicesFields.ENCRYPT.toString())) {

				if (jsonRequest.has(EncryptFields.WRAPPED_KEY.toString()))
					connectionEncrypter.setSymmetricKey(jsonRequest
							.getString(EncryptFields.WRAPPED_KEY.toString()));

				return jsonRequest;
			}

			// ...or a polling message...
			if (jsonRequest.getString(ServicesFields.SERVICE.toString()).equals(
					ServicesFields.POLLING.toString())) {
				return jsonRequest;
			}

			// ...or a normal not encrypted message.
			if (REFUSE_UNENCRYPTED_REQUEST)
				return new JSONObject();

			return jsonRequest;
		}

		// If the request is encrypted but the asymmetric key wasn't set, I
		// return a JSNObject, that it cause an Unknown Service response.
		if (!connectionEncrypter.isSymmetricKeySet()) {
			return new JSONObject();
		}

		String uncryptedRequest = connectionEncrypter.decryptRequest(request);

		return new JSONObject(uncryptedRequest);

	}

	public String encrypt(JSONObject jsonResponse) throws JSONException {

		// If (a) the encryption is not enabled, or if (b) the response is an
		// encryption establishment message.
		if ((jsonResponse.has(ServicesFields.SERVICE.toString()) && (jsonResponse
				.getString(ServicesFields.SERVICE.toString()).equals(ServicesFields.ENCRYPT
				.toString())))
				|| !ConnectionEncrypter.isEncryptionEnabled()) {
			return jsonResponse.toString();
		}

		return connectionEncrypter.encryptResponse(jsonResponse.toString());
	}

	private boolean isJSONObject(String string) {

		try {
			new JSONObject(string);
		} catch (JSONException e1) {
			try {
				new JSONArray(string);
			} catch (JSONException e2) {
				return false;
			}
		}

		return true;

	}

}
