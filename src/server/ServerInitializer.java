package server;

import java.util.ArrayList;
import java.util.Collections;

import check_fields.RequestFieldChecher;
import connection_encryption.AsymmetricKeysGenerator;
import connection_encryption.ConnectionEncrypter;
import data_management.DataManager;
import data_management.RegisterDataSaver;
import data_management.RegisteredUser;
import data_management.password_encrypter.PasswordEncrypter;
import data_management.password_encrypter.SHA512StringEncrypter;
import database.Paths;
import database.loader.RegistrationFileChecker;
import database.loader.LoginFileChecker;
import database.writer.EmailFormatter;
import database.writer.EmailSaver;
import database.writer.UserSaver;
import database.writer.UserLineFormatter;
import fields_name.FieldsNames;

/**
 * Initializes some classes used by the server.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	public void init() {

		initEncryption();
		initdataManager();
		String[] services = { FieldsNames.ENCRYPT, FieldsNames.LOGIN, FieldsNames.REGISTER,
				FieldsNames.UNKNOWN, FieldsNames.GAME, FieldsNames.POLLING};
		ArrayList<String> arrayList = new ArrayList<>();
		Collections.addAll(arrayList, services);
		RequestFieldChecher.setServiceNotToBeChecked(arrayList);
	}

	private void initEncryption() {
		ConnectionEncrypter.setKeysGenerator(new AsymmetricKeysGenerator());
		RegisteredUser.setPasswordEncrypter(new PasswordEncrypter(new SHA512StringEncrypter()));

	}

	private void initdataManager() {
		DataManager dataManager = DataManager.getInstance();

		RegisterDataSaver registerDataSaver = new RegisterDataSaver(new UserSaver(
				Paths.getUsersPath(), new UserLineFormatter()), new EmailSaver(
				Paths.getEmailsPath(), new EmailFormatter()));

		dataManager.setRegisterDataSaver(registerDataSaver);

		dataManager.setChecker(new RegistrationFileChecker(Paths.getUsersPath(), Paths
				.getEmailsPath()));

		dataManager.setLoginChecker(new LoginFileChecker(Paths.getUsersPath()));
	}

}
