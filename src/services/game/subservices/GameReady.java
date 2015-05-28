package services.game.subservices;

import fields_names.CommonFields;
import fields_names.FieldsNames;
import fields_names.GameFields;
import fields_names.RoomFields;
import game.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * A {@link GameStatus} sub-service. After fully charged the game map the
 * {@link Player} must send a Ready Message informing the server that he's ready
 * to start the game.
 * 
 * @author Micieli
 * @date 2015/05/24
 */

public class GameReady implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			String username = request.getString(CommonFields.USERNAME.toString());
			Player player = GameDataManager.getInstance().getRoomByName(roomName)
					.getPlayerByName(username);
			player.setStatus(request.getBoolean(GameFields.GAME_READY.toString()));

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
		return GameFields.GAME_READY.toString();
	}
}
