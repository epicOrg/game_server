package performance_tests;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import server.ServerInitializer;
import services.IService;
import services.Login;
import services.Register;
import database.Paths;
import fields_names.CommonFields;
import fields_names.FieldsNames;
import fields_names.RegisterFields;
import fields_names.ServicesFields;

/**
 * Register NUMBER_OF_USERS users and login them. Note: remember to delete the
 * e-mails from the database.
 * 
 * @author Noris
 * @date 2015/04/21
 *
 */

class Test01 {

	private static final int NUMBER_OF_USERS = 10;

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().init();

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// Generate a random user name
			String username = "Eraclito" + i;

			// CLIENT: Registration
			JSONObject jsonRegFromClient = new JSONObject();
			jsonRegFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
			jsonRegFromClient.put(CommonFields.USERNAME.toString(), username);
			jsonRegFromClient.put(CommonFields.PASSWORD.toString(), "Doxa0rTrolling");
			jsonRegFromClient.put(RegisterFields.EMAIL.toString(), username + "@logos.org");
			System.out.println("[" + i + "] CLIENT Registration Message: " + jsonRegFromClient);

			// SERVER: Register the user
			Register register = new Register();
			System.out.println("[" + i + "] SERVER Registration Message: "
					+ register.start(jsonRegFromClient) + "\n");

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.LOGIN.toString());
			jsonLoginFromClient.put(CommonFields.USERNAME.toString(), username);
			jsonLoginFromClient.put(CommonFields.PASSWORD.toString(), "Doxa0rTrolling");
			jsonLoginFromClient.put(CommonFields.IP_ADDRESS.toString(), "192.168.1.3");
			jsonLoginFromClient.put(CommonFields.LOCAL_PORT.toString(), "1234");
			System.out.println("[" + i + "] CLIENT Login Message: " + jsonLoginFromClient);

			// SERVER: Set the user online
			IService login = new Login(null);
			String stringLoginFromServer = login.start(jsonLoginFromClient).toString();
			System.out.println("[" + i + "] SERVER Login Message: " + stringLoginFromServer + "\n");

			// CLIENT: Read response from server
			JSONObject jsonLoginFromServer = new JSONObject(stringLoginFromServer);
			@SuppressWarnings("unused")
			int hashCode = (int) jsonLoginFromServer.get(CommonFields.HASHCODE.toString());
		}

		// Deleted registration file for a clean test
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			String username = "Eraclito" + i;
			new File(Paths.getUsersPath() + username).delete();
		}

	}

}
