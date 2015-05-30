package services.current_room.subservices;

import exceptions.NoSuchRoomException;
import game.model.Room;
import messages.fields_names.RoomFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import data_management.GameDataManager;

/**
 * A {@link RoomActions} sub-service that acquire client message communicating
 * that they received the player list after entering in a {@link Room}.
 * 
 * @author Micieli
 * @date 2015/05/24
 * @see Game
 */

public class ListReceived implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		try {

			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			room.checkIfFull();

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		return RoomFields.ROOM_LIST_RECEIVED.toString();
	}
}
