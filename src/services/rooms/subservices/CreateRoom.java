package services.rooms.subservices;

import messages.RoomsMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;

/**
 * @author Micieli
 * @date 2015/05/24
 */

public class CreateRoom implements IService {

	private RoomsMessagesCreator messagesCreator;
	private RoomChecker roomChecker;

	public CreateRoom() {
		super();
		messagesCreator = new RoomsMessagesCreator();
		roomChecker = new RoomChecker();
	}

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {
			String roomName = null;
			try {
				roomName = request.getString(FieldsNames.ROOM_NAME);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (!roomChecker.checkRoomName(roomName)) {
				return messagesCreator.generateNameInvalidRespose();
			}

			GameDataManager.getInstance().newRoom(roomName);

		} catch (RoomAlreadyExistsException e) {
			return messagesCreator.generateRoomExistMessage();
		}

		return messagesCreator.generateRommListMessage(GameDataManager
				.getInstance().getRooms());
	}

	@Override
	public String getName() {
		return FieldsNames.ROOM_CREATE;
	}
}
