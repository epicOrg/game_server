package connection_encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Use it to unwrap a symmetric key crypted with a public asymmetric key.
 * 
 * @author Noris
 * @date 2015/03/30
 */

public class KeyUnwrapper {

	private Key privateKey;
	private Key symmetricKey;

	public KeyUnwrapper(Key privateKey) {
		super();
		this.privateKey = privateKey;
	}

	public void unwrapKey(String wrappedKeyString) {

		byte[] wrappedKeyData = null;

		try {
			wrappedKeyData = Hex.decodeHex(wrappedKeyString.toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.UNWRAP_MODE, privateKey);
			symmetricKey = cipher.unwrap(wrappedKeyData, "AES", Cipher.SECRET_KEY);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Key getUnwrappedKey() {
		return symmetricKey;
	}

}
