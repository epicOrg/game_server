package services.current_room.subservices;

import game.model.Room;
import messages.CurrentRoomMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * A {@link RoomActions} subservice that execute the exit request of client
 * removing him from the room player list
 * 
 * @author Micieli
 * @date 2015/05/24
 * @see Game
 */

public class RoomExit implements IService {

	private CurrentRoomMessagesCreator messagesCreator;

	public RoomExit() {
		super();
		messagesCreator = new CurrentRoomMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
		// System.out.println("Exit");
		try {
			String playerName = request.getString(FieldsNames.USERNAME);
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			room.removePlayer(room.getPlayerByName(playerName));
			return messagesCreator.generateExitResponse(true);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException | NoSuchPlayerException e) {
			return messagesCreator.generateExitResponse(false);
		}
		return null;
	}

	@Override
	public String getName() {
		return FieldsNames.ROOM_EXIT;
	}
}
