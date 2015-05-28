package connection_encryption;

import org.json.JSONException;
import org.json.JSONObject;

import fields_names.EncryptFields;
import fields_names.FieldsNames;
import fields_names.ServicesFields;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class SecureConnectionApplicator {

	private ConnectionEncrypter connectionEncrypter = new ConnectionEncrypter();

	private static final boolean REFUSE_UNENCRYPTED_REQUEST = false;

	public JSONObject decrypt(JSONObject jsonRequest) throws JSONException {

		// If the request is not encrypted...
		if (!isRequestEncrypted(jsonRequest)) {

			// ...it can be an encryption establishment message...
			if (jsonRequest.getString(ServicesFields.SERVICE.toString()).equals(
					ServicesFields.ENCRYPT.toString())) {

				if (jsonRequest.has(EncryptFields.WRAPPED_KEY.toString()))
					connectionEncrypter.setAsymmetricKey(jsonRequest
							.getString(EncryptFields.WRAPPED_KEY.toString()));

				return jsonRequest;
			}

			// ...or a normal unencrypted message.
			if (REFUSE_UNENCRYPTED_REQUEST)
				return new JSONObject();
			return jsonRequest;
		}

		// If the request is encrypted but the asymmetric key wasn't set, I
		// return a JSNObject, that it cause an Unknown Service response.
		if (!connectionEncrypter.isSymmetricKeySet())
			return new JSONObject();

		String encryptedRequest = jsonRequest
				.getString(EncryptFields.ENCRYPTED_MESSAGE.toString());
		String uncryptedRequest = connectionEncrypter
				.decryptRequest(encryptedRequest);

		return new JSONObject(uncryptedRequest);

	}

	// TODO Refactor
	public JSONObject encrypt(JSONObject jsonResponse) throws JSONException {

		// TODO If the encryption is enabled but the asymmetric key is not set,
		// the server can send unencrypted messages. Remove this feature when
		// the client will implement the secure connection.

		// If (a) the encryption is not enabled, or if (b) the response is an
		// encryption establishment message, or if (c) the asymmetric key is not
		// set, it send an unencrypted response.
		if ((jsonResponse.has(ServicesFields.SERVICE.toString()) && (jsonResponse // TODO
				.getString(ServicesFields.SERVICE.toString()).equals(ServicesFields.ENCRYPT.toString())))
				|| !ConnectionEncrypter.isEncryptionEnabled()
				|| jsonResponse.getString(ServicesFields.SERVICE.toString()).equals(
						ServicesFields.ENCRYPT.toString())
				|| !connectionEncrypter.isSymmetricKeySet())
			return jsonResponse;

		String uncryptedResponse = jsonResponse
				.getString(EncryptFields.ENCRYPTED_MESSAGE.toString());
		String encryptedResponse = connectionEncrypter
				.encryptResponse(uncryptedResponse);

		JSONObject jsonEncryptedResponse = new JSONObject();
		jsonEncryptedResponse.put(EncryptFields.ENCRYPTED_MESSAGE.toString(),
				encryptedResponse);

		return jsonEncryptedResponse;
	}

	private boolean isRequestEncrypted(JSONObject jsonRequest) {
		return jsonRequest.has(EncryptFields.ENCRYPTED_MESSAGE.toString());
	}

}
