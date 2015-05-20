package services.messages;

import game.Room;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Micieli
 * @date 2015/05/19
 */

public class RoomsMessagesCreator {

	public void addRoomsList(JSONObject object, ArrayList<Room> rooms) {
		
		JSONObject roomsList = new JSONObject();

		try {

			for (Room room : rooms) {
				JSONObject roomDescription = new JSONObject();
				roomDescription.put(FieldsNames.ROOM_MAX_PLAYERS, Room.MAX_PLAYERS);
				roomDescription.put(FieldsNames.ROOM_CURRENT_PLAYERS, room.getSize());
				roomsList.put(room.getName(), roomDescription);
			}

			object.put(FieldsNames.ROOMS_LIST, roomsList);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
