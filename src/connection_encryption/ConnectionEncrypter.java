package connection_encryption;

import java.security.Key;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class ConnectionEncrypter {

	private static KeysGenerator keysGenerator;
	private static boolean enabled = false;

	private Encrypter encrypter;
	private Decrypter decrypter;
	private boolean asymmetricKeySet = false;

	public static void setKeysGenerator(KeysGenerator keysGenerator) {
		ConnectionEncrypter.keysGenerator = keysGenerator;
		enabled = true;
	}

	public static Key getPublicKey() {
		return keysGenerator.getPublicKey();
	}

	public static boolean isEncryptionEnabled() {
		return enabled;
	}

	public void setAsymmetricKey(String wrappedAsymmetricKey) {
		Key asymmetricKey = new KeyUnwrapper(keysGenerator.getPrivateKey())
				.getUnwrappedKey();
		encrypter = new Encrypter(asymmetricKey);
		decrypter = new Decrypter(asymmetricKey);
		asymmetricKeySet = true;
	}

	public boolean isAsymmetricKeySet() {
		return asymmetricKeySet;
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
