package data_management;

import java.io.IOException;

import database.writer.EmailSaver;
import database.writer.IDataSaver;
import database.writer.UserSaver;

/**
 * It uses two {@link IDataSaver} to save fields. In order to speed up the
 * checking phase, e-mails are also saved in a separate file.
 * 
 * @author Micieli
 * @date 2015/04/17
 * @see EmailSaver
 * @see UserSaver
 */

public class RegisterDataSaver implements IDataSaver {

	private IDataSaver userSaver;
	private IDataSaver emailSaver;

	/**
	 * @param userSaver
	 *            a <code>IDataSaver</code> that saves e-mails
	 * @param emailSaver
	 *            a <code>IDataSaver</code> that saves user data
	 */
	public RegisterDataSaver(IDataSaver userSaver, IDataSaver emailSaver) {
		super();
		this.userSaver = userSaver;
		this.emailSaver = emailSaver;
	}

	@Override
	public void saveData(RegisteredUser user) throws IOException {
		userSaver.saveData(user);
		emailSaver.saveData(user);
	}
}
