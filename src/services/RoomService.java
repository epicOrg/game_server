package services;

import game.Player;
import game.Room;

import java.util.ArrayList;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exception.FullRoomException;
import exception.NoSuchRoomException;

/**
 * 
 * @author Luca
 * @since 18/04/2015
 */

public class RoomService implements Service {

	private JSONObject request;
	private GameDataManager dataManager;
	private JSONObject response;

	public RoomService(JSONObject json) {
		request = json;
		dataManager = GameDataManager.getInstance();
		response = new JSONObject();
	}

	@Override
	public String start() {

		System.out.println(request.toString());
		try {
			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String serviceType = request.getString(FieldsNames.SERVICE_TYPE);

			runService(serviceType);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(response.toString());

		return response.toString();

	}

	private void runService(String serviceType) throws JSONException {
		boolean creationOk;
		switch (serviceType) {
		case FieldsNames.ROOOMS_CREATE:
			creationOk = dataManager.newRoom(request
					.getString(FieldsNames.ROOM_NAME));
			response.put(FieldsNames.RESULT, creationOk);
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOMS_LIST);
			addRoomsList();
			break;
		case FieldsNames.ROOMS_LIST:
			addRoomsList();
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOMS_LIST);
			break;
		case FieldsNames.ROOM_JOIN:
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_JOIN);
			addPlayer();
			break;
		default:
			break;
		}
	}

	private void addPlayer() {

		try {
			int hascode = request.getInt(FieldsNames.HASHCODE);
			String username = OnlineManager.getInstance()
					.getUsernameByHashCode(hascode);
			Player player = new Player(username);

			String roomName = request.getString(FieldsNames.ROOM_NAME);
			Room room = dataManager.getRoomByName(roomName);
			room.addPlayer(player);
			response.put(FieldsNames.RESULT, true);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			joinFailed();
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			joinFailed();
			e.printStackTrace();
		} catch (FullRoomException e) {
			// TODO Auto-generated catch block
			joinFailed();
			e.printStackTrace();
		}
	}

	private void joinFailed() {
		try {
			response.put(FieldsNames.RESULT, false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addRoomsList() {
		JSONObject roomsList = new JSONObject();

		ArrayList<Room> rooms = dataManager.getRooms();
		System.out.println("ROOMS N°" + rooms.size());

		try {
			for (Room room : rooms) {
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(FieldsNames.ROOM_MAX_PLAYERS,
						Room.MAX_PLAYER);
				roomDescription.put(FieldsNames.ROOM_CURRENT_PLAYERS,
						room.getCurrentPlayers());
				roomsList.put(room.getName(), roomDescription);
			}

			response.put(FieldsNames.ROOMS_LIST, roomsList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
