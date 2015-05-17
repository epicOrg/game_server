package email_confirmation;

import java.util.Random;

/**
 * @author Noris
 * @date 2015/05/05
 */

public class EmailConfirmation {

	private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int CODE_LENGTH = 20;

	public static String generateRandomCode() {

		StringBuilder stringBuilder = new StringBuilder(CODE_LENGTH);
		Random random = new Random();

		for (int i = 0; i < CODE_LENGTH; i++) {
			stringBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}

		return stringBuilder.toString();
	}

	public static String getURL(String username) {
		return "reg=" + username + "&" + generateRandomCode();
	}
}
