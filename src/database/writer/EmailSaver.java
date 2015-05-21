package database.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data_management.RegisteredUser;

/**
 * 
 * Save emails during registration using a file
 * 
 * @author Micieli
 * @date 2015/04/17
 */

public class EmailSaver implements IDataSaver {

	private String filename;
	private ILineFormatter lineFormatter;

	/**
	 * 
	 * @param filename			the name of the file where to write emails list
	 * @param lineFormatter		a line formatter 
	 */
	public EmailSaver(String filename, ILineFormatter lineFormatter) {
		super();
		this.filename = filename;
		this.lineFormatter = lineFormatter;
	}

	@Override
	public void saveData(RegisteredUser user) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				filename), true));

		writer.append(lineFormatter.formatLine(user));
		writer.close();
	}

}
