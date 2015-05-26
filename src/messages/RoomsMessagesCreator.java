package messages;

import game.model.Room;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Micieli
 * @date 2015/05/19
 */

public class RoomsMessagesCreator {

	public JSONObject generateNameInvalidRespose() {

		String error = FieldsNames.ROOM_CREATE_ERROR_INVALID_NAME;
		JSONObject response = getCreateFailedMessage(error);
		return response;
	}

	public JSONObject generateRoomExistMessage() {

		String error = FieldsNames.ROOM_CREATE_ERROR_ALREADY_PRESENT;
		JSONObject response = getCreateFailedMessage(error);
		return response;

	}
	
	public JSONObject generateRommListMessage(ArrayList<Room> rooms) {
		JSONObject response = new JSONObject();
		JSONObject roomsList = new JSONObject();

		try {
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOMS_LIST);
			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			response.put(FieldsNames.NO_ERRORS, true);
			for (Room room : rooms) {
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(FieldsNames.ROOM_MAX_PLAYERS,
						room.getMaxPlayers());
				roomDescription.put(FieldsNames.ROOM_CURRENT_PLAYERS,
						room.getSize());
				roomsList.put(room.getName(), roomDescription);
			}

			response.put(FieldsNames.ROOMS_LIST, roomsList);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}
	
	public JSONObject generateJoinResponse(boolean result ,String roomName){
					  
		JSONObject response = new JSONObject();
		
		try {
			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_JOIN);
			response.put(FieldsNames.ROOM_NAME, roomName);
			response.put(FieldsNames.RESULT, result);
			response.put(FieldsNames.NO_ERRORS, result);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return response;
	}	

	private JSONObject getCreateFailedMessage(String error) {
		JSONObject response = new JSONObject();
		try {
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_CREATE);
			response.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			response.put(FieldsNames.NO_ERRORS, false);
			JSONObject errorObj = new JSONObject();
			JSONArray errors = new JSONArray();
			errors.put(error);
			errorObj.put(FieldsNames.ERRORS, errors);
			response.put(FieldsNames.ERRORS, errorObj);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	

}
