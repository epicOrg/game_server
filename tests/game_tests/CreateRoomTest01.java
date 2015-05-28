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
import fields_names.CommonFields;
import fields_names.FieldsNames;
import fields_names.RegisterFields;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import fields_names.ServicesFields;

/**
 * Test for a valid Room Create request.
 * 
 * @author Noris
 * @date 2015/04/21
 * @see CreateRoom
 */

class CreateRoomTest01 {

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().init();

		// Generate a random username
		String randomUsername = "Eraclito" + new Random().nextInt(10000);

		// CLIENT: Registration
		JSONObject jsonRegFromClient = new JSONObject();
		jsonRegFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
		jsonRegFromClient.put(CommonFields.USERNAME.toString(), randomUsername);
		jsonRegFromClient.put(CommonFields.PASSWORD.toString(), "Doxa0rTrolling");
		jsonRegFromClient.put(RegisterFields.EMAIL.toString(), randomUsername + "@logos.org");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Register the user
		Register register = new Register();
		System.out.println("SERVER Registration Message: "
				+ register.start(jsonRegFromClient) + "\n");

		// CLIENT: Send message to go online
		JSONObject jsonLoginFromClient = new JSONObject();
		jsonLoginFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.LOGIN.toString());
		jsonLoginFromClient.put(CommonFields.USERNAME.toString(), randomUsername);
		jsonLoginFromClient.put(CommonFields.PASSWORD.toString(), "Doxa0rTrolling");
		jsonLoginFromClient.put(CommonFields.IP_ADDRESS.toString(), "192.168.1.3");
		jsonLoginFromClient.put(CommonFields.LOCAL_PORT.toString(), "1234");
		System.out.println("CLIENT Login Message: " + jsonLoginFromClient);

		// SERVER: Set the user online
		IService login = new Login(null);
		String stringLoginFromServer = login.start(jsonLoginFromClient).toString();
		System.out.println("SERVER Login Message: " + stringLoginFromServer
				+ "\n");

		// CLIENT: Read response from server
		JSONObject jsonLoginFromServer = new JSONObject(stringLoginFromServer);
		int hashCode = (int) jsonLoginFromServer.get(CommonFields.HASHCODE.toString());

		// CLIENT: Send a RoomCreate request
		JSONObject jsonRoomCreateFromClient = new JSONObject();
		jsonRoomCreateFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
		jsonRoomCreateFromClient.put(ServicesFields.SERVICE_TYPE.toString(),
				RoomsFields.ROOM_CREATE.toString());
		jsonRoomCreateFromClient.put(RoomFields.ROOM_NAME.toString(), "OntologicRoom");
		jsonRoomCreateFromClient.put(RoomsFields.ROOM_TEAMS_NUMBER.toString(), 2);
		jsonRoomCreateFromClient.put(RoomsFields.ROOM_TEAMS_DIMENSION.toString(), 2);
		jsonRoomCreateFromClient.put(CommonFields.USERNAME.toString(), randomUsername);
		jsonRoomCreateFromClient.put(CommonFields.HASHCODE.toString(), hashCode);
		System.out.println("CLIENT RoomCreate Message: "
				+ jsonRoomCreateFromClient);

		// SERVER: Send a response with the list of the online rooms
		Rooms roomService = new Rooms();
		roomService.addSubService(new CreateRoom());
		System.out.println("SERVER RoomCreate Message: "
				+ roomService.start(jsonRoomCreateFromClient));
	}

}
