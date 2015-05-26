package data_management.password_encrypter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * It encrypts a string using SHA1.
 * 
 * @author Noris
 * @date 2015/04/24
 * @see StringEncrypter
 */

public class SHA1StringEncrypter implements StringEncrypter {

	@Override
	public String encryptString(String string) {
		return DigestUtils.sha1Hex(string);
	}

	/*
	 * Alternative way, with java standard library.
	 */
	// @Override
	// public String encryptString(String string) {
	//
	// try {
	//
	// byte[] stringBytes = string.getBytes("UTF-8");
	//
	// MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
	// byte[] encryptedString = messageDigest.digest(stringBytes);
	//
	// return new String(Hex.encodeHex(encryptedString));
	//
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchAlgorithmException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

}
