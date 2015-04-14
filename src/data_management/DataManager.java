package data_management;

import java.io.IOException;

import writer.IUserCreator;
import writer.LineFormatter;
import writer.UserCreator;

/**
 * 
 * @author Noris
 * @date 2015/03/27
 * 
 */

public class DataManager {

	private static final String PATH = "database/";

	private RegisteredUser user;

	private DataManager(RegisteredUser user) {
		this.user = user;
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

		IUserCreator userCreator = new UserCreator(PATH + user.getUsername(),
				new LineFormatter());

		try {

			userCreator.writeUser(user);
			return true;

		} catch (IOException e) {
			return false;
		}
	}
}
