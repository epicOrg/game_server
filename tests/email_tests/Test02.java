package email_tests;

import email_confirmation.EmailCodeGenerator;
import email_confirmation.EmailConfirmation;

/**
 * Random email confirmation code generation test.
 * 
 * @author Noris
 * @date 2015/05/05
 * @see EmailConfirmation
 */

public class Test02 {

	private static final int NUM = 100;

	public static void main(String[] args) {

		for (int i = 0; i < NUM; i++) {
			System.out.println(EmailCodeGenerator.generateRandomCode());
		}
	}

}
