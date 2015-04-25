package connection_encryption;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class AsymmetricKeysGenerator implements KeysGenerator {

	private static final int KEYSIZE = 256;

	private Key publicKey;
	private Key privateKey;

	public void generateKeys() {

		try {

			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("RSA");
			SecureRandom secureRandom = new SecureRandom();

			keyPairGenerator.initialize(KEYSIZE, secureRandom);

			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Key getPublicKey() {
		return publicKey;
	}

	public Key getPrivateKey() {
		return privateKey;
	}

}
