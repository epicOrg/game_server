package services.rooms.subservices;

import messages.RoomsMessagesCreator;
import messages.fields_names.RoomFields;
import messages.fields_names.RoomsFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.current_room.CurrentRoom;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.RoomAlreadyExistsException;
import game.map.generation.GridMapGenerator;
import game.model.Room;

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

		try {

			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			int numberOfTeams = request.getInt(RoomsFields.ROOM_TEAMS_NUMBER
					.toString());
			int numberOfPlayrXTeam = request
					.getInt(RoomsFields.ROOM_TEAMS_DIMENSION.toString());

			if (!(roomChecker.checkRoomName(roomName)
					&& roomChecker.chekNumberOfPlayer(numberOfPlayrXTeam) && roomChecker
						.chekNumberOfTeams(numberOfTeams))) {
				return messagesCreator.generateNameInvalidRespose();
			}

			GameDataManager.getInstance().newRoom(roomName, numberOfTeams,
					numberOfPlayrXTeam, new GridMapGenerator());

		} catch (RoomAlreadyExistsException e) {
			return messagesCreator.generateRoomExistMessage();

		} catch (JSONException e) {
			e.printStackTrace();
			return messagesCreator.generateNameInvalidRespose();
		}

		return messagesCreator.generateRoomsListMessage(GameDataManager
				.getInstance().getRooms());
	}

	@Override
	public String getName() {
		return RoomsFields.ROOM_CREATE.toString();
	}
}
