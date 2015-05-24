package services.subservices.current_room;

import game.model.Room;
import messages.CurrentRoomMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

public class RoomExit implements IService {
	
	private CurrentRoomMessagesCreator messagesCreator;

	public RoomExit() {
		super();
		messagesCreator = new CurrentRoomMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
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
