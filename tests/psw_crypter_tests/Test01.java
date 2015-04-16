package psw_crypter_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data_management.password_encrypter.Md5StringEncrypter;
import data_management.password_encrypter.PasswordEncrypter;

/**
 * 
 * Here I test only if two equals passwords are encrypted in the same way.
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

		PasswordEncrypter passwordEncrypter = new PasswordEncrypter(password1,
				new Md5StringEncrypter());
		String encryptedPassword1 = passwordEncrypter.cryptPassword();
		String encryptedPassword2 = passwordEncrypter.cryptPassword();

		System.out.println(encryptedPassword1);
		System.out.println(encryptedPassword2);

		assertEquals(password1, password2);

	}

}
