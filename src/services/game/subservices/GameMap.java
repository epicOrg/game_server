package services.game.subservices;

import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import game.model.Room;
import services.IService;
import services.game.Game;

/**
 * 
 * A {@link Game} subservice. This service send to the client the generated map
 * for the current room in which the player is entered.
 * 
 * @author Micieli
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
			return messagesCreator.generateMapMessage(room.getRoomMapSelector()
					.getMap(), room.getRoomMapSelector().getSpawnPoint());

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
