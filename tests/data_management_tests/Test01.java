package data_management_tests;

import data_management.DataManager;
import data_management.RegisteredUser;
import exception.RegistrationFailedException;
import game_server.ServerInitializer;

/**
 * File saving test with DataManager.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */
public class Test01 {

	public static void main(String[] args) throws RegistrationFailedException {

		new ServerInitializer().initDataManager();

		DataManager dataManager = DataManager.getInstance();

		RegisteredUser user = new RegisteredUser("Tony", "pass12345678",
				"example@epic.org");

		dataManager.saveRegistrationFields(user);

		RegisteredUser user2 = new RegisteredUser("Licia", "pass12343366",
				"example2@epic.org");

		dataManager.saveRegistrationFields(user2);
	}
}
