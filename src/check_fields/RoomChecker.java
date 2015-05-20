package check_fields;


/**
 * @author Noris
 * @date 2015/04/19
 */

public class RoomChecker extends ServiceChecker {

	/**
	 * Check if the room name is valid.
	 * 
	 * @param roomName
	 * @return true if the room name is valid, false otherwise
	 */
	public boolean checkRoomName(String roomName) {

		boolean fieldIsOk = true;

		if (roomName.length() < FieldsValues.ROOMNAME_MIN_LENGTH) {
			fieldIsOk = false;
		}

		if (roomName.length() > FieldsValues.ROOMNAME_MAX_LENGTH) {
			fieldIsOk = false;
		}

		if (!fieldIsOk) {
			noErrors = false;
		}

		return fieldIsOk;
	}
}
