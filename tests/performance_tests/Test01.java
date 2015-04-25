package performance_tests;

import game_server.ServerInitializer;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.Login;
import services.Register;
import check_fields.FieldsNames;
import database.Paths;

/**
 * Register NUMBER_OF_USERS users and login them. Note: remember to delete the
 * emails from the database.
 * 
 * @author Noris
 * @date 2015/04/21
 *
 */

class Test01 {

	private static final int NUMBER_OF_USERS = 10;

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().initDataManager();

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// Generate a random user name
			String username = "Eraclito" + i;

			// CLIENT: Registration
			JSONObject jsonRegFromClient = new JSONObject();
			jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
			jsonRegFromClient.put(FieldsNames.USERNAME, username);
			jsonRegFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
			jsonRegFromClient.put(FieldsNames.EMAIL, username + "@logos.org");
			System.out.println("[" + i + "] CLIENT Registration Message: "
					+ jsonRegFromClient);

			// SERVER: Register the user
			Register register = new Register();
			register.setRequest(jsonRegFromClient);
			System.out.println("[" + i + "] SERVER Registration Message: "
					+ register.start() + "\n");

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
			jsonLoginFromClient.put(FieldsNames.USERNAME, username);
			jsonLoginFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
			jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.3");
			jsonLoginFromClient.put(FieldsNames.LOCAL_PORT, "1234");
			System.out.println("[" + i + "] CLIENT Login Message: "
					+ jsonLoginFromClient);

			// SERVER: Set the user online
			IService login = new Login();
			login.setRequest(jsonLoginFromClient);
			String stringLoginFromServer = login.start().toString();
			System.out.println("[" + i + "] SERVER Login Message: "
					+ stringLoginFromServer + "\n");

			// CLIENT: Read response from server
			JSONObject jsonLoginFromServer = new JSONObject(
					stringLoginFromServer);
			@SuppressWarnings("unused")
			int hashCode = (int) jsonLoginFromServer.get(FieldsNames.HASHCODE);
		}

		// Deleted registration file for a clean test
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			String username = "Eraclito" + i;
			new File(Paths.getUsersPath() + username).delete();
		}

	}

}
