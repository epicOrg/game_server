package online_management_tests;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import online_management.OnlineManager;

import org.junit.Test;

import exceptions.UserNotOnlineException;

/**
 * This test sets online a defined number of users and it chooses randomly one
 * of those users. Then, with the user name, it search the hashCode of this
 * user, and with this hashCode it search the user name again.
 * 
 * @author Noris
 * @date 2015/04/05
 *
 */

public class Test01 {

	private final int ONLINE_USERS = 100;

	@Test
	public void test() throws UnknownHostException {

		OnlineManager onlineManager = OnlineManager.getInstance();

		// Set online ONLINE_USERS users
		for (int i = 0; i < ONLINE_USERS; i++) {
			String usernameGen = "user" + i;
			InetAddress ipAddressGen = InetAddress.getByName("192.168.1." + i);
			onlineManager.setOnline(usernameGen, ipAddressGen, 3000);
		}

		// Get a random user
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(ONLINE_USERS);
		String realUsername = "user" + randomNumber;

		// Get the hashCode of realUsername
		int hashCode;
		try {
			hashCode = onlineManager.getHashCodeByUsername(realUsername);
			System.out.println("HashCode of " + realUsername + ": " + hashCode);

			// Get the user name of the previous founded HashCode
			String foundUsername = onlineManager
					.getUsernameByHashCode(hashCode);
			System.out
					.println("Username of " + hashCode + ": " + foundUsername);

			assertEquals(realUsername, foundUsername);
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
