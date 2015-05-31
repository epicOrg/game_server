package encryption_tests;

import java.io.UnsupportedEncodingException;

import javax.crypto.SecretKey;

import connection_encryption.Decrypter;
import connection_encryption.Encrypter;
import connection_encryption.SymmetricKeyGenerator;

/**
 * @author Noris
 * @date 2015/03/30
 * @see SymmetricKeyGenerator
 * @see Encrypter
 * @see Decrypter
 */

class Test01 {

	public static void main(String[] args) throws UnsupportedEncodingException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		SecretKey symmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa \u00E8 una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(symmetricKey);
		encrypter.encrypt(string);

		String string1 = encrypter.getEncryptedString();
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(symmetricKey);
		decrypter.decrypt(string1);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");
		System.out.println("Decrypted: " + string2);

	}

}
