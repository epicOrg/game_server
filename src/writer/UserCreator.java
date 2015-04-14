package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import data_management.RegisteredUser;

/**
 * 
 * Implementation of @see ICreatoreRapporto that saves users on a file.
 * 
 * @author Micieli
 * @date 2015/04/14
 *
 */
public class UserCreator implements IUserCreator {

	private String filename;
	private ILineFormatter lineFormatter;

	public UserCreator(String filename, ILineFormatter lineFormatter) {
		super();
		this.filename = filename;
		this.lineFormatter = lineFormatter;
	}

	@Override
	public void writeUser(RegisteredUser user)
			throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		writer.write(lineFormatter.formatLine(user));

		writer.close();
	}
}
