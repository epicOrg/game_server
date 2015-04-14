package data_management;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Noris
 * @date 2015/04/14
 */

public class PasswordCrypter {

	public String cryptPassword(String password) {

		try {

			byte[] passwordBytes = password.getBytes("UTF-8");

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