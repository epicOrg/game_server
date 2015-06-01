package connection_encryption;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Interface for the asymmetric cryptography. The classes who implements this
 * interface must provided a method that generates two asymmetric keys: the
 * private key can decrypt the data encrypted with the public key, but not the
 * vice versa (the public key was used only to encrypt data).
 * 
 * @author Noris
 * @date 2015/04/25
 */

public interface KeysGenerator {

	/**
	 * Generates two asymmetric keys (public and private key).
	 */
	public void generateKeys();

	/**
	 * @return the public key: with this key you can encrypt data that can be
	 *         only decrypted with the private key
	 */
	public PublicKey getPublicKey();

	/**
	 * @return the private key: you can use this key to decrypt data encrypted
	 *         with the public key
	 */
	public PrivateKey getPrivateKey();

}
