package data_management.password_encrypter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Implementation of @see {@link StringCrypter}. It encrypts a string using MD5.
 * 
 * @author Noris
 * @date 2015/04/16
 */

public class Md5StringEncrypter implements StringEncrypter {

	@Override
	public String encryptString(String string) {

		try {

			byte[] passwordBytes = string.getBytes("UTF-8");

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] passwordCrypted = messageDigest.digest(passwordBytes);
			return new String(Hex.encodeHex(passwordCrypted));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
