package game_server;

import java.util.HashMap;

/**
 * @author	Micieli
 * @author	Noris
 * @since	2015-03-27
 */

public class DataManager {
	
	private static DataManager dataManager = new DataManager();
	private HashMap<String, Integer> hashCodes = new HashMap<String, Integer>();

	private DataManager() {
		super();
	}
	
	public static DataManager getIstance() {
		return dataManager;
	}
	
	public void setLogged(String username, int hashCode) {
		hashCodes.put(username, hashCode);
	}
	
	public boolean saveRegistrationFields() {
		//TODO
		return true;
	}
	
	public boolean checkUsername(String username){
		//TODO
		return true;
	}
	
	public boolean checkEmail(String email){
		//TODO
		return true;
	}
}
