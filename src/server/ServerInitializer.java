package server;

import java.util.ArrayList;
import java.util.Collections;

import messages.fields_names.ServicesFields;
import check_fields.RequestFieldChecher;
import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.ConnectionEncrypter;
import connection_encryption.KeysGenerator;
import data_management.UsersDataManager;
import data_management.RegisterDataSaver;
import data_management.RegisteredUser;
import data_management.password_encrypter.PasswordEncrypter;
import data_management.password_encrypter.SHA512StringEncrypter;
import database.Paths;
import database.loader.LoginFileChecker;
import database.loader.RegistrationFileChecker;
import database.writer.EmailFormatter;
import database.writer.EmailSaver;
import database.writer.UserLineFormatter;
import database.writer.UserSaver;

/**
 * Initializes some classes used by the server.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	/**
	 * Initialize the server setting up configurations information about data
	 * managing system and encryption.
	 */
	public void init() {
		initEncryption();
		initdataManager();
		initChecks();
	}

	private void initChecks() {
		String[] services = { ServicesFields.ENCRYPT.toString(), ServicesFields.LOGIN.toString(),
				ServicesFields.REGISTER.toString(), ServicesFields.UNKNOWN.toString(),
				ServicesFields.GAME.toString(), ServicesFields.POLLING.toString() };

		ArrayList<String> arrayList = new ArrayList<>();
		Collections.addAll(arrayList, services);
		RequestFieldChecher.setServiceNotToBeChecked(arrayList);
	}

	private void initEncryption() {

		KeysGenerator keysGenerator = new AsymmetricKeysGenerator();
		keysGenerator.generateKeys();
		ConnectionEncrypter.setKeysGenerator(keysGenerator);

		RegisteredUser.setPasswordEncrypter(new PasswordEncrypter(new SHA512StringEncrypter()));
	}

	private void initdataManager() {

		UsersDataManager dataManager = UsersDataManager.getInstance();

		RegisterDataSaver registerDataSaver = new RegisterDataSaver(new UserSaver(
				Paths.getUsersPath(), new UserLineFormatter()), new EmailSaver(
				Paths.getEmailsPath(), new EmailFormatter()));

		dataManager.setRegisterDataSaver(registerDataSaver);

		dataManager.setChecker(new RegistrationFileChecker(Paths.getUsersPath(), Paths
				.getEmailsPath()));

		dataManager.setLoginChecker(new LoginFileChecker(Paths.getUsersPath()));
	}

}
