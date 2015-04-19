package data_management_tests;

import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;
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

		RegisteredUser user = new RegisteredUser("Hegel", "I_AM_A_LOL",
				"hegel@epic.org");

		dataManager.saveRegistrationFields(user);

		RegisteredUser user2 = new RegisteredUser("Kant", "I_AM_TROLL",
				"kant@epic.org");

		dataManager.saveRegistrationFields(user2);
	}
}
