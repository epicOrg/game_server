package connection_encryption;

/**
 * @author Noris
 * @version 2
 * @date 2015/05/30
 */

public class EncryptionConst {

    public static final String ASYMMETRIC_ALGORITHM = "RSA";
    public static final int ASYMMETRIC_KEYSIZE = 512;
    public static final String ASYMMETRIC_DECODE = ASYMMETRIC_ALGORITHM + "/ECB/PKCS1Padding";

    public static final String SYMMETRIC_ALGORITHM = "AES";
    public static final int SYMMETRIC_KEYSIZE = 128;

}