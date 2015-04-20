package data_management.password_encrypter;

/**
 * This class encrypts a password using a @see {@link StringEncrypter}.
 * 
 * @author Noris
 * @date 2015/04/16
 */

public class PasswordEncrypter {

	private StringEncrypter stringEncrypter;

	public PasswordEncrypter( StringEncrypter stringEncrypter) {
		this.stringEncrypter = stringEncrypter;
	}

	public String cryptPassword(String password) {
		return stringEncrypter.encryptString(password);
	}
}
