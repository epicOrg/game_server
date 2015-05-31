package connection_encryption;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Noris
 * @date 2015/05/31
 */

public class StringConverter {

	public static String bytesToString(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] stringToBytes(String string) {
		return Base64.decodeBase64(string);
	}

}
