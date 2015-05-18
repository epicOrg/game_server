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
	private boolean symmetricKeySet = false;

	public static void setKeysGenerator(KeysGenerator keysGenerator) {
		ConnectionEncrypter.keysGenerator = keysGenerator;
		enabled = true;
	}

	public static String getPublicKey() {
		return KeyConverter.keyToString(keysGenerator.getPublicKey());
	}

	public static boolean isEncryptionEnabled() {
		return enabled;
	}

	public void setAsymmetricKey(String wrappedSymmetricKey) {
		KeyUnwrapper keyUnwrapper = new KeyUnwrapper(keysGenerator.getPrivateKey());
		keyUnwrapper.unwrapKey(wrappedSymmetricKey);
		Key symmetricKey = keyUnwrapper.getUnwrappedKey();
		encrypter = new Encrypter(symmetricKey);
		decrypter = new Decrypter(symmetricKey);
		symmetricKeySet = true;
	}

	public boolean isSymmetricKeySet() {
		return symmetricKeySet;
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
