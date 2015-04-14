package data_management;

import java.util.GregorianCalendar;

/**
 * @author Noris
 * @date 2015/04/14
 */

public class RegisteredUser {

	private String username;
	private String password;
	private String email;
	private GregorianCalendar registrationDate;

	public RegisteredUser(String username, String password, String email) {
		this.username = username;
		this.email = email;
		cryptPassword(password);
		registrationDate = new GregorianCalendar();
	}

	private void cryptPassword(String password) {
		PasswordCrypter passwordCrypter = new PasswordCrypter();
		this.password = passwordCrypter.cryptPassword(password);
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
}
