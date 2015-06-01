package connection_encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class Decrypter {

	private SecretKey symmetricKey;
	private byte[] decryptedData;

	public Decrypter(SecretKey symmetricKey) {
		this.symmetricKey = symmetricKey;
	}

	/**
	 * Decrypts a string using the symmetric key (obviously the key must be the
	 * same used to encrypt the string).
	 * 
	 * @param encryptedString
	 *            the encrypted string
	 */
	public void decrypt(String encryptedString) {

		byte[] encryptedData = StringConverter.stringToBytes(encryptedString);

		try {

			Cipher cipher = Cipher
					.getInstance(EncryptionConst.SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
			decryptedData = cipher.doFinal(encryptedData);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.err.println(EncryptionConst.SYMMETRIC_ALGORITHM
					+ " algorithm not found!");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the decrypted data
	 */
	public byte[] getDecryptedData() {
		return decryptedData;
	}

	/**
	 * @return the decrypted data in string format
	 */
	public String getDecryptedString() {
		try {

			return new String(decryptedData, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 not supported!");
			e.printStackTrace();
			return null;
		}
	}

}
