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
 * <code>DataManager</code> provides methods to manages users data. Allows the
 * {@link Register} service to save users data and to check them.
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/03/27
 */

public class UsersDataManager {

	private static UsersDataManager instance = new UsersDataManager();

	private IDataSaver registerDataSaver;
	private IRegistrationChecker checker;
	private ILoginChecker loginChecker;

	private UsersDataManager() {
	}

	/**
	 * 
	 * Checks if exist another user registered with the same name.
	 * 
	 * @param username
	 *            the user name to check
	 * @return true if the user name is available, false otherwise
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
	 * Checks if exist another user registered with the same e-mail.
	 * 
	 * @param email
	 *            the email to check
	 * @return true if the aren't user registered with the given email
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
	 * Checks if the password given by the user matches with the one provided
	 * during the registration.
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
	 * Permanently saves user data (username, email and password) in the server
	 * database.
	 * 
	 * @param user
	 *            the registered user to be saved
	 * @throws RegistrationFailedException
	 *             if an error occurred during writing into database
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

	public static UsersDataManager getInstance() {
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
