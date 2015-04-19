package writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data_management.RegisteredUser;

/**
 * Implementation of @see ICreatoreRapporto that saves users on a file.
 * 
 * @author Micieli
 * @author Gavina
 * @author Modica
 * @author Noris
 * @date 2015/04/14
 * 
 */
public class UserCreator implements IDataSaver {

	private String path;
	private ILineFormatter lineFormatter;

	public UserCreator(String path, ILineFormatter lineFormatter) {
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
