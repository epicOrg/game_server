package connection_encryption;

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

public class Encrypter {

	private byte[] encryptedData;
	private SecretKey symmetricKey;

	public Encrypter(SecretKey symmetricKey) {
		super();
		this.symmetricKey = symmetricKey;
	}

	/**
	 * Encrypts a string using a symmetric key.
	 * 
	 * @param uncryptedString
	 *            the string to encrypt
	 */
	public void encrypt(String uncryptedString) {

		byte[] uncryptedData = uncryptedString.getBytes();

		try {

			Cipher cipher = Cipher.getInstance(EncryptionConst.SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
			encryptedData = cipher.doFinal(uncryptedData);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.err.println(EncryptionConst.SYMMETRIC_ALGORITHM + " algorithm not found!");
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
	 * @return the encrypted data.
	 */
	public byte[] getEncryptedData() {
		return encryptedData;
	}

	/**
	 * @return the encrypted data in string format.
	 */
	public String getEncryptedString() {
		return StringConverter.bytesToString(encryptedData);
	}

}
