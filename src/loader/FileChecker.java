package loader;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exception.EmailAlreadyUsedException;
import exception.UsernameAlreadyUsedException;

/**
 * Implementation of @see IRegistrationChecker
 * A class for registration data checking
 * @author Modica
 * @author Gavina
 * @date 2015/04/17
 */
public class FileChecker implements IRegistrationChecker {

	private String path;
	private String filename;

	/**
	 * 
	 * @param path the path of the file
	 * @param filename the name of the file
	 */
	public FileChecker(String path, String filename) {
		super();
		this.path = path;
		this.filename = filename;
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
	public void checkEmail(String email) throws EmailAlreadyUsedException, IOException {

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(path + filename));
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
			File file=new File(path+filename);
			file.createNewFile();
		}

	}
}
