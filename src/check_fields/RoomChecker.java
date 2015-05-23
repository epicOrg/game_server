package check_fields;

/**
 * Checking of the room's creation client request fields.
 * 
 * @author Noris
 * @date 2015/04/19
 * @see RoomService
 */

public class RoomChecker {

	/**
	 * Check if the room name is valid.
	 * 
	 * @param roomName
	 *            the name of the room
	 * @return true if the room's name is valid, false otherwise
	 */
	public boolean checkRoomName(String roomName) {

		boolean fieldIsOk = true;

		if (roomName.length() < FieldsValues.ROOMNAME_MIN_LENGTH) {
			fieldIsOk = false;
		}

		if (roomName.length() > FieldsValues.ROOMNAME_MAX_LENGTH) {
			fieldIsOk = false;
		}

		return fieldIsOk;
	}
}
