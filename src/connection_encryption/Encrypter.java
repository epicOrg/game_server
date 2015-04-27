package connection_encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class Encrypter {

	private byte[] cryptedData;
	private Key asymmetricKey;

	public Encrypter(Key asymmetricKey) {
		super();
		this.asymmetricKey = asymmetricKey;
	}

	public void encrypt(String uncryptedString) {

		byte[] uncryptedData = uncryptedString.getBytes();

		try {

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, asymmetricKey);
			cryptedData = cipher.doFinal(uncryptedData);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
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
		return cryptedData;
	}

	public String getEncryptedString() {
		return Hex.encodeHexString(cryptedData);
	}

}
