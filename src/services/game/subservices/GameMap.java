package services.game.subservices;

import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import fields_name.FieldsNames;
import game.model.Room;
import services.IService;
import services.game.Game;

/**
 * A {@link Game} sub-service. This service sends to the client the generated
 * map for the current {@link Room} in which the player is entered.
 * 
 * @author Micieli
 * @date 2015/05/24
 * @see game.map
 */
public class GameMap implements IService {

	private GameMessagesCreator messagesCreator;

	public GameMap() {
		super();
		this.messagesCreator = new GameMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {

		// TODO DEBUG PRINT
		System.out.println(getName());

		try {
			
			Room room = null;
			
			try {
				room = GameDataManager.getInstance().getRoomByName(
						request.getString(FieldsNames.ROOM_NAME));
			} catch (NoSuchRoomException e) {
				e.printStackTrace();
				return null;
			}
			
			return messagesCreator.generateMapMessage(room.getRoomMapSelector().getMap(), room
					.getRoomMapSelector().getSpawnPoint());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return FieldsNames.GAME_MAP;
	}

}
