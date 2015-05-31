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

	public byte[] getEncryptedData() {
		return encryptedData;
	}

	public String getEncryptedString() {
		return StringConverter.bytesToString(encryptedData);
	}

}
