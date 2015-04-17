package data_management;

import java.io.IOException;

import writer.IUserCreator;
import exception.RegistrationFailedException;

/**
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/03/27
 * 
 */

public class DataManager {

	private IUserCreator userCreator;
	private static DataManager instance = new DataManager();

	private DataManager() {
	}

	public boolean checkUsername(String username) {
		// TODO
		return true;
	}

	public boolean checkEmail(String email) {
		// TODO
		return true;
	}

	public boolean checkPassword(String username) {
		// TODO
		return true;
	}

	public static DataManager getInstance() {
		return instance;
	}
	
	public void setUserCreator(IUserCreator userCreator) {
		this.userCreator = userCreator;
	}

	public void saveRegistrationFields(RegisteredUser user)
			throws RegistrationFailedException {

		try {
			userCreator.saveUser(user);
		} catch (IOException e) {
			throw new RegistrationFailedException("Writing error");
		}
	}
	
	public String getPath() {
		if (System.getProperty("os.name").startsWith("Windows"))
			return "database\\";
		
		return "database/";
	}
}
