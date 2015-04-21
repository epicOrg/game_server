package call_tests;

import game_server.ServerInitializer;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.Call;
import services.Login;
import services.Register;
import services.Service;
import data_management.DataManager;

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
		InetAddress ipAddress = InetAddress.getByName("192.168.1.1");
		onlineManager.setOnline("JohnLocke", ipAddress);

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
		System.out.println("Registration Server Message: "
				+ new Register(jsonRegFromClient).start());

		// CLIENT: Send message to go online
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		jsonLoginFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonLoginFromClient.put(FieldsNames.PASSWORD, "AufhebungRulezL0L");
		jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.2");
		System.out.println("Login Client Message: " + jsonLoginFromClient);

		// SERVER: Set the user online
		Service login = new Login(jsonLoginFromClient);
		String stringLoginFromServer = login.start().toString();
		System.out.println("Login Server Message: " + stringLoginFromServer);

		// CLIENT: Read response from server
		JSONObject jsonLoginFromServer = new JSONObject(stringLoginFromServer);
		int hashCode = (int) jsonLoginFromServer.get(FieldsNames.HASHCODE);

		// CLIENT: Send a valid call request
		JSONObject jsonCallFromClient = new JSONObject();
		jsonCallFromClient.put(FieldsNames.SERVICE, FieldsNames.CALL);
		jsonCallFromClient.put(FieldsNames.CALLER, randomUsername);
		jsonCallFromClient.put(FieldsNames.HASHCODE, hashCode);
		jsonCallFromClient.put(FieldsNames.PORT, 6666);
		jsonCallFromClient.put(FieldsNames.CALLEE, "JohnLocke");
		System.out.println("Call Client Message:  " + jsonCallFromClient);

		// SERVER: Read call request
		Service call = new Call(jsonCallFromClient);
		String stringCallFromServer = call.start().toString();
		System.out.println("Call Server Message:  " + stringCallFromServer);

		// Deleted registration file for a clean test
		new File(DataManager.getInstance().getPath() + randomUsername).delete();
	}

}
