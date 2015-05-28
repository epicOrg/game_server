package game_tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import exceptions.UserNotOnlineException;
import fields_names.CommonFields;
import fields_names.FieldsNames;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import fields_names.ServicesFields;

/**
 * RoomService request message test with no errors.
 * 
 * @author Noris
 * @date 2015/04/19
 * @see Rooms
 * @see CreateRoom
 */
class RoomCheckerTest01 {

	public static void main(String[] args) throws UnknownHostException, JSONException {

		// SERVER: Set online Friedrich Nietzsche
		OnlineManager onlineManager = OnlineManager.getInstance();
		InetAddress ipAddress = InetAddress.getByName("15.10.18.44");
		onlineManager.setOnline("Nietzsche", ipAddress, null);
		int hashCode;

		try {

			hashCode = onlineManager.getHashCodeByUsername("Nietzsche");
			JSONObject jsonRequest = new JSONObject();
			jsonRequest.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
			jsonRequest.put(CommonFields.USERNAME.toString(), "Nietzsche");
			jsonRequest.put(CommonFields.HASHCODE.toString(), hashCode);
			jsonRequest.put(ServicesFields.SERVICE_TYPE.toString(), RoomsFields.ROOM_CREATE.toString());
			jsonRequest.put(RoomFields.ROOM_NAME.toString(), "GottIstTot");
			jsonRequest.put(RoomsFields.ROOM_TEAMS_NUMBER.toString(), 2);
			jsonRequest.put(RoomsFields.ROOM_TEAMS_DIMENSION.toString(), 2);
			

			System.out.println("Request: " + jsonRequest);
			Rooms roomService = new Rooms();
			roomService.addSubService(new CreateRoom());
			System.out.println("Response: " + roomService.start(jsonRequest));

		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
