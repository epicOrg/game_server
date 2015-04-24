package data_management;

import java.util.GregorianCalendar;

import data_management.password_encrypter.PasswordEncrypter;

/**
 * @author Noris
 * @date 2015/04/14
 */

public class RegisteredUser {

	private String username;
	private String password;
	private String encryptedPassword;
	private String email;
	private GregorianCalendar registrationDate;
	private static PasswordEncrypter passwordEncrypter;

	public RegisteredUser(String username, String password, String email) {
		this.username = username;
		this.email = email;
		this.password = password;
		cryptPassword();
		registrationDate = new GregorianCalendar();
	}

	private void cryptPassword() {
		this.encryptedPassword = passwordEncrypter.cryptPassword(password);
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public GregorianCalendar getRegistrationDate() {
		return registrationDate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static void setPasswordEncrypter(PasswordEncrypter passwordEncrypter) {
		RegisteredUser.passwordEncrypter = passwordEncrypter;
	}

}
