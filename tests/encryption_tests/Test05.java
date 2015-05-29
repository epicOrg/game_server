package encryption_tests;

import java.security.Key;

import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.KeyConverter;

/**
 * @author Noris
 * @date 2015/05/29
 */

class Test05 {

	public static void main(String[] args) {

		AsymmetricKeysGenerator keyGenerator = new AsymmetricKeysGenerator();
		keyGenerator.generateKeys();

		Key originalKey = keyGenerator.getPublicKey();

		System.out.println("ORIGINAL KEY : " + originalKey + "\n");

		String stringKey = KeyConverter.keyToString(originalKey);

		Key newKey = KeyConverter.stringToPublicKey(stringKey);

		System.out.println("CONVERTED KEY: " + newKey + "\n");

		if (originalKey.equals(newKey))
			System.out.println("> THE KEYS ARE EQUAL!");

	}

}
