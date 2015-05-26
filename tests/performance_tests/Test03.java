package performance_tests;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import database.Paths;
import fields_name.FieldsNames;

/**
 * This test registers and logins NUMBER_OF_USERS users (login after the
 * registration). Before run this test, you must start the server on the same
 * machine. Remember to restore the e-mails database after this test (it already
 * removes users registration files, but not the e-mails in the database).
 * 
 * @author Noris
 * @date 2015/04/21
 */

class Test03 {

	// Number of users to registered and logged in
	private static final int NUMBER_OF_USERS = 10;

	public static void main(String[] args) throws JSONException,
			UnknownHostException, IOException, InterruptedException {

		for (int i = 0; i < NUMBER_OF_USERS; i++) {

			// CLIENT: Open the socket
			Test_OpenSocket openSocket = new Test_OpenSocket();
			openSocket.connectSocket();

			// Generate a random user name
			String username = "Schiller" + i;

			// CLIENT: Registration
			JSONObject jsonRegFromClient = new JSONObject();
			jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
			jsonRegFromClient.put(FieldsNames.USERNAME, username);
			jsonRegFromClient.put(FieldsNames.PASSWORD, "Friedrich59");
			jsonRegFromClient.put(FieldsNames.EMAIL, username + "@neckar.com");
			System.out.println("[" + i + "] CLIENT Registration Message: "
					+ jsonRegFromClient);

			openSocket.writeSocket(jsonRegFromClient.toString());

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
			jsonLoginFromClient.put(FieldsNames.USERNAME, username);
			jsonLoginFromClient.put(FieldsNames.PASSWORD, "Friedrich59");
			jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.4");
			jsonLoginFromClient.put(FieldsNames.LOCAL_PORT, "1234");
			System.out.println("[" + i + "] CLIENT Login Message: "
					+ jsonLoginFromClient + "\n");

			openSocket.writeSocket(jsonLoginFromClient.toString());
			openSocket.closeSocket();
		}

		// Attends SECONDS seconds
		int SECONDS = 2;
		Thread.sleep(1000 * SECONDS);

		// Delete registration files for a clean test
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			String username = "Schiller" + i;
			new File(Paths.getUsersPath() + username).delete();
		}

	}
}
