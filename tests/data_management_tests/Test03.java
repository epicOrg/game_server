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
class Test03 {

	public static void main(String[] args) throws RegistrationFailedException {

		new ServerInitializer().init();

		DataManager dataManager = DataManager.getInstance();

		RegisteredUser user = new RegisteredUser("marx", "proletarian707",
				"marx@proletarian.org");

		System.out.println(dataManager.checkPassword(user));

		dataManager.saveRegistrationFields(user);

		System.out.println(dataManager.checkPassword(user));

	}
}
