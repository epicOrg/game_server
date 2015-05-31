package messages;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.rooms.subservices.CreateRoom;
import services.rooms.subservices.JoinPlayer;
import check_fields.RoomChecker;
import fields_names.CommonFields;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import fields_names.ServicesFields;
import game.model.Room;

/**
 * @author Micieli
 * @date 2015/05/19
 */

public class RoomsMessagesCreator {

	/**
	 * Generate a message to warn the client that the given <code>Room</code>
	 * name is invalid.
	 * 	
	 * @return the complete message to send
	 * @see RoomChecker
	 * @see CreateRoom
	 * */
	public JSONObject generateNameInvalidRespose() {

		String error = RoomsFields.ROOM_CREATE_ERROR_INVALID_NAME.toString();
		JSONObject response = getCreateFailedMessage(error);
		return response;
	}

	/**
	 * Generate a message to warn the client that the given <code>Room</code>
	 * name is already used.
	 * 
	 * @return the complete message to send
	 * @see RoomChecker
	 * @see CreateRoom
	 */
	public JSONObject generateRoomExistMessage() {

		String error = RoomsFields.ROOM_CREATE_ERROR_ALREADY_PRESENT.toString();
		JSONObject response = getCreateFailedMessage(error);
		return response;

	}

	/**
	 * Generate a message that contains all the rooms available in the server
	 * 
	 * @param rooms
	 *            the list of room to send
	 * @return the complete message to send
	 */
	public JSONObject generateRoomsListMessage(ArrayList<Room> rooms) {

		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE_TYPE.toString(),
					RoomsFields.ROOMS_LIST.toString());
			response.put(ServicesFields.SERVICE.toString(),
					ServicesFields.ROOMS.toString());
			response.put(CommonFields.NO_ERRORS.toString(), true);
			JSONObject roomsList = formatRoomList(rooms);

			response.put(RoomsFields.ROOMS_LIST.toString(), roomsList);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	private JSONObject formatRoomList(ArrayList<Room> rooms)
			throws JSONException {
		JSONObject roomsList = new JSONObject();
		for (Room room : rooms) {
			JSONObject roomDescription = new JSONObject();
			roomDescription.put(RoomsFields.ROOM_MAX_PLAYERS.toString(),
					room.getMaxPlayers());
			roomDescription.put(RoomFields.ROOM_CURRENT_PLAYERS.toString(),
					room.getSize());
			roomsList.put(room.getName(), roomDescription);
		}
		return roomsList;
	}

	/**
	 * Genetare a message advice the player about the join request result.
	 * 
	 * @param result		the join result
	 * @param roomName		the room name
	 * @return				the message to send
	 * @see JoinPlayer
	 */
	public JSONObject generateJoinResponse(boolean result, String roomName) {

		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE.toString(),
					ServicesFields.ROOMS.toString());
			response.put(ServicesFields.SERVICE_TYPE.toString(),
					RoomsFields.ROOM_JOIN.toString());
			response.put(RoomFields.ROOM_NAME.toString(), roomName);
			response.put(CommonFields.RESULT.toString(), result);
			response.put(CommonFields.NO_ERRORS.toString(), result);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	private JSONObject getCreateFailedMessage(String error) {

		JSONObject response = new JSONObject();

		try {
			response.put(ServicesFields.SERVICE_TYPE.toString(),
					RoomsFields.ROOM_CREATE.toString());
			response.put(ServicesFields.SERVICE.toString(),
					ServicesFields.ROOMS.toString());
			response.put(CommonFields.NO_ERRORS.toString(), false);
			JSONObject errorObj = new JSONObject();
			JSONArray errors = new JSONArray();
			errors.put(error);
			errorObj.put(CommonFields.ERRORS.toString(), errors);
			response.put(CommonFields.ERRORS.toString(), errorObj);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

}
