package email_confirmation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data_management.RegisteredUser;
import database.Paths;

/**
 * @author Noris
 * @date 2015/05/05
 */

public class EmailConfirmationChecker {

	public void saveCode(RegisteredUser user, String code) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Paths.getConfirmPath()
				+ user.getUsername())));

		writer.write(code);
		writer.close();
	}

	public boolean checkCode(RegisteredUser user, String code) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(Paths.getConfirmPath()
				+ user.getUsername()));

		String savedCode = reader.readLine();
		reader.close();

		if (code.equals(savedCode)) {
			new File(Paths.getConfirmPath() + user.getUsername()).delete();
			return true;
		}

		return false;
	}

}
