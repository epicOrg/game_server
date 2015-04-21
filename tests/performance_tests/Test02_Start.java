package performance_tests;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;

/**
 * Start the server on the same machine before run this test. Remember to
 * restore emails database after this test.
 * 
 * @author Noris
 * @date 2015/04/21
 */

public class Test02_Start {

	private static final int NUMBER_OF_USERS = 10;

	public static void main(String[] args) throws JSONException,
			UnknownHostException, IOException, InterruptedException {

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// Generate a random username
			String username = "Eraclito" + i;

			// CLIENT: Registration
			JSONObject jsonRegFromClient = new JSONObject();
			jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
			jsonRegFromClient.put(FieldsNames.USERNAME, username);
			jsonRegFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
			jsonRegFromClient.put(FieldsNames.EMAIL, username + "@logos.org");
			System.out.println("[" + i + "] CLIENT Registration Message: "
					+ jsonRegFromClient);

			new Test02_OpenSocket().start(jsonRegFromClient.toString());
		}

		System.out.println();

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// Generate a random username
			String username = "Eraclito" + i;

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
			jsonLoginFromClient.put(FieldsNames.USERNAME, username);
			jsonLoginFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
			jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.3");
			System.out.println("[" + i + "] CLIENT Login Message: "
					+ jsonLoginFromClient);

		}

		// Attends SECONDS seconds
		int SECONDS = 2;
		Thread.sleep(1000 * SECONDS);

		// Delete registration file for a clean test
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			String username = "Eraclito" + i;
			new File(DataManager.getInstance().getPath() + username).delete();
		}

	}
}
