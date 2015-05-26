package game_tests;

import org.json.JSONException;
import org.json.JSONObject;

import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;
import game.model.Team;
import game.model.TeamManager;

/**
 * @author Micieli
 * @date 2015/04/18
 */

class RoomsServiceTest01 {

	public static void main(String[] args) throws RoomAlreadyExistsException {

		GameDataManager gameDataManager = GameDataManager.getInstance();

		gameDataManager.newRoom("ciao", TeamManager.NUMBER_OF_TEAMS, Team.MAX_PLAYERS);

		gameDataManager.newRoom("ok", TeamManager.NUMBER_OF_TEAMS, Team.MAX_PLAYERS);

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

		Rooms roomService = new Rooms();
		roomService.addSubService(new CreateRoom());
		System.out.println(roomService.start(request));
	}
}
