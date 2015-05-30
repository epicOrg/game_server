package services.game.subservices;

import java.util.HashMap;

import exceptions.NoSuchRoomException;
import fields_names.GameFields;
import fields_names.RoomFields;
import game.map.MapDimension;
import game.map.MapJSONizer;
import game.model.PlayerStatus;
import game.model.Room;
import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import data_management.GameDataManager;

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
						request.getString(RoomFields.ROOM_NAME.toString()));
			} catch (NoSuchRoomException e) {
				e.printStackTrace();
				return null;
			}
			
			MapJSONizer jsoNizer = new MapJSONizer(room.getMap());
			jsoNizer.generateMap();

			PlayerStatus s = room.getMap().getSpawnPoint();
			System.out.println(s);
			return messagesCreator.generateMapMessage(jsoNizer.getMap(),MapJSONizer.getAdaptedSpawnPoints(s) );

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return GameFields.GAME_MAP.toString();
	}

}
