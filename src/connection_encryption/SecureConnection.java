package connection_encryption;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class SecureConnection {

	private ConnectionEncrypter connectionEncrypter = new ConnectionEncrypter();

	private static final boolean REFUSE_UNCRYPTED_CONNECTIONS = false;
	private static final boolean SEND_ENCRYPTED_RESPONSE = false;

	public JSONObject decrypt(JSONObject jsonRequest) throws JSONException {

		if (!isConnectionEncrypted(jsonRequest)) {
			
			if (jsonRequest.has(FieldsNames.ENCRYPT_WRAPPED_KEY))
				connectionEncrypter.setAsymmetricKey(jsonRequest
						.getString(FieldsNames.ENCRYPT_WRAPPED_KEY));
			
			return jsonRequest;
		}
			

		String encryptedRequest = jsonRequest
				.getString(FieldsNames.ENCRYPT_MESSAGE);
		String uncryptedRequest = connectionEncrypter
				.decryptRequest(encryptedRequest);

		return new JSONObject(uncryptedRequest);

	}

	public JSONObject encrypt(JSONObject jsonResponse) throws JSONException {

		if (!SEND_ENCRYPTED_RESPONSE)
			return jsonResponse;

		if (jsonResponse.getString(FieldsNames.SERVICE).equals(
				FieldsNames.ENCRYPT))
			return jsonResponse;

		String uncryptedResponse = jsonResponse
				.getString(FieldsNames.ENCRYPT_MESSAGE);
		String encryptedResponse = connectionEncrypter
				.encryptResponse(uncryptedResponse);

		JSONObject jsonEncryptedResponse = new JSONObject();
		jsonEncryptedResponse.put(FieldsNames.ENCRYPT_MESSAGE,
				encryptedResponse);

		return jsonEncryptedResponse;
	}

	private boolean isConnectionEncrypted(JSONObject json) {

		if (REFUSE_UNCRYPTED_CONNECTIONS)
			return true;

		return json.has(FieldsNames.ENCRYPT_MESSAGE);
	}

}
