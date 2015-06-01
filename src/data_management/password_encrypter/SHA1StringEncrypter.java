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

}
