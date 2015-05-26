package data_management;

import java.io.IOException;

import services.Login;
import services.Register;
import database.loader.ILoginChecker;
import database.loader.IRegistrationChecker;
import database.writer.IDataSaver;
import exceptions.EmailAlreadyUsedException;
import exceptions.LoginFailedException;
import exceptions.RegistrationFailedException;
import exceptions.UsernameAlreadyUsedException;

/**
 * 
 * <code>DataManager</code> provides methods to manages users data.
 * Allows the {@link Register} service to save users data and to check them.
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/03/27
 */

public class DataManager {

	private static DataManager instance = new DataManager();

	private IDataSaver registerDataSaver;
	private IRegistrationChecker checker;
	private ILoginChecker loginChecker;

	private DataManager() {
	}
	
	/**
	 * 
	 * Checks if exist another user registered with the same username
	 * 
	 * @param username  the username to check
	 * @return true if the username is available, false otherwise
	 * @see IRegistrationChecker#checkUsername(String)
	 */
	public boolean checkUsername(String username) {
		try {

			checker.checkUsername(username);

		} catch (UsernameAlreadyUsedException e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Checks if exist another user registered with the same email
	 * 
	 * @param 		 email the email to check
	 * @return		 true if the aren't user registered with the given email
	 * @see IRegistrationChecker#checkEmail(String)
	 */
	public boolean checkEmail(String email) {
		try {

			checker.checkEmail(email);

		} catch (EmailAlreadyUsedException e) {
			return false;
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * Checks if the password given by the user mathes with the one provided during registration
	 * 
	 * @param registeredUser
	 * @return
	 * @see ILoginChecker#checkUser(RegisteredUser)
	 * @see Login
	 */
	public boolean checkPassword(RegisteredUser registeredUser) {
		try {

			loginChecker.checkUser(registeredUser);

		} catch (LoginFailedException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * 
	 * Permanently saves user data (username, email and password) in the server database.
	 * 
	 * @param user								the registered user to be saved
	 * @throws RegistrationFailedException		if an error occurred during writing into database
	 * @see RegisteredUser
	 * @see IDataSaver
	 */
	public void saveRegistrationFields(RegisteredUser user)
			throws RegistrationFailedException {

		try {
			registerDataSaver.saveData(user);
		} catch (IOException e) {
			throw new RegistrationFailedException("Writing error");
		}
	}

	public static DataManager getInstance() {
		return instance;
	}

	public void setRegisterDataSaver(IDataSaver registerDataSaver) {
		this.registerDataSaver = registerDataSaver;
	}

	public void setChecker(IRegistrationChecker checker) {
		this.checker = checker;
	}

	public void setLoginChecker(ILoginChecker loginChecker) {
		this.loginChecker = loginChecker;
	}
}
