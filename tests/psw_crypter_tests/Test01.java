package psw_crypter_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data_management.PasswordCrypter;

/**
 * 
 * Here I test only if two equals passwords are crypted in the same way.
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

		PasswordCrypter crypter = new PasswordCrypter();
		String cryptedPassword1 = crypter.cryptPassword(password1);
		String cryptedPassword2 = crypter.cryptPassword(password2);

		System.out.println(cryptedPassword1);
		System.out.println(cryptedPassword2);

		assertEquals(password1, password2);

	}

}
