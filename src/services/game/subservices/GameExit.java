package services.game.subservices;

import game.model.Player;
import game.model.Room;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 *  
 * A {@link GameStatus} subservice.
 * The client that request this service will terminate the game instantly. If
 * the game already started also other players currently playing in the room
 * will be forcedly removed from the game.
 * 
 * @author Micieli
 *
 */
public class GameExit implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			GameDataManager gameDataManager = GameDataManager.getInstance();
			Player player = gameDataManager.getRoomByName(roomName)
					.getPlayerByName(username);

			Room room = gameDataManager.getRoomByName(roomName);
			room.removePlayer(player);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		return FieldsNames.GAME_EXIT;
	}
}
