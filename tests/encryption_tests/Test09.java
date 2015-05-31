package encryption_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import connection_encryption.StringConverter;

/**
 * Test for {@link StringConverter}. The test fails with the string "EPICORG"...
 * 
 * @author Noris
 * @date 2015/05/31
 */

public class Test09 {

	@Test
	public void test() {

		String string1 = "EPICORG";
		String string2 = string1;

		System.out.println("String1......: " + string1);
		System.out.println("String2......: " + string2);
		System.out.println();

		byte[] byte1 = StringConverter.stringToBytes(string1);
		byte[] byte2 = StringConverter.stringToBytes(string2);

		System.out.println("Byte1........: " + byte1.hashCode());
		System.out.println("Byte2........: " + byte2.hashCode());
		System.out.println();

		String newString1 = StringConverter.bytesToString(byte1);
		String newString2 = StringConverter.bytesToString(byte2);

		System.out.println("NewString1...: " + newString1);
		System.out.println("NewString2...: " + newString2);
		System.out.println();

		assertEquals(newString1, string1);
		assertEquals(newString2, string1);
	}

}
