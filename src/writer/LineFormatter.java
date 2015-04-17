package writer;

import data_management.RegisteredUser;

/**
 * 
 * Implementation of @see ILineFormatter. It formats file's lines.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/14
 * 
 */

public class LineFormatter implements ILineFormatter {
	
	private static final String SEPARATOR = " ";
	
	@Override
	public String formatLine(RegisteredUser user) {
		return	user.getUsername() + SEPARATOR +
				user.getPassword() + SEPARATOR +
				user.getEmail()+ "\n";
	}

}
