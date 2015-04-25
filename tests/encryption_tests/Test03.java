package encryption_tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.junit.Test;

import connection_encryption.Decrypter;
import connection_encryption.Encrypter;
import connection_encryption.SymmetricKeyGenerator;

/**
 * Encryption test for a string.
 * 
 * @author Noris
 * @date 2015/03/31
 *
 */

public class Test03 {

	@Test
	public void test() throws UnsupportedEncodingException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		Key asymmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa \u00E8 una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(asymmetricKey);
		encrypter.encrypt(string);

		String string1 = encrypter.getEncryptedString();
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(asymmetricKey);
		decrypter.decrypt(string1);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");

		System.out.println("Decrypted: " + string2);
		assertEquals(string, string2);

	}

}
