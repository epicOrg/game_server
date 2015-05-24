package database.loader;

import java.io.IOException;

import data_management.RegisteredUser;
import exceptions.LoginFailedException;

/**
 * Interface for user checking in the login. It allows to switch painless to
 * different data managing systems.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public interface ILoginChecker {

	/**
	 * Check if the user password matches with the one saved during the
	 * registration.
	 * 
	 * @param registeredUser
	 *            the user to check
	 * @throws LoginFailedException
	 *             if the username and the password doesn't match
	 * @throws IOException
	 *             if there is a communication problem with the data managing
	 *             system
	 */
	public void checkUser(RegisteredUser registeredUser) throws LoginFailedException, IOException;

}