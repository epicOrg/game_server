package data_management;

import java.io.IOException;

import database.loader.ILoginChecker;
import database.loader.IRegistrationChecker;
import database.writer.IDataSaver;
import exceptions.EmailAlreadyUsedException;
import exceptions.LoginFailedException;
import exceptions.RegistrationFailedException;
import exceptions.UsernameAlreadyUsedException;

/**
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

	public boolean checkUsername(String username) {
		try {

			checker.checkUsername(username);

		} catch (UsernameAlreadyUsedException e) {
			return false;
		}
		return true;
	}

	public boolean checkEmail(String email) {
		try {

			checker.checkEmail(email);

		} catch (EmailAlreadyUsedException e) {
			return false;
		} catch (IOException e) {
			// TODO
		}
		return true;
	}

	public boolean checkPassword(RegisteredUser registeredUser) {
		try {

			loginChecker.checkUser(registeredUser);

		} catch (LoginFailedException e) {
			return false;
		} catch (IOException e) {
			// TODO
		}

		return true;
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

	public void saveRegistrationFields(RegisteredUser user)
			throws RegistrationFailedException {

		try {
			registerDataSaver.saveData(user);
		} catch (IOException e) {
			throw new RegistrationFailedException("Writing error");
		}
	}

	public String getPath() {
		if (System.getProperty("os.name").startsWith("Windows"))
			return "database\\";

		return "database/";
	}

	public void setLoginChecker(ILoginChecker loginChecker) {
		this.loginChecker = loginChecker;
	}

}
