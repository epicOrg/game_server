package encryption;

import java.security.Key;

/**
 * @author Noris
 * @date 2015/03/30
 */

// This class has a temporary name
public class NoName {

	private Key symmetricKey;
	private Key privateKey;
	private Key publicKey;
	private byte[] uncryptedData;

	public void start() {
		generateSymmetricKey();
		generateAsymmetricKeys();
		wrapSymmetricKey();
		// TODO
	}

	private void generateSymmetricKey() {
		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		symmetricKey = symmetricKeyGenerator.getKey();
	}

	private void generateAsymmetricKeys() {
		AsymmetricKeysGenerator asymmetricKeysGenerator = new AsymmetricKeysGenerator();
		asymmetricKeysGenerator.generateKeys();
		privateKey = asymmetricKeysGenerator.getPrivateKey();
		publicKey = asymmetricKeysGenerator.getPublicKey();
	}

	private void wrapSymmetricKey() {
		KeyWrapper keyWrapper = new KeyWrapper(symmetricKey, publicKey);
		uncryptedData = keyWrapper.getWrappedKey();
	}

}
