package psw_encrypt_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data_management.password_encrypter.Md5StringEncrypter;
import data_management.password_encrypter.PasswordEncrypter;

/**
 * Tests if two equals passwords are encrypted in the same way.
 * 
 * @author Noris
 * @date 2015/04/14
 * 
 */

public class Test01 {

	@Test
	public void test() {

		String password1 = "OPS = Ops, Password Stupida!";
		String password2 = password1;

		PasswordEncrypter passwordEncrypter1 = new PasswordEncrypter(
				new Md5StringEncrypter());
		String encryptedPassword1 = passwordEncrypter1.cryptPassword(password1);

		PasswordEncrypter passwordEncrypter2 = new PasswordEncrypter(
				new Md5StringEncrypter());
		String encryptedPassword2 = passwordEncrypter2.cryptPassword(password2);

		System.out.println("Password1: <" + encryptedPassword1 + ">");
		System.out.println("Password2: <" + encryptedPassword2 + ">");

		assertEquals(password1, password2);

	}

}
