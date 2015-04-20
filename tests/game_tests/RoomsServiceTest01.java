package game_tests;

import org.json.JSONException;
import org.json.JSONObject;

import services.RoomService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;

/**
 * @author Micieli
 * @date 2015/04/18
 */

public class RoomsServiceTest01 {

	public static void main(String[] args) throws RoomAlreadyExistsException {

		GameDataManager gameDataManager = GameDataManager.getInstance();

		gameDataManager.newRoom("ciao");

		gameDataManager.newRoom("ok");

		JSONObject request = new JSONObject();
		
		try {

			request.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			request.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_CREATE);
			request.put(FieldsNames.ROOM_NAME, "ciao2");
			System.out.println(request.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(new RoomService(request).start());

	}

}
