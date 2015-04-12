package call_tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.Login;
import services.Service;
import check_fields.FieldsNames;

/**
 * Test for a valid call request.
 * 
 * @author Noris
 * @date 2015/04/11
 *
 */
public class Test01 {

	public static void main(String[] args) throws UnknownHostException,
			JSONException {

		// SERVER: Set online "JohnLocke"
		OnlineManager onlineManager = OnlineManager.getInstance();
		InetAddress ipAddress = InetAddress.getByName("192.168.1.1");
		onlineManager.setOnline("JohnLocke", ipAddress);

		// CLIENT: Send message to go online
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		jsonLoginFromClient.put(FieldsNames.USERNAME, "GWFHegel");
		jsonLoginFromClient.put(FieldsNames.PASSWORD, "AufhebungRulez!");
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
		jsonCallFromClient.put(FieldsNames.CALLER, "GWFHegel");
		jsonCallFromClient.put(FieldsNames.HASHCODE, hashCode);
		jsonCallFromClient.put(FieldsNames.PORT, 6666);
		jsonCallFromClient.put(FieldsNames.CALLEE, "JohnLocke");
		System.out.println("Call Client Message:  " + jsonCallFromClient);

		// SERVER: Read call request
		Service call = new Call(jsonCallFromClient);
		String stringCallFromServer = call.start().toString();
		System.out.println("Call Server Message:  " + stringCallFromServer);
	}

}
