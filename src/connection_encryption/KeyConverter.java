package connection_encryption;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/04/28
 */
public class KeyConverter {

	public static String keyToString(Key key) {
		//return Hex.encodeHexString(key.getEncoded());
		return Base64.encodeBase64String(key.getEncoded());
	}

	public static Key stringToKey(String key) {
		try {

			return new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");

		} catch (DecoderException e) {
			// TODO invalid key
			e.printStackTrace();
			System.out.println("Invalid key!");
			return null;
		}
	}

}
