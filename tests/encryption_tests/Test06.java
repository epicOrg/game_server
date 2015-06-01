package encryption_tests;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import connection_encryption.KeyConverter;

/**
 * Generates a symmetric key and convert it into a string using
 * {@link KeyConverter}. Then it re-converts the string into a key. Finally it
 * checks if the keys are equals (through their hash-code).
 * 
 * @author Noris
 * @date 2015/05/29
 */

class Test06 {

	public static void main(String[] args) {

		Key originalKey = null;

		try {
			originalKey = KeyGenerator.getInstance("AES").generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println("ORIGINAL KEY : " + originalKey + "\n");

		String stringKey = KeyConverter.keyToString(originalKey);

		Key newKey = KeyConverter.stringToSymmetricKey(stringKey);

		System.out.println("CONVERTED KEY: " + newKey + "\n");

		if (originalKey.hashCode() == newKey.hashCode())
			System.out.println("> THE KEYS ARE EQUAL!");

	}

}
