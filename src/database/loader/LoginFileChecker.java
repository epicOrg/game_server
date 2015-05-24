package database.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data_management.RegisteredUser;
import exceptions.LoginFailedException;

/**
 * An implementation of {@link ILoginChecker} that finds and checks used data in
 * files.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class LoginFileChecker implements ILoginChecker {

	private String path;

	public LoginFileChecker(String path) {
		super();
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see loader.ILoginChecker#checkUser(data_management.RegisteredUser)
	 */
	@Override
	public void checkUser(RegisteredUser registeredUser) throws LoginFailedException, IOException {

		BufferedReader reader;

		try {

			reader = new BufferedReader(new FileReader(path + registeredUser.getUsername()));

		} catch (FileNotFoundException e) {

			throw new LoginFailedException("Not registred user!");
		}

		String line = reader.readLine();

		String[] fields = line.split(" ");

		if (!registeredUser.getEncryptedPassword().equals(fields[1])) {
			reader.close();
			throw new LoginFailedException("Wrong password!");
		}

		reader.close();
	}

}
