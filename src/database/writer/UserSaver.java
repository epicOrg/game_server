package database.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data_management.RegisteredUser;

/**
 * Save all user data of the registration in a file. In order to speed up the
 * checking phase, each user has its own file.
 * 
 * @author Micieli
 * @author Gavina
 * @author Modica
 * @author Noris
 * @date 2015/04/14
 */
public class UserSaver implements IDataSaver {

	private String path;
	private ILineFormatter lineFormatter;

	/**
	 * @param path
	 *            the path in which user files are saved
	 * @param lineFormatter
	 *            a line formatter
	 */
	public UserSaver(String path, ILineFormatter lineFormatter) {
		super();
		this.path = path;
		this.lineFormatter = lineFormatter;
	}

	@Override
	public void saveData(RegisteredUser user) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path
				+ user.getUsername())));

		writer.write(lineFormatter.formatLine(user));

		writer.close();
	}

}
