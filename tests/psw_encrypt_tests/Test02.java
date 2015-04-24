package psw_encrypt_tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author Noris
 * @date 2015/04/24
 */

public class Test02 {

	@Test
	public void test() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		String password = "Ciao";

		byte[] stringBytes = password.getBytes("UTF-8");

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] encryptedString = messageDigest.digest(stringBytes);

		String encryptedPassword1 = new String(Hex.encodeHex(encryptedString));

		String encryptedPassword2 = DigestUtils.sha256Hex(password);

		System.out.println("PSW1: " + encryptedPassword1);
		System.out.println("PSW2: " + encryptedPassword2);

		assertEquals(encryptedPassword1, encryptedPassword2);

	}

}
