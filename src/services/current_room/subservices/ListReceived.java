package services.current_room.subservices;

import game.model.Room;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;

/**
 * A {@link RoomActions} subservice that acquire client message communicating that
 * they received the player list after entering in a {@link Room}
 * 
 * @author Micieli
 * @date 2015/05/24
 * @see Game
 */

public class ListReceived implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		try {

			String roomName = request.getString(FieldsNames.ROOM_NAME);
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
		return FieldsNames.ROOM_LIST_RECEIVED;
	}
}
