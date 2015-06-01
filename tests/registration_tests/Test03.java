package registration_tests;

import java.util.Random;

import messages.fields_names.CommonFields;
import messages.fields_names.RegisterFields;
import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

import server.ServerInitializer;
import services.Register;

/**
 * Test for client registration with an error: email already used.
 * 
 * @author Noris
 * @date 2015/04/21
 */

class Test03 {

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().init();

		// Generate a random user name
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

		// CLIENT: Try to register with the same email
		JSONObject jsonRegFromClientCopy = new JSONObject();
		jsonRegFromClientCopy.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
		jsonRegFromClientCopy.put(CommonFields.USERNAME.toString(), randomUsername + "LOL");
		jsonRegFromClientCopy.put(CommonFields.PASSWORD.toString(), "abdera460");
		jsonRegFromClientCopy.put(RegisterFields.EMAIL.toString(), randomUsername
				+ "@atom.com");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Try to register the user
		System.out.println("SERVER Registration Message: "
				+ register
				.start(jsonRegFromClientCopy) + "\n");

	}

}
