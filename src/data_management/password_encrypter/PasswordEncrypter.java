package data_management.password_encrypter;

/**
 * This class encrypts a password using a @see {@link StringEncrypter}.
 * 
 * @author Noris
 * @date 2015/04/16
 */

public class PasswordEncrypter {

	private String password;
	private StringEncrypter stringEncrypter;

	public PasswordEncrypter(String password, StringEncrypter stringEncrypter) {
		this.password = password;
		this.stringEncrypter = stringEncrypter;
	}

	public String cryptPassword() {
		return stringEncrypter.encryptString(password);
	}

}
