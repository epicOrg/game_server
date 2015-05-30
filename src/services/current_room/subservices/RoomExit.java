package services.current_room.subservices;

import messages.CurrentRoomMessagesCreator;
import messages.fields_names.CommonFields;
import messages.fields_names.RoomFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Room;

/**
 * A {@link RoomActions} sub-service that execute the exit request of client
 * removing him from the {@link Room} player list.
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

		// TODO DEBUG PRINT
		// System.out.println("Exit");

		try {

			String playerName = request.getString(CommonFields.USERNAME.toString());
			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
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
		return RoomFields.ROOM_EXIT.toString();
	}
}
