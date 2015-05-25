package services.game.subservices;

import game.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * 
 * A {@link GameStatus} subservice. After fully charged the game map the player
 * must send a Ready Message informing the server that he is ready to start the
 * game.
 * 
 * @author Micieli
 */
public class GameReady implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			Player player = GameDataManager.getInstance()
					.getRoomByName(roomName).getPlayerByName(username);
			player.setStatus(request.getBoolean(FieldsNames.GAME_READY));

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
		return FieldsNames.GAME_READY;
	}
}