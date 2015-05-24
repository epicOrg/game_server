package services.current_room.subservices;

import game.model.Room;
import messages.CurrentRoomMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;

/**
 * A {@link Game} subservice that responds to the player list request of a client
 * providing the full list currently in the requested {@link Room}.
 * 
 * @author Micieli
 * @date 2015/05/24
 */


public class PlayerList implements IService {

	private CurrentRoomMessagesCreator messagesCreator;

	public PlayerList() {
		super();
		messagesCreator = new CurrentRoomMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {

		// TODO DEBUG
		System.out.println("Players list request.");

		try {

			String roomName = request.getString(FieldsNames.ROOM_NAME);
			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			return messagesCreator.generatePlayersListMessage(room);

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
		return FieldsNames.ROOM_PLAYER_LIST;
	}
}
