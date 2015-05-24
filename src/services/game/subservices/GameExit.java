package services.game.subservices;

import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import game.model.Room;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.IService;

public class GameExit implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			GameDataManager gameDataManager = GameDataManager.getInstance();
			Player player = gameDataManager.getRoomByName(roomName).getPlayerByName(username);

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
