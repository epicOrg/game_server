package connection_encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class Decrypter {

	private Key asymmetricKey;
	private byte[] decryptedData;

	public Decrypter(Key asymmetricKey) {
		this.asymmetricKey = asymmetricKey;
	}

	public void decrypt(String encryptedString) {
		
		byte[] encryptedData = null;
		
		try {
			encryptedData = Hex.decodeHex(encryptedString.toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, asymmetricKey);
			decryptedData = cipher.doFinal(encryptedData);

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

	public byte[] getDecryptedData() {
		return decryptedData;
	}

	public String getDecryptedString() {
		try {

			return new String(decryptedData, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
