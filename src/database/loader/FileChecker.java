package database.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.EmailAlreadyUsedException;
import exceptions.UsernameAlreadyUsedException;

/**
 * Implementation of @see {@link IRegistrationChecker}: this class check the
 * registration fields: if the username or the email already exists.
 * 
 * @author Modica
 * @author Gavina
 * @date 2015/04/17
 */
public class FileChecker implements IRegistrationChecker {

	private String path;

	/**
	 * @param path
	 *            the path of the file
	 */
	public FileChecker(String path) {
		super();
		this.path = path;
	}

	@Override
	public void checkUsername(String username)
			throws UsernameAlreadyUsedException {

		File file = new File(path + username);
		boolean exsist = file.exists();
		if (exsist) {
			throw new UsernameAlreadyUsedException();
		}
	}

	@Override
	public void checkEmail(String email) throws EmailAlreadyUsedException,
			IOException {

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {

				if (line.trim().equals(email.trim())) {
					reader.close();
					throw new EmailAlreadyUsedException();
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			File file = new File(path);
			file.createNewFile();
		}

	}
}