package data_management_tests;

import loader.FileChecker;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;
import game_server.ServerInitializer;

/**
 * Test @see {@link FileChecker}.
 * 
 * @author Modica
 * @author Gavina
 * @date 2015/04/17
 */
class Test02 {

	public static void main(String[] args) throws RegistrationFailedException {

		new ServerInitializer().initDataManager();

		DataManager dataManager = DataManager.getInstance();

		RegisteredUser user = new RegisteredUser("Hegel", "I_AM_A_LOL",
				"hegel@epic.org");

		System.out.println(dataManager.checkEmail(user.getEmail()));
		System.out.println(dataManager.checkUsername(user.getUsername()));

		dataManager.saveRegistrationFields(user);

		System.out.println(dataManager.checkEmail(user.getEmail()));
		System.out.println(dataManager.checkUsername(user.getUsername()));

		RegisteredUser user2 = new RegisteredUser("Kant", "I_AM_TROLL",
				"kant@epic.org");

		System.out.println(dataManager.checkEmail(user2.getEmail()));
		System.out.println(dataManager.checkUsername(user2.getUsername()));
		dataManager.saveRegistrationFields(user2);

	}
}
