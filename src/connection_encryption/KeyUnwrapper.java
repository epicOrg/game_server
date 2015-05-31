package connection_encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Unwraps a symmetric key encrypted with a public asymmetric key.
 * 
 * @author Noris
 * @date 2015/03/30
 */

public class KeyUnwrapper {

	private PrivateKey privateKey;
	private SecretKey symmetricKey;

	public KeyUnwrapper(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public void unwrapKey(String wrappedKeyString) {

		byte[] wrappedKeyData = StringConverter.stringToBytes(wrappedKeyString);

		try {

			Cipher cipher = Cipher.getInstance(EncryptionConst.ASYMMETRIC_DECODE);
			cipher.init(Cipher.UNWRAP_MODE, privateKey);
			symmetricKey = (SecretKey) cipher.unwrap(wrappedKeyData,
					EncryptionConst.SYMMETRIC_ALGORITHM, Cipher.SECRET_KEY);

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

	public SecretKey getUnwrappedKey() {
		return symmetricKey;
	}

}
