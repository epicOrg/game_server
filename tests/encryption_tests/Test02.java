package encryption_tests;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import encryption.Decrypter;
import encryption.Encrypter;
import encryption.SymmetricKeyGenerator;

/**
 * @author Noris
 * @date 2015/03/31
 */

public class Test02 {

	public static void main(String[] args) throws UnsupportedEncodingException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		Key asymmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa è una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(string.getBytes(), asymmetricKey);
		encrypter.encrypt();
		int encriptionLenght = encrypter.getEncriptionLenght();

		byte[] cryptedData = encrypter.getEncryptedData();

		// NON FUNZIONA
		// /////////////////////////////////////////////////////////////////////
		String stringToSend = new String(cryptedData);
		byte[] incomingData = stringToSend.getBytes();
		// /////////////////////////////////////////////////////////////////////

		String string1 = new String(incomingData, "UTF-8");
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(incomingData, asymmetricKey);
		decrypter.decrypt(encriptionLenght);

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");
		System.out.println("Decrypted: " + string2);

	}

}
