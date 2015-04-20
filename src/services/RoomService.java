package services;

import game.Player;
import game.Room;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.FullRoomException;
import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 */

public class RoomService implements Service {

	private GameDataManager gameDataManager;

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private RoomChecker roomChecker;

	public RoomService(JSONObject json) {
		jsonRequest = json;
		gameDataManager = GameDataManager.getInstance();
		jsonResponse = new JSONObject();
		roomChecker = new RoomChecker();
	}

	@Override
	public String start() {

		try {

			checkFields();
			if (roomChecker.noErrors())
				runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateResponse();
		return jsonResponse.toString();

	}

	private void checkFields() {

		// TODO Mettere a posto

		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			int hashCode = (int) jsonRequest.get(FieldsNames.HASHCODE);

			roomChecker.isUserOnline(username);
			roomChecker.checkHashCode(username, hashCode);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void runService(String serviceType) throws JSONException {

		// TODO Mettere a posto

		switch (serviceType) {

		case FieldsNames.ROOM_CREATE:

			try {

				String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
				if (!roomChecker.checkRoomName(roomName)) {
					jsonResponse.put(FieldsNames.SERVICE_TYPE,
							FieldsNames.ROOM_CREATE);
					break;
				}

				gameDataManager.newRoom(roomName);

			} catch (RoomAlreadyExistsException e) {
				roomChecker.getRoomAlreadyExistsError();
				jsonResponse.put(FieldsNames.SERVICE_TYPE,
						FieldsNames.ROOM_CREATE);
				break;
			}

			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOMS_LIST);
			addRoomsList();
			break;

		case FieldsNames.ROOMS_LIST:
			addRoomsList();
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOMS_LIST);
			break;
		case FieldsNames.ROOM_JOIN:
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_JOIN);
			addPlayer();
			break;
		default:
			break;
		}
	}

	private void addPlayer() {

		try {

			Player player = new Player(
					jsonRequest.getString(FieldsNames.USERNAME));

			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);
			room.addPlayer(player);
			jsonResponse.put(FieldsNames.ROOM_NAME, roomName);
			jsonResponse.put(FieldsNames.RESULT, true);

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

			jsonResponse.put(FieldsNames.RESULT, false);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addRoomsList() {
		JSONObject roomsList = new JSONObject();

		ArrayList<Room> rooms = gameDataManager.getRooms();

		try {

			for (Room room : rooms) {
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(FieldsNames.ROOM_MAX_PLAYERS,
						Room.MAX_PLAYERS);
				roomDescription.put(FieldsNames.ROOM_CURRENT_PLAYERS,
						room.getSize());
				roomsList.put(room.getName(), roomDescription);
			}

			jsonResponse.put(FieldsNames.ROOMS_LIST, roomsList);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateResponse() {
		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			jsonResponse.put(FieldsNames.NO_ERRORS, roomChecker.noErrors());

			if (!roomChecker.noErrors())
				jsonResponse.put(FieldsNames.ERRORS, roomChecker.getErrors());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
