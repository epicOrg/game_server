package connection_encryption;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Noris
 * @date 2015/05/31
 */

public class StringConverter {

	/**
	 * Converts an array of bytes into a string, using Base 64 encoding schemes
	 * (with a no wrap encoding).
	 * 
	 * @param bytes
	 *            an array of bytes
	 * @return a string who codify the bytes array in Base64
	 */
	public static String bytesToString(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * Converts a string encoded with Base 64 encoding schemes (in a generic
	 * encoding, for example no wrap or URL safe) into the original bytes array.
	 * 
	 * @param string
	 *            a string encoded using using Base 64 encoding schemes
	 * @return the original array of bytes
	 */
	public static byte[] stringToBytes(String string) {
		return Base64.decodeBase64(string);
	}

}
