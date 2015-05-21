package database.writer;

import java.io.IOException;

import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * Interface for user data saving.
 * It allows to switch painless to different data managing systems 
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public interface IDataSaver {

	/**
	 * 
	 * Permanently save user data 
	 * 
	 * @param user				the user to be saved
	 * @throws IOException		if there is a problem communicating with the data managing system
	 */
	public void saveData(RegisteredUser user) throws IOException;

}