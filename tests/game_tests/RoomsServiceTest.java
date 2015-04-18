package game_tests;

import org.json.JSONException;
import org.json.JSONObject;

import services.RoomService;
import check_fields.FieldsNames;
import data_management.GameDataManager;

public class RoomsServiceTest {
	
	public static void main(String[] args) {
		
		GameDataManager dataManager = GameDataManager.getInstance();
		
		dataManager.newRoom("ciao");
		
		dataManager.newRoom("ok");
		
		JSONObject request = new JSONObject();
		try {
			request.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			request.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOOMS_CREATE);
			request.put(FieldsNames.ROOM_NAME, "ciao2");
			System.out.println(request.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(new RoomService(request).start());
		
	}

}
