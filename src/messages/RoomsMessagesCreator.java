package messages;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

	public JSONObject generateNameInvalidRespose() {

		String error = RoomsFields.ROOM_CREATE_ERROR_INVALID_NAME.toString();
		JSONObject response = getCreateFailedMessage(error);
		return response;
	}

	public JSONObject generateRoomExistMessage() {

		String error = RoomsFields.ROOM_CREATE_ERROR_ALREADY_PRESENT.toString();
		JSONObject response = getCreateFailedMessage(error);
		return response;

	}

	public JSONObject generateRommListMessage(ArrayList<Room> rooms) {

		JSONObject response = new JSONObject();
		JSONObject roomsList = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE_TYPE.toString(), RoomsFields.ROOMS_LIST.toString());
			response.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
			response.put(CommonFields.NO_ERRORS.toString(), true);

			for (Room room : rooms) {
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(RoomsFields.ROOM_MAX_PLAYERS.toString(), room.getMaxPlayers());
				roomDescription.put(RoomFields.ROOM_CURRENT_PLAYERS.toString(), room.getSize());
				roomsList.put(room.getName(), roomDescription);
			}

			response.put(RoomsFields.ROOMS_LIST.toString(), roomsList);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	public JSONObject generateJoinResponse(boolean result, String roomName) {

		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
			response.put(ServicesFields.SERVICE_TYPE.toString(), RoomsFields.ROOM_JOIN.toString());
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
			response.put(ServicesFields.SERVICE_TYPE.toString(), RoomsFields.ROOM_CREATE.toString());
			response.put(ServicesFields.SERVICE.toString(), ServicesFields.ROOMS.toString());
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
