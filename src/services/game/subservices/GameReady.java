package services.game.subservices;

import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.IService;

public class GameReady implements IService {

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			Player player = GameDataManager.getInstance().getRoomByName(roomName).getPlayerByName(username);
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
