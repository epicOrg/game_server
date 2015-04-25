package database.loader;

import java.io.IOException;

import data_management.RegisteredUser;
import exceptions.LoginFailedException;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public interface ILoginChecker {

	public abstract void checkUser(RegisteredUser registeredUser)
			throws LoginFailedException, IOException;

}