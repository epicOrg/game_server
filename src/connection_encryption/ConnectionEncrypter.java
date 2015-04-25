package connection_encryption;

import java.security.Key;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class ConnectionEncrypter {

	private static KeysGenerator keysGenerator;

	private Encrypter encrypter;
	private Decrypter decrypter;

	public static void setKeysGenerator(KeysGenerator keysGenerator) {
		ConnectionEncrypter.keysGenerator = keysGenerator;
	}

	public static Key getPublicKey() {
		return keysGenerator.getPublicKey();
	}

	public void setAsymmetricKey(String wrappedAsymmetricKey) {
		Key asymmetricKey = new KeyUnwrapper(keysGenerator.getPrivateKey())
				.getUnwrappedKey();
		encrypter = new Encrypter(asymmetricKey);
		decrypter = new Decrypter(asymmetricKey);
	}

	public String encryptResponse(String response) {
		encrypter.encrypt(response);
		return encrypter.getEncryptedString();
	}

	public String decryptRequest(String request) {
		decrypter.decrypt(request);
		return decrypter.getDecryptedString();
	}

}
