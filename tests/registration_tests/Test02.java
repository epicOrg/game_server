package registration_tests;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import server.ServerInitializer;
import services.Register;
import fields_names.CommonFields;
import fields_names.RegisterFields;
import fields_names.ServicesFields;

/**
 * Test for client registration with an error: username already exists.
 * 
 * @author Noris
 * @date 2015/04/21
 *
 */

class Test02 {

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().init();

		// Generate a random username
		String randomUsername = "Democrito" + new Random().nextInt(10000);

		// CLIENT: Registration
		JSONObject jsonRegFromClient = new JSONObject();
		jsonRegFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
		jsonRegFromClient.put(CommonFields.USERNAME.toString(), randomUsername);
		jsonRegFromClient.put(CommonFields.PASSWORD.toString(), "abdera460");
		jsonRegFromClient.put(RegisterFields.EMAIL.toString(), randomUsername + "@atom.com");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Register the user
		Register register = new Register();
		System.out.println("SERVER Registration Message: "
				+ register.start(jsonRegFromClient) + "\n");

		// CLIENT: Try to register with the same username
		JSONObject jsonRegFromClientCopy = new JSONObject();
		jsonRegFromClientCopy.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
		jsonRegFromClientCopy.put(CommonFields.USERNAME.toString(), randomUsername);
		jsonRegFromClientCopy.put(CommonFields.PASSWORD.toString(), "abdera460");
		jsonRegFromClientCopy.put(RegisterFields.EMAIL.toString(), "anotherEmail@epic.org");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Try to register the user
		System.out.println("SERVER Registration Message: "
				+ register.start(jsonRegFromClient) + "\n");

	}

}
