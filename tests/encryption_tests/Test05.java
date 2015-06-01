package encryption_tests;

import static org.junit.Assert.assertEquals;

import java.security.Key;

import org.junit.Test;

import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.KeyConverter;

/**
 * Generates an asymmetric public key and convert it into a string using
 * {@link KeyConverter}. Then it re-converts the string into a key. Finally it
 * checks if the keys are equals (through their hash-code).
 * 
 * @author Noris
 * @date 2015/05/29
 */

public class Test05 {

	@Test
	public void test() {

		AsymmetricKeysGenerator keyGenerator = new AsymmetricKeysGenerator();
		keyGenerator.generateKeys();

		Key originalKey = keyGenerator.getPublicKey();

		System.out.println("> ORIGINAL KEY....: " + originalKey + "\n");

		String stringKey = KeyConverter.keyToString(originalKey);

		System.out.println("> STRING KEY......: " + stringKey + "\n");

		Key newKey = KeyConverter.stringToPublicKey(stringKey);

		System.out.println("> CONVERTED KEY...: " + newKey + "\n");

		assertEquals(originalKey.hashCode(), newKey.hashCode());

	}

}
