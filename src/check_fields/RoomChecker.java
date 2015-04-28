package check_fields;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class RoomChecker extends ServiceChecker {

	public void getRoomAlreadyExistsError() {
		try {

			JSONArray roomExistsError = new JSONArray();
			roomExistsError.put(FieldsNames.ROOM_CREATE_ERROR_ALREADY_PRESENT);
			errors.put(FieldsNames.ERRORS, roomExistsError);
			noErrors = false;

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getFullRoomError() {
		try {

			JSONArray roomFullError = new JSONArray();
			roomFullError.put(FieldsNames.ROOM_JOIN_ERROR_FULL);
			errors.put(FieldsNames.ERRORS, roomFullError);
			noErrors = false;

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the room name is valid.
	 * 
	 * @param roomName
	 * @return true if the room name is valid, false otherwise
	 */
	public boolean checkRoomName(String roomName) {

		boolean fieldIsOk = true;

		try {

			JSONArray roomNameError = new JSONArray();

			if (roomName.length() < FieldsValues.ROOMNAME_MIN_LENGTH) {
				roomNameError.put(FieldsNames.ROOM_CREATE_ERROR_INVALID_NAME);
				fieldIsOk = false;
			}

			if (roomName.length() > FieldsValues.ROOMNAME_MAX_LENGTH) {
				roomNameError.put(FieldsNames.ROOM_CREATE_ERROR_INVALID_NAME);
				fieldIsOk = false;
			}

			if (!fieldIsOk) {
				errors.put(FieldsNames.ERRORS, roomNameError);
				noErrors = false;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fieldIsOk;
	}

}
