package data_management.password_encrypter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * It encrypts a string using SHA256..
 * 
 * @author Noris
 * @date 2015/04/24
 * @see StringEncrypter
 */

class SHA256StringEncrypter implements StringEncrypter {

	@Override
	public String encryptString(String string) {
		return DigestUtils.sha256Hex(string);
	}

}
