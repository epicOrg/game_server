package game_server;

import writer.EmailFormatter;
import writer.EmailSaver;
import writer.UserCreator;
import writer.UserLineFormatter;
import data_management.DataManager;
import data_management.RegisterDataSaver;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	public void initDataManager() {
		DataManager dataManager = DataManager.getInstance();
		RegisterDataSaver registerDataSaver = new RegisterDataSaver(new UserCreator(dataManager.getPath(), new UserLineFormatter()), 
				new EmailSaver(dataManager.getPath() + "emails", new EmailFormatter()));
		dataManager.setRegisterDataSaver(registerDataSaver);
	}
}
