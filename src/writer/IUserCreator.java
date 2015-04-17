package writer;

import java.io.IOException;

import data_management.RegisteredUser;

/**
 * 
 * Data export interface, regardless of saving support type.
 * 
 * @author Micieli
 * @date 2015/04/14
 * 
 */

public interface IUserCreator {

	/**
	 * Data exporter.
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @throws IOException
	 */
	public abstract void saveUser(RegisteredUser user) throws IOException;

}