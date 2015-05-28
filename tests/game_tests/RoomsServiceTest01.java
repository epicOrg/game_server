package game_tests;

import org.json.JSONException;
import org.json.JSONObject;

import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import fields_names.ServicesFields;
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

			request.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
			request.put(ServicesFields.SERVICE_TYPE.toString(), RoomsFields.ROOM_CREATE.toString());
			request.put(RoomFields.ROOM_NAME.toString(), "ciao2");
			request.put(RoomsFields.ROOM_TEAMS_NUMBER.toString(), 2);
			request.put(RoomsFields.ROOM_TEAMS_DIMENSION.toString(), 2);
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
