package connection_encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class Decrypter {

	byte[] cryptedData;
	byte[] decryptedData;
	private Key asymmetricKey;

	public Decrypter(byte[] cryptedData, Key asymmetricKey) {
		this.cryptedData = cryptedData;
		this.asymmetricKey = asymmetricKey;
	}

	// TODO Adjust throws
	public Decrypter(String cryptedString, Key asymmetricKey)
			throws DecoderException {
		
		this.cryptedData = Hex.decodeHex(cryptedString.toCharArray());
		this.asymmetricKey = asymmetricKey;
	}

	public void decrypt(int encriptionLenght) {

		try {

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, asymmetricKey);
			decryptedData = new byte[cipher.getOutputSize(encriptionLenght)];

			int decriptionLenght = cipher.update(cryptedData, 0,
					encriptionLenght, decryptedData, 0);

			decriptionLenght += cipher.doFinal(decryptedData, decriptionLenght);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShortBufferException e) {
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
