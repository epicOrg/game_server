package connection_encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class KeyUnwrapper {

	byte[] wrappedKey;
	Key privateKey;
	Key symmetricKey;

	public KeyUnwrapper(byte[] wrappedKey, Key privateKey) {
		super();
		this.wrappedKey = wrappedKey;
		this.privateKey = privateKey;
	}

	public void unwrapKey() {

		try {

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.UNWRAP_MODE, privateKey);
			symmetricKey = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

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

}
