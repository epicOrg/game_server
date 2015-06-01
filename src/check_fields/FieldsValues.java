package check_fields;

/**
 * Defines the checking parameters.
 * 
 * @author Noris
 * @date 2015/03/30
 */

public class FieldsValues {

	/**
	 * Minimum length for the user name that an user must be insert for the
	 * registration on the server.
	 */
	public static final int USERNAME_MIN_LENGTH = 5;

	/**
	 * Maximum length for the user name that an user must be insert for the
	 * registration on the server.
	 */
	public static final int USERNAME_MAX_LENGTH = 20;

	/**
	 * Characters that can not be used for the user name.
	 */
	public static final String USERNAME_FORBIDDEN_CHARS = ".*[ ]+.*";

	/*
	 * Only these characters can be used for an user name.
	 */
	// public static final String USERNAME_ONLY_CHARS = "";

	/**
	 * The user name must contains the characters defined by this regular
	 * expression.
	 */
	public static final String USERNAME_NEEDED_CHARS = ".*[a-zA-Z]+.*";

	/**
	 * Minimum length for the password that an user must be insert for the
	 * registration on the server.
	 */
	public static final int PASSWORD_MIN_LENGTH = 8;

	/**
	 * Maximum length for the password that an user must be insert for the
	 * registration on the server.
	 */
	public static final int PASSWORD_MAX_LENGTH = 35;

	/*
	 * Characters that can not be used for the password.
	 */
	// public static final String PASSWORD_FORBIDDEN_CHARS = "";

	/*
	 * Only these characters can be used for a password.
	 */
	// public static final String PASSWORD_ONLY_CHARS = "";

	/**
	 * The password must contains the characters defined by this regular
	 * expression.
	 */
	public static final String PASSWORD_NEEDED_CHARS = "^"
			+ "([a-zA-Z]+[0-9][a-zA-Z0-9]*)" + "|"
			+ "([0-9]+[a-zA-Z][a-zA-Z0-9]*)" + "$";

	/*
	 * These passwords can not be used.
	 */
	// public static final String[] PASSWORD_FORBIDDEN = {"password", "qwerty",
	// "123456"};

	/*
	 * These email domains can not be used.
	 */
	// public static final String[] EMAIL_FORBIDDEN_DOMAIN = {"@example.com"};

	/**
	 * Minimum length for the room name that an user must be insert when he
	 * create a room.
	 */
	public static final int ROOMNAME_MIN_LENGTH = 3;

	/**
	 * Maximum length for the room name that an user must be insert when he
	 * create a room.
	 */
	public static final int ROOMNAME_MAX_LENGTH = 30;

	/*
	 * Characters that can not be used for a room name.
	 */
	// public static final String ROOMNAME_FORBIDDEN_CHARS =

}
