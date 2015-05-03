package call_tests;

import game_server.ServerInitializer;

import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.IService;
import services.Login;
import check_fields.FieldsNames;

/**
 * Test for an invalid call request (callee not online).
 * 
 * @author Noris
 * @date 2015/04/11
 *
 */
class Test03 {

	public static void main(String[] args) throws UnknownHostException,
			JSONException {

		// CLIENT: Send message to go online
		new ServerInitializer().initDataManager();
		
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		jsonLoginFromClient.put(FieldsNames.USERNAME, "GWFHegel");
		jsonLoginFromClient.put(FieldsNames.PASSWORD, "AufhebungRulez1");
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

		// CLIENT: Send an invalid call request (callee is not online)
		JSONObject jsonCallFromClient = new JSONObject();
		jsonCallFromClient.put(FieldsNames.SERVICE, FieldsNames.CALL);
		jsonCallFromClient.put(FieldsNames.CALLER, "GWFHegel");
		jsonCallFromClient.put(FieldsNames.HASHCODE, hashCode);
		jsonCallFromClient.put(FieldsNames.AUDIO_PORT_CLIENT, 6666);
		jsonCallFromClient.put(FieldsNames.CALLEE, "JohnLocke");
		System.out.println("Call Client Message:  " + jsonCallFromClient);

		// SERVER: Read call request
		IService call = new Call();
		call.setRequest(jsonCallFromClient);
		String stringCallFromServer = call.start().toString();
		System.out.println("Call Server Message:  " + stringCallFromServer);
	}

}
