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
 * @date 2015/04/18
 */

public class RoomService implements Service {

	private JSONObject request;
	private GameDataManager gameDataManager;

	private JSONObject response;
	private boolean noErrors = true;

	JSONObject errors = new JSONObject();
	RoomChecker roomChecker = new RoomChecker(errors);

	public RoomService(JSONObject json) {
		request = json;
		gameDataManager = GameDataManager.getInstance();
		response = new JSONObject();
	}

	@Override
	public String start() {

		try {

			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);

			checkFields();
			if (noErrors)
				runService(request.getString(FieldsNames.SERVICE_TYPE));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateResponse();
		return response.toString();

	}

	private void checkFields() {

		// TODO Mettere a posto

		try {

			String username = request.getString(FieldsNames.USERNAME);
			int hashCode = (int) request.get(FieldsNames.HASHCODE);

			noErrors &= roomChecker.isUserOnline(username);
			noErrors &= roomChecker.checkHashCode(username, hashCode);

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

				String roomName = request.getString(FieldsNames.ROOM_NAME);
				if (!roomChecker.checkRoomName(roomName)) {
					response.put(FieldsNames.SERVICE_TYPE,
							FieldsNames.ROOM_CREATE);
					noErrors = false;
					break;
				}

				gameDataManager.newRoom(roomName);

			} catch (RoomAlreadyExistsException e) {
				roomChecker.getRoomAlreadyExistsError();
				response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_CREATE);
				noErrors = false;
				break;
			}

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
			Player player = new Player(request.getString(FieldsNames.USERNAME));
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			
			Room room = gameDataManager.getRoomByName(roomName);
			room.addPlayer(player);
			response.put(FieldsNames.ROOM_NAME, roomName);
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

			response.put(FieldsNames.ROOMS_LIST, roomsList);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateResponse() {
		try {

			if (!noErrors)
				response.put(FieldsNames.ERRORS, errors);

			response.put(FieldsNames.NO_ERRORS, noErrors);

			System.out.println("SERVER: " + response);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
