package database.writer;

import data_management.RegisteredUser;

/**
 * Abstraction on file's format to write.
 * 
 * @author Micieli
 * @date 2015/04/14
 * 
 */

public interface ILineFormatter {

	/**
	 * Formats line to be directly written in a file
	 * 
	 * @param user		the <code>RegisteredUser</code> that contains data to format
	 * @return			a formatted string containing user information
	 */
	public String formatLine(RegisteredUser user);

}
