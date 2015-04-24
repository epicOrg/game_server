package data_management.password_encrypter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Implementation of @see {@link StringCrypter}. It encrypts a string using
 * SHA512 @see <http://en.wikipedia.org/wiki/SHA-2>.
 * 
 * @author Noris
 * @date 2015/04/24
 */

public class SHA512StringEncrypter implements StringEncrypter {

	@Override
	public String encryptString(String string) {
		return DigestUtils.sha512Hex(string);
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
	// MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
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
