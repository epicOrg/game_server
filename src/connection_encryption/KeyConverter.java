package connection_encryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.SecretKeySpec;

/**
 * Some useful Key conversion methods.
 * 
 * @author Noris
 * @date 2015/04/28
 * @see StringConverter
 * @see Key
 */

public class KeyConverter {

	/**
	 * Converts a Key into a string.
	 *
	 * @param key
	 *            a generic Key
	 * @return a string encoding the Key
	 */
	public static String keyToString(Key key) {
		return StringConverter.bytesToString(key.getEncoded());
	}

	/**
	 * Convert a string, that encodes a PublicKey, into the PublicKey.
	 *
	 * @param key
	 *            a string that encodes a PublicKey
	 * @return the PublicKey
	 */
	public static PublicKey stringToPublicKey(String key) {

		byte[] decodedKey = StringConverter.stringToBytes(key);

		X509EncodedKeySpec eks = new X509EncodedKeySpec(decodedKey);

		KeyFactory keyFactor = null;

		try {
			keyFactor = KeyFactory.getInstance(EncryptionConst.ASYMMETRIC_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			System.err.println(EncryptionConst.ASYMMETRIC_ALGORITHM + " algorithm not found!");
			e.printStackTrace();
		}

		try {
			return keyFactor.generatePublic(eks);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Convert a string, that encodes a generic Key, into the Key.
	 *
	 * @param key
	 *            a string that encodes a Key
	 * @return the Key
	 */
	public static Key stringToSymmetricKey(String key) {

		byte[] decodedKey = StringConverter.stringToBytes(key);
		return new SecretKeySpec(decodedKey, EncryptionConst.SYMMETRIC_ALGORITHM);

	}

}
