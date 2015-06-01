package data_management_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data_management.UsersDataManager;
import data_management.RegisteredUser;
import database.loader.RegistrationFileChecker;
import exceptions.RegistrationFailedException;
import server.ServerInitializer;

/**
 * Test {@link RegistrationFileChecker}.
 * 
 * @author Modica
 * @author Gavina
 * @date 2015/04/17
 * @see UsersDataManager
 * @see RegisteredUser
 * @see RegistrationFileChecker
 * @see RegistrationFailedException
 */
public class Test02 {

	private UsersDataManager dataManager;
	private RegisteredUser user1;
	private RegisteredUser user2;
	private ServerInitializer serverInitializer = new ServerInitializer();

	@Before
	public void setUp() {

		serverInitializer.init();

		dataManager = UsersDataManager.getInstance();
		user1 = new RegisteredUser("Hegel", "I_AM_A_LOL", "hegel@epic.org");
		user2 = new RegisteredUser("Kant", "I_AM_TROLL", "kant@epic.org");
	}

	@Test
	public void usernameTest() throws RegistrationFailedException {

		dataManager.saveRegistrationFields(user1);
		assertFalse("Username non esistente",
				dataManager.checkUsername(user1.getUsername()));
		assertTrue("Username già esistente", dataManager.checkUsername(user2.getUsername()));
	}

	@Test
	public void emailTest() {
		assertFalse("Email non esistente",
				dataManager.checkEmail(user1.getEmail()));
		assertTrue("Email già esistente", dataManager.checkEmail(user2.getEmail()));
	}
}
