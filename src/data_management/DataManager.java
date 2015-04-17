package data_management;

import java.io.IOException;

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

	private RegisteredUser user;
	private UserCreator userCreator;

	private DataManager(RegisteredUser user) {
		this.user = user;
		userCreator = new UserCreator(PATH , new LineFormatter());
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

	public boolean saveRegistrationFields() { 		

		try {

			userCreator.writeUser(user);
			return true;

		} catch (IOException e) {
			return false;
		}
	}
}
