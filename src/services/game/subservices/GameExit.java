package services.game.subservices;

import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import game.model.Room;
import messages.fields_names.CommonFields;
import messages.fields_names.GameFields;
import messages.fields_names.RoomFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import data_management.GameDataManager;

/**
 * A {@link GameStatus} sub-service. The client who requests this service will
 * terminate the game instantly. If the game is already started, also the other
 * players currently playing in the {@link Room} will be forcedly removed from
 * the game.
 * 
 * @author Micieli
 * @date 2015/05/24
 */
public class GameExit implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		try {

			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			String username = request.getString(CommonFields.USERNAME.toString());
			GameDataManager gameDataManager = GameDataManager.getInstance();
			Player player = gameDataManager.getRoomByName(roomName).getPlayerByName(username);

			Room room = gameDataManager.getRoomByName(roomName);
			room.removePlayer(player);

		} catch (JSONException e) {
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
		return GameFields.GAME_EXIT.toString();
	}
}
