package database.writer;

import java.io.IOException;

import data_management.RegisteredUser;

/**
 * Interface for user data saving. It allows to switch painless through
 * different data managing systems.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public interface IDataSaver {

	/**
	 * Permanently save user data.
	 * 
	 * @param user
	 *            the user to save
	 * @throws IOException
	 *             throw if there is a communication problem with the data
	 *             managing system
	 */
	public void saveData(RegisteredUser user) throws IOException;

}