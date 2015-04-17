package filewriter_tests;

import java.io.IOException;

import writer.LineFormatter;
import writer.UserCreator;
import data_management.RegisteredUser;

/**
 * test for write users on a file "registration.txt".
 * 
 * @author Gavina
 * @author Modica
 * @date 2015/04/14
 */
public class Test01 {

	public static void main(String[] args) {

		UserCreator creator = new UserCreator("database/",
				new LineFormatter());

		RegisteredUser user = new RegisteredUser("EpicOrg", "pass12345678",
				"example@epic.org");

		try {
			creator.saveUser(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RegisteredUser user2 = new RegisteredUser("Rossi", "pass12343366",
				"example2@epic.org");

		try {
			creator.saveUser(user2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
