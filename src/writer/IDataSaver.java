package writer;

import java.io.IOException;

import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * Data export interface, regardless of saving support type.
 * 
 * @author Micieli
 * @date 2015/04/14
 * 
 */

public interface IDataSaver {

	/**
	 * Data exporter.
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @throws IOException
	 * @throws RegistrationFailedException
	 */
	public abstract void saveData(RegisteredUser user) throws IOException;

}