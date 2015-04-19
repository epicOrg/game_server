package connection_encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class SymmetricKeyGenerator {

	private SecretKey key;

	public void generateKey() {

		try {

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = new SecureRandom();

			keyGenerator.init(secureRandom);
			key = keyGenerator.generateKey();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public SecretKey getKey() {
		return key;
	}

}
