package services.subservices.current_room;

import game.model.Room;
import messages.CurrentRoomMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;

public class PlayerListService implements IService{
	
	private CurrentRoomMessagesCreator messagesCreator;
	
	public PlayerListService() {
		super();
		messagesCreator = new CurrentRoomMessagesCreator();
	}
	
	@Override
	public JSONObject start(JSONObject request) {
		System.out.println("List players request");
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
