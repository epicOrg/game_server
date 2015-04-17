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
public class Test03 {

	public static void main(String[] args) throws RegistrationFailedException {

		new ServerInitializer().initDataManager();

		DataManager dataManager = DataManager.getInstance();

		RegisteredUser user = new RegisteredUser("marx", "I_AM_A_LOL",
				"marx@proletarian.org");

		System.out.println(dataManager.checkPassword(user));

		dataManager.saveRegistrationFields(user);

		System.out.println(dataManager.checkPassword(user));

	}
}
