package game_tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.RoomService;
import exceptions.UserNotOnlineException;

/**
 * RoomService request message test with no errors.
 * 
 * @author Noris
 * @date 2015/04/19
 */
class RoomCheckerTest01 {

	public static void main(String[] args) throws UnknownHostException,
			JSONException {

		// SERVER: Set online Friedrich Nietzsche
		OnlineManager onlineManager = OnlineManager.getInstance();
		InetAddress ipAddress = InetAddress.getByName("15.10.18.44");
		onlineManager.setOnline("Nietzsche", ipAddress);
		int hashCode;
		try {
			hashCode = onlineManager.getHashCodeByUsername("Nietzsche");
			JSONObject jsonRequest = new JSONObject();
			jsonRequest.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			jsonRequest.put(FieldsNames.USERNAME, "Nietzsche");
			jsonRequest.put(FieldsNames.HASHCODE, hashCode);
			jsonRequest.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_CREATE);
			jsonRequest.put(FieldsNames.ROOM_NAME, "GottIstTot");

			System.out.println("Request: " + jsonRequest);
			RoomService roomService = new RoomService();
			roomService.setRequest(jsonRequest);
			System.out.println("Response: "
					+ roomService.start());
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
