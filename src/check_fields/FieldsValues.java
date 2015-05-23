package check_fields;

/**
 * @author Noris
 * @date 2015/03/30
 */

public class FieldsValues {

	/**
	 * Minimum and maximum length for the user name that an user must be insert
	 * for the registration on the server.
	 */
	public static final int USERNAME_MIN_LENGTH = 5;
	public static final int USERNAME_MAX_LENGTH = 20;

	public static final String USERNAME_FORBIDDEN_CHARS = ".*[ ]+.*";
	// public static final String USERNAME_ONLY_CHARS = "";
	public static final String USERNAME_NEEDED_CHARS = ".*[a-zA-Z]+.*";

	public static final int PASSWORD_MIN_LENGTH = 8;
	public static final int PASSWORD_MAX_LENGTH = 30;
	// public static final String PASSWORD_FORBIDDEN_CHARS = "";
	// public static final String PASSWORD_ONLY_CHARS = "";
	public static final String PASSWORD_NEEDED_CHARS = "^" + "([a-zA-Z]+[0-9][a-zA-Z0-9]*)" + "|"
			+ "([0-9]+[a-zA-Z][a-zA-Z0-9]*)" + "$";

	// public static final String[] PASSWORD_FORBIDDEN = {"password", "qwerty",
	// "123456"};

	// public static final String[] EMAIL_FORBIDDEN_DOMAIN = {"@example.com"};

	public static final int ROOMNAME_MIN_LENGTH = 3;
	public static final int ROOMNAME_MAX_LENGTH = 30;
	// public static final String ROOMNAME_FORBIDDEN_CHARS =

}
