package writer;

import data_management.RegisteredUser;

/**
 * Implementation of @see ILineFormatter
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
