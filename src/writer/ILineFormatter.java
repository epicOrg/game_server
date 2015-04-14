package writer;

import data_management.RegisteredUser;

/**
 * 
 * Abstraction on file's format to write.
 * 
 * @author Micieli
 * @date 2015/04/14
 * 
 */

public interface ILineFormatter {

	/**
	 * It formats file's lines.
	 * 
	 * @param username
	 * @param password
	 * @param email
	 */
	public String formatLine(RegisteredUser user);

}
