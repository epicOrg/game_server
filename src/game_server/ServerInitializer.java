package game_server;

import writer.LineFormatter;
import writer.UserCreator;
import data_management.DataManager;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	public void initDataManager() {
		DataManager dataManager = DataManager.getInstance();
		dataManager.setUserCreator(new UserCreator(dataManager.getPath(),
				new LineFormatter()));
	}

}
