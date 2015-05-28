package services.rooms.subservices;

import messages.RoomsMessagesCreator;

import org.json.JSONObject;

import services.IService;
import data_management.GameDataManager;
import fields_names.RoomsFields;

/**
 * @author Micieli
 * @date 2015/05/24
 */

public class RoomsList implements IService {

	private RoomsMessagesCreator messagesCreator;

	public RoomsList() {
		super();
		messagesCreator = new RoomsMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
		return messagesCreator.generateRommListMessage(GameDataManager.getInstance().getRooms());
	}

	@Override
	public String getName() {
		return RoomsFields.ROOMS_LIST.toString();
	}
}
