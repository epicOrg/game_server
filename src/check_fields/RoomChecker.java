package check_fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class RoomChecker extends ServiceChecker {

	public RoomChecker(JSONObject errors) {
		super(errors);
	}

	public void getRoomAlreadyExistsError() {

		// TODO Sistemare

		try {

			JSONArray roomExistError = new JSONArray();
			roomExistError.put(FieldsNames.ROOM_CREATE_ERROR_ALREADYPRESENT);
			errors.put(FieldsNames.ERRORS, roomExistError);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
				roomNameError.put(FieldsNames.ROOM_CREATE_ERROR_INVALIDNAME);
				fieldIsOk = false;
			}

			if (roomName.length() > FieldsValues.ROOMNAME_MAX_LENGTH) {
				roomNameError.put(FieldsNames.ROOM_CREATE_ERROR_INVALIDNAME);
				fieldIsOk = false;
			}

			if (!fieldIsOk)
				errors.put(FieldsNames.ERRORS, roomNameError);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fieldIsOk;
	}

}
