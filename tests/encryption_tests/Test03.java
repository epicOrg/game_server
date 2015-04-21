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
 * [ TEST FAILED : string2 has a strange termination character. ]
 * 
 * @author Noris
 * @date 2015/03/31
 *
 */

class Test03 {

	@Test
	public void test() throws UnsupportedEncodingException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		Key asymmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa \u00E8 una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(string.getBytes(), asymmetricKey);
		encrypter.encrypt();
		int encriptionLenght = encrypter.getEncriptionLenght();

		byte[] cryptedData = encrypter.getEncryptedData();

		String string1 = new String(cryptedData, "UTF-8");
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(cryptedData, asymmetricKey);
		decrypter.decrypt(encriptionLenght);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");

		System.out.println("Decrypted: " + string2);
		assertEquals(string, string2);

	}

}
