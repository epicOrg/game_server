package data_management_tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import server.ServerInitializer;
import data_management.UsersDataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * File saving test with {@link UsersDataManager}.
 * 
 * @author Micieli
 * @author Noris
 * @author Gavina
 * @date 2015/04/17
 * @see UsersDataManager
 * @see RegisteredUser
 */

public class Test03 {

	private UsersDataManager dataManager;
	private RegisteredUser user;
	private ServerInitializer serverInitializer = new ServerInitializer();

	@Before
	public void setUp() {

		serverInitializer.init();

		dataManager = UsersDataManager.getInstance();
		user = new RegisteredUser("marx", "proletarian707",
				"marx@proletarian.org");

	}

	@Test
	public void testCheckPassword() throws RegistrationFailedException {

		assertTrue(dataManager.checkPassword(user));
		dataManager.saveRegistrationFields(user);
		assertTrue(dataManager.checkPassword(user));

	}

}
