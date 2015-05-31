package performance_tests;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import messages.fields_names.CommonFields;
import messages.fields_names.RegisterFields;
import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

import database.Paths;

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
			jsonRegFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
			jsonRegFromClient.put(CommonFields.USERNAME.toString(), username);
			jsonRegFromClient.put(CommonFields.PASSWORD.toString(), "Friedrich59");
			jsonRegFromClient.put(RegisterFields.EMAIL.toString(), username + "@neckar.com");
			System.out.println("[" + i + "] CLIENT Registration Message: "
					+ jsonRegFromClient);

			openSocket.writeSocket(jsonRegFromClient.toString());

			// CLIENT: Send message to go online
			JSONObject jsonLoginFromClient = new JSONObject();
			jsonLoginFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.LOGIN.toString());
			jsonLoginFromClient.put(CommonFields.USERNAME.toString(), username);
			jsonLoginFromClient.put(CommonFields.PASSWORD.toString(), "Friedrich59");
			jsonLoginFromClient.put(CommonFields.IP_ADDRESS.toString(), "192.168.1.4");
			jsonLoginFromClient.put(CommonFields.LOCAL_PORT.toString(), "1234");
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
