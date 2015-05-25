package data_management_tests;

import server.ServerInitializer;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * File saving test with {@link DataManager}.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 * @see DataManager
 * @see RegisteredUser
 */
class Test01 {

	public static void main(String[] args) throws RegistrationFailedException {

		new ServerInitializer().init();

		DataManager dataManager = DataManager.getInstance();

		RegisteredUser user = new RegisteredUser("Hegel", "I_AM_A_LOL",
				"hegel@epic.org");

		dataManager.saveRegistrationFields(user);

		RegisteredUser user2 = new RegisteredUser("Kant", "I_AM_TROLL",
				"kant@epic.org");

		dataManager.saveRegistrationFields(user2);
	}
}
