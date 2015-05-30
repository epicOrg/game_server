package check_fields;

/**
 * Checking of the room's creation client request fields.
 * 
 * @author Noris
 * @date 2015/04/19
 * @see Rooms
 */

public class RoomChecker {

	private static final int MAX_TEAMS = 5;
	private static final int MAX_PLAYERS_PER_TEAM = 5;

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

	public boolean chekNumberOfTeams(int numbersOfTeams) {
		if (numbersOfTeams > MAX_TEAMS)
			return false;
		return true;
	}

	public boolean chekNumberOfPlayer(int numberOfPlayersPerTeam) {
		if (numberOfPlayersPerTeam > MAX_PLAYERS_PER_TEAM)
			return false;
		return true;
	}
}
