package data_management;

import java.io.IOException;

import exception.RegistrationFailedException;
import writer.IUserCreator;
import writer.LineFormatter;
import writer.UserCreator;

/**
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/03/27
 * 
 */

public class DataManager {

	private static final String PATH = "database\\";
	
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

	public void saveRegistrationFields(RegisteredUser user) throws RegistrationFailedException { 		

		try {
			userCreator.writeUser(user);
		} catch (IOException e) {
			throw new RegistrationFailedException("Writing error");
		}
	}
}
