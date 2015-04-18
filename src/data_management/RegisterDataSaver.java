package data_management;

import java.io.IOException;

import writer.IDataSaver;

/**
 * @author Micieli
 * @date 2015/04/17
 */

public class RegisterDataSaver implements IDataSaver {

	private IDataSaver userSaver;
	private IDataSaver emailSaver;

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
