package performance_tests;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;

/**
 * This test registers and logins NUMBER_OF_USERS users (login after
 * registration for every user). Before run this test start the server on the
 * same machine. Remember to restore the e-mails database after this test (it
 * already remove users registration file).
 * 
 * @author Noris
 * @date 2015/04/21
 */

class Test03 {

	private static final int NUMBER_OF_USERS = 10;

	public static void main(String[] args) throws JSONException,
			UnknownHostException, IOException, InterruptedException {

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// Generate a random user name
			String username = "Schiller" + i;

			// CLIENT: Registration
			JSONObject jsonRegFromClient = new JSONObject();
			jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
			jsonRegFromClient.put(FieldsNames.USERNAME, username);
			jsonRegFromClient.put(FieldsNames.PASSWORD, "Friedrich59");
			jsonRegFromClient.put(FieldsNames.EMAIL, username + "@neckar.com");
			System.out.println("[" + i+1 + "] CLIENT Registration Message: "
					+ jsonRegFromClient);

			new Test_OpenSocket().start(jsonRegFromClient.toString());

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
			jsonLoginFromClient.put(FieldsNames.USERNAME, username);
			jsonLoginFromClient.put(FieldsNames.PASSWORD, "Friedrich59");
			jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.4");
			System.out.println("[" + i+1 + "] CLIENT Login Message: "
					+ jsonLoginFromClient + "\n");
			
			new Test_OpenSocket().start(jsonLoginFromClient.toString());
		}

		// Attends SECONDS seconds
		int SECONDS = 2;
		Thread.sleep(1000 * SECONDS);

		// Delete registration file for a clean test
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			String username = "Schiller" + i;
			new File(DataManager.getInstance().getPath() + username).delete();
		}

	}
}
