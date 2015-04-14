package encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class Encrypter {

	byte[] uncryptedData;
	byte[] cryptedData;
	int encriptionLenght;
	private Key asymmetricKey;

	public Encrypter(byte[] uncryptedData, Key asymmetricKey) {
		super();
		this.uncryptedData = uncryptedData;
		this.asymmetricKey = asymmetricKey;
	}

	public void encrypt() {

		try {

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, asymmetricKey);
			cryptedData = new byte[cipher.getOutputSize(uncryptedData.length)];

			encriptionLenght = cipher.update(uncryptedData, 0,
					uncryptedData.length, cryptedData, 0);

			encriptionLenght += cipher.doFinal(cryptedData, encriptionLenght);

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

	public byte[] getEncryptedData() {
		return cryptedData;
	}
	
	public String getEncryptedString() {
		return new String(Hex.encodeHex(cryptedData));
	}

	public int getEncriptionLenght() {
		return encriptionLenght;
	}

}
