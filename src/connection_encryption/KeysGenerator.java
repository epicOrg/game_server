package connection_encryption;

import java.security.Key;

/**
 * @author Noris
 * @date 2015/04/25
 */

public interface KeysGenerator {

	public void generateKeys();

	public Key getPublicKey();

	public Key getPrivateKey();

}
