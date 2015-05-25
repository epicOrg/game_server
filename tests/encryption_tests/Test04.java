package encryption_tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import connection_encryption.Decrypter;
import connection_encryption.Encrypter;
import connection_encryption.SymmetricKeyGenerator;

/**
 * Encryption test for a string.
 * 
 * @author Noris
 * @date 2015/04/14
 * @see SymmetricKeyGenerator
 * @see Encrypter
 * @see Decrypter
 */

public class Test04 {

	@Test
	public void test() throws UnsupportedEncodingException, DecoderException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		Key symmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa \u00E8 una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(symmetricKey);
		encrypter.encrypt(string);

		String cryptedString = encrypter.getEncryptedString();

		String string1 = cryptedString;
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(symmetricKey);
		decrypter.decrypt(cryptedString);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");

		System.out.println("Decrypted: " + string2);
		assertEquals(string, string2);

	}

}
