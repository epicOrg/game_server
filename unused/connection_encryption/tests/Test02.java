package encryption_tests;

import java.io.UnsupportedEncodingException;

import javax.crypto.SecretKey;

import connection_encryption.Decrypter;
import connection_encryption.Encrypter;
import connection_encryption.SymmetricKeyGenerator;

/**
 * @author Noris
 * @date 2015/03/31
 */

@Deprecated
class Test02 {

	public static void main(String[] args) throws UnsupportedEncodingException {

		SymmetricKeyGenerator symmetricKeyGenerator = new SymmetricKeyGenerator();
		symmetricKeyGenerator.generateKey();
		SecretKey symmetricKey = symmetricKeyGenerator.getKey();

		String string = "Questa \u00E8 una stringa di prova!";
		System.out.println("Uncrypted: " + string);

		Encrypter encrypter = new Encrypter(symmetricKey);
		encrypter.encrypt(string);

		byte[] cryptedData = encrypter.getEncryptedData();

		// NON FUNZIONA
		// /////////////////////////////////////////////////////////////////////
		String stringToSend = new String(cryptedData);
		byte[] incomingData = stringToSend.getBytes();
		// /////////////////////////////////////////////////////////////////////

		String string1 = new String(incomingData, "UTF-8");
		System.out.println("Encrypted: " + string1);

		Decrypter decrypter = new Decrypter(symmetricKey);
		decrypter.decrypt(new String(incomingData, "UTF-8"));

		byte[] decryptedData = decrypter.getDecryptedData();

		String string2 = new String(decryptedData, "UTF-8");
		System.out.println("Decrypted: " + string2);

	}

}
