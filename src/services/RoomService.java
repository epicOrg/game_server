package services;

import game.Room;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;
import data_management.GameDataManager;

/**
 * 
 * @author Luca
 * @since 18/04/2015
 */

public class RoomService implements Service {
	
	private JSONObject request;
	private GameDataManager dataManager;

	public RoomService(JSONObject json) {
		request = json;
		dataManager = GameDataManager.getInstance();
	}

	@Override
	public String start() {
		
		JSONObject response = new JSONObject();
		System.out.println(request.toString());
		try {
			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean creationOk = true;
		try {
			String serviceType = request.getString(FieldsNames.SERVICE_TYPE);
			switch (serviceType) {
			case FieldsNames.ROOOMS_CREATE:
				creationOk = dataManager.newRoom(request.getString(FieldsNames.ROOM_NAME));
				addRoomsList(response);
				break;
			case FieldsNames.ROOMS_LIST	:
				addRoomsList(response);
			default:
				break;
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		System.out.println(response.toString());
		
		return response.toString();
		
	}

	private void addRoomsList(JSONObject response) {
		JSONObject roomsList = new JSONObject();
		
		ArrayList<Room> rooms = dataManager.getRooms();
		
		try {
			for(Room room: rooms){
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(FieldsNames.ROOM_MAX_PLAYERS, Room.MAX_PLAYER);
				roomDescription.put(FieldsNames.ROOM_CURRENT_PLAYERS, room.getCurrentPlayers());			
				roomsList.put(room.getName(), roomDescription);
			}
			
			response.put(FieldsNames.ROOMS_LIST, roomsList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
