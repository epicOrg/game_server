package encryption_tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import encryption.Decrypter;
import encryption.Encrypter;
import encryption.SymmetricKeyGenerator;

/**
 * Encryption test for a string.
 * [ TEST FAILED : string2 has a strange termination character. ]
 * 
 * @author Noris
 * @date 2015/04/14
 *
 */

public class Test04 {

	@Test
	public void test() throws UnsupportedEncodingException, DecoderException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		Key asymmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa è una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(string.getBytes(), asymmetricKey);
		encrypter.encrypt();
		int encriptionLenght = encrypter.getEncriptionLenght();

		String cryptedString = encrypter.getEncryptedString();

		String string1 = cryptedString;
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(cryptedString, asymmetricKey);
		decrypter.decrypt(encriptionLenght);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");

		System.out.println("Decrypted: " + string2 + "|||||||");
		assertEquals(string, string2);

	}

}
