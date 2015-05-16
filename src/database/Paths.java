package database;

/**
 * @author Noris
 * @date 2015/04/25
 */

public final class Paths {

	private static final String DATABASE_PATH = "database";
	private static final String USERS_PATH = "users";
	private static final String EMAILS_PATH = "emails";
	private static final String EMAILS_FILENAME = "emails";
	private static final String CONFIRM_PATH = "confirm";

	private static final String WIN_SEPARATOR = "\\";
	private static final String UNIX_SEPARATOR = "/";

	private static String separator;

	public static String getDatabasePath() {
		if (System.getProperty("os.name").startsWith("Windows"))
			separator = WIN_SEPARATOR;
		else
			separator = UNIX_SEPARATOR;

		return DATABASE_PATH + separator;
	}

	public static String getUsersPath() {
		return getDatabasePath() + USERS_PATH + separator;
	}

	public static String getEmailsPath() {
		return getDatabasePath() + EMAILS_PATH + separator + EMAILS_FILENAME;
	}

	public static String getConfirmPath() {
		return getDatabasePath() + USERS_PATH + separator + CONFIRM_PATH + separator;
	}

}
