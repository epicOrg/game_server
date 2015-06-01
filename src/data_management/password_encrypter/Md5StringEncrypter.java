package data_management.password_encrypter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * It encrypts a string using MD5.
 * 
 * @author Noris
 * @date 2015/04/16
 * @see StringEncrypter
 */

public class Md5StringEncrypter implements StringEncrypter {

	@Override
	public String encryptString(String string) {
		return DigestUtils.md5Hex(string);
	}

}
