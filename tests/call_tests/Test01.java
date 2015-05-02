package call_tests;

import game_server.ServerInitializer;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.IService;
import services.Login;
import services.Register;
import check_fields.FieldsNames;
import database.Paths;

/**
 * Test for a valid call request. Remember that the email could already exists
 * in email database: in this case the test fail.
 * 
 * @author Noris
 * @date 2015/04/11
 *
 */
class Test01 {

	public static void main(String[] args) throws UnknownHostException,
			JSONException {

		// SERVER: Set online "JohnLocke"
		new ServerInitializer().initDataManager();
		OnlineManager onlineManager = OnlineManager.getInstance();
		InetAddress ipAddress = InetAddress.getByName("192.168.1.131");
		onlineManager.setOnline("JohnLocke", ipAddress, null);

		// Generate random username
		String randomUsername = "Hegel" + new Random().nextInt(10000);

		// CLIENT: Registration
		JSONObject jsonRegFromClient = new JSONObject();
		jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonRegFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonRegFromClient.put(FieldsNames.PASSWORD, "AufhebungRulezL0L");
		jsonRegFromClient.put(FieldsNames.EMAIL, randomUsername + "@lol.com");
		System.out.println("Registration Client Message: " + jsonRegFromClient);
		
		// SERVER: Register the user
		Register register = new Register();
		register.setRequest(jsonRegFromClient);
		System.out.println("Registration Server Message: "
				+ register.start());

		// CLIENT: Send message to go online
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		jsonLoginFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonLoginFromClient.put(FieldsNames.PASSWORD, "AufhebungRulezL0L");
		jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.2");
		System.out.println("Login Client Message: " + jsonLoginFromClient);

		// SERVER: Set the user online
		IService login = new Login(null);
		login.setRequest(jsonLoginFromClient);
		String stringLoginFromServer = login.start().toString();
		System.out.println("Login Server Message: " + stringLoginFromServer);

		// CLIENT: Read response from server
		JSONObject jsonLoginFromServer = new JSONObject(stringLoginFromServer);
		int hashCode = (int) jsonLoginFromServer.get(FieldsNames.HASHCODE);

		// CLIENT: Send a valid call request
		JSONObject jsonCallFromClient = new JSONObject();
		jsonCallFromClient.put(FieldsNames.SERVICE, FieldsNames.AUDIO);
		jsonCallFromClient.put(FieldsNames.CALLER, randomUsername);
		jsonCallFromClient.put(FieldsNames.HASHCODE, hashCode);
		jsonCallFromClient.put(FieldsNames.AUDIO_PORT, 6666);
		jsonCallFromClient.put(FieldsNames.CALLEE, "JohnLocke");
		System.out.println("Call Client Message:  " + jsonCallFromClient);

		// SERVER: Read call request
		IService call = new Call();
		call.setRequest(jsonCallFromClient);
		String stringCallFromServer = call.start().toString();
		System.out.println("Call Server Message:  " + stringCallFromServer);

		// Deleted registration file for a clean test
		new File(Paths.getUsersPath() + randomUsername).delete();
	}

}
