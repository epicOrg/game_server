package services.current_room.subservices;

import game.model.Room;
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
import exceptions.RoomCancelledException;

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
		} catch (RoomCancelledException e) {
			return messagesCreator.generateExitResponse(true);
		}

		return null;
	}

	@Override
	public String getName() {
		return RoomFields.ROOM_EXIT.toString();
	}
}
