package database.writer;

import data_management.RegisteredUser;

/**
 * Formats file's lines for saving all the user data.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/14
 */

public class UserLineFormatter implements ILineFormatter {

	private static final String SEPARATOR = " ";

	@Override
	public String formatLine(RegisteredUser user) {
		return user.getUsername() + SEPARATOR + user.getEncryptedPassword()
				+ SEPARATOR + user.getEmail();
	}

}
