package encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author	Noris
 * @since	2015-03-30
 */

public class KeyWrapper {
	
	Key symmetricKey;
	Key publicKey;
	byte[] wrappedKey;
	
	public KeyWrapper(Key symmetricKey, Key publicKey) {
		super();
		this.symmetricKey = symmetricKey;
		this.publicKey = publicKey;
	}
	
	public void wrapKey() {
		
		try {
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.WRAP_MODE, publicKey);
			wrappedKey = cipher.wrap(symmetricKey);
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	public byte[] getWrappedKey() {
		return wrappedKey;
	}

}
