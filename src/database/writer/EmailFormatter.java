package database.writer;

import data_management.RegisteredUser;

/**
 * Formats lines to be written in the e-mails file.
 * 
 * @author Micieli
 * @date 2015/04/17
 */

public class EmailFormatter implements ILineFormatter {

	@Override
	public String formatLine(RegisteredUser user) {
		return user.getEmail() + "\n";
	}

}
