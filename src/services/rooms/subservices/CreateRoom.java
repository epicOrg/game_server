package services.rooms.subservices;

import messages.RoomsMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.current_room.CurrentRoom;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;
import fields_name.FieldsNames;

/**
 * A {@link CurrentRoom} subservice that allow the client to create a new
 * {@link Room}, choosing also the <code>Room</code> name.
 * 
 * @author Micieli
 * @date 2015/05/24
 */

public class CreateRoom implements IService {

	// private int

	private RoomsMessagesCreator messagesCreator;
	private RoomChecker roomChecker;

	public CreateRoom() {
		super();
		messagesCreator = new RoomsMessagesCreator();
		roomChecker = new RoomChecker();
	}

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(request);
		try {
			String roomName = null;
			int numberOfTeams = 0;
			int numberOfPlayrXTeam = 0;
			try {
				roomName = request.getString(FieldsNames.ROOM_NAME);
				numberOfTeams = request.getInt(FieldsNames.ROOM_TEAMS_NUMBER);
				numberOfPlayrXTeam = request
						.getInt(FieldsNames.ROOM_TEAMS_DIMENSION);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (!(roomChecker.checkRoomName(roomName)
					 && roomChecker.chekNumberOfPlayer(numberOfPlayrXTeam) && 
					roomChecker.chekNumberOfTeams(numberOfTeams))) {
				return messagesCreator.generateNameInvalidRespose();
			}

			GameDataManager.getInstance().newRoom(roomName, numberOfTeams,
					numberOfPlayrXTeam);

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
