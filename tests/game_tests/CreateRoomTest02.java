package game_tests;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import server.ServerInitializer;
import services.IService;
import services.Login;
import services.Register;
import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import check_fields.FieldsNames;

/**
 * Test for an invalid Room Create request: room already exists.
 * 
 * @author Noris
 * @date 2015/04/21
 *
 */

class CreateRoomTest02 {

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().init();

		// Generate a random username
		String randomUsername = "Eraclito" + new Random().nextInt(10000);

		// CLIENT: Registration
		JSONObject jsonRegFromClient = new JSONObject();
		jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonRegFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonRegFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
		jsonRegFromClient.put(FieldsNames.EMAIL, randomUsername + "@logos.org");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Register the user
		Register register = new Register();
		System.out.println("SERVER Registration Message: "
				+ register.start(jsonRegFromClient) + "\n");

		// CLIENT: Send message to go online
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
		jsonLoginFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonLoginFromClient.put(FieldsNames.PASSWORD, "Doxa0rTrolling");
		jsonLoginFromClient.put(FieldsNames.IP_ADDRESS, "192.168.1.3");
		jsonLoginFromClient.put(FieldsNames.LOCAL_PORT, "1234");
		System.out.println("CLIENT Login Message: " + jsonLoginFromClient);

		// SERVER: Set the user online
		IService login = new Login(null);
		String stringLoginFromServer = login.start(jsonLoginFromClient).toString();
		System.out.println("SERVER Login Message: " + stringLoginFromServer
				+ "\n");

		// CLIENT: Read response from server
		JSONObject jsonLoginFromServer = new JSONObject(stringLoginFromServer);
		int hashCode = (int) jsonLoginFromServer.get(FieldsNames.HASHCODE);

		// CLIENT: Send a RoomCreate request
		JSONObject jsonRoomCreateFromClient = new JSONObject();
		jsonRoomCreateFromClient.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
		jsonRoomCreateFromClient.put(FieldsNames.SERVICE_TYPE,
				FieldsNames.ROOM_CREATE);
		jsonRoomCreateFromClient.put(FieldsNames.ROOM_NAME, "OntologicRoom");
		jsonRoomCreateFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonRoomCreateFromClient.put(FieldsNames.HASHCODE, hashCode);
		System.out.println("CLIENT RoomCreate Message: "
				+ jsonRoomCreateFromClient);

		// SERVER: Send a response with the list of the online rooms
		Rooms roomService = new Rooms();
		roomService.addSubService(new CreateRoom());
		System.out.println("SERVER RoomCreate Message: "
				+ roomService.start(jsonRoomCreateFromClient) + "\n");

		// CLIENT: Send a duplicate Room Create request
		JSONObject jsonRoomCreateFromClientCopy = new JSONObject();
		jsonRoomCreateFromClientCopy
				.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
		jsonRoomCreateFromClientCopy.put(FieldsNames.SERVICE_TYPE,
				FieldsNames.ROOM_CREATE);
		jsonRoomCreateFromClientCopy
				.put(FieldsNames.ROOM_NAME, "OntologicRoom");
		jsonRoomCreateFromClientCopy.put(FieldsNames.USERNAME, randomUsername);
		jsonRoomCreateFromClientCopy.put(FieldsNames.HASHCODE, hashCode);
		System.out.println("CLIENT RoomCreate Message: "
				+ jsonRoomCreateFromClientCopy);

		// SERVER: Send an error
		System.out.println("SERVER RoomCreate Message: "
				+ roomService.start(jsonRoomCreateFromClientCopy));
	}
}
