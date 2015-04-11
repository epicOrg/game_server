package check_fields;

/**
 * @author Noris
 * @date 2015/03/30
 * @version 6
 */

public class FieldsNames {

	/***************************************************************************
	 * SERVICE NAMES
	 **************************************************************************/

	public static final String SERVICE = "service";
	public static final String REGISTER = "register";
	public static final String LOGIN = "login";
	public static final String CALL = "call";
	public static final String UNKNOWN = "unknown";

	/***************************************************************************
	 * FIELDS NAMES
	 **************************************************************************/

	// Register
	public static final String EMAIL = "email";

	// Register, Login
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	// Call
	public static final String CALLER = "caller";
	public static final String CALLEE = "callee";
	public static final String PORT = "port";

	// Register, Login, Call
	public static final String IP_ADDRESS = "ipAddress";
	public static final String HASHCODE = "hashcode";
	public static final String NO_ERRORS = "noErrors";

	/***************************************************************************
	 * ERROR NAMES
	 **************************************************************************/

	// Register
	public static final String SHORT = "short";
	public static final String LONG = "long";
	public static final String INVALID_CHAR = "invalidChar";
	public static final String INVALID_DOMAIN = "invalidDomain";

	// Call
	public static final String OFFLINE = "offline";

	// Register, Login, Call
	public static final String INVALID = "invalidField";

}
