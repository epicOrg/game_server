package server;

import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.ConnectionEncrypter;
import data_management.DataManager;
import data_management.RegisterDataSaver;
import data_management.RegisteredUser;
import data_management.password_encrypter.PasswordEncrypter;
import data_management.password_encrypter.SHA512StringEncrypter;
import database.Paths;
import database.loader.FileChecker;
import database.loader.LoginChecker;
import database.writer.EmailFormatter;
import database.writer.EmailSaver;
import database.writer.UserCreator;
import database.writer.UserLineFormatter;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	public void initDataManager() {

		ConnectionEncrypter.setKeysGenerator(new AsymmetricKeysGenerator());

		DataManager dataManager = DataManager.getInstance();

		RegisterDataSaver registerDataSaver = new RegisterDataSaver(
				new UserCreator(Paths.getUsersPath(), new UserLineFormatter()),
				new EmailSaver(Paths.getEmailsPath(), new EmailFormatter()));

		dataManager.setRegisterDataSaver(registerDataSaver);

		dataManager.setChecker(new FileChecker(Paths.getEmailsPath()));

		dataManager.setLoginChecker(new LoginChecker(Paths.getUsersPath()));

		RegisteredUser.setPasswordEncrypter(new PasswordEncrypter(
				new SHA512StringEncrypter()));
	}

}
