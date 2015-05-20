package services;

import java.net.InetAddress;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.messages.RoomsMessagesCreator;
import voip.NetUtils;
import check_fields.FieldsNames;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.FullRoomException;
import exceptions.MissingFieldException;
import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.Player;
import game.Room;

/**
 * @author Micieli
 * @author Noris
 * @author Torlaschi
 * @date 2015/04/18
 */

public class RoomService implements IService {

	private GameDataManager gameDataManager;

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private RoomChecker roomChecker;
	private RoomsMessagesCreator messagesCreator;

	public RoomService() {
		gameDataManager = GameDataManager.getInstance();
		messagesCreator = new RoomsMessagesCreator();
	}

	@Override
	public JSONObject start() {

		try {

			checkFields();
			if (roomChecker.noErrors())
				runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));

		} catch (JSONException | MissingFieldException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		generateResponse();
		return jsonResponse;

	}

	private void checkFields() throws MissingFieldException {

		// TODO Mettere a posto

		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			int hashCode = jsonRequest.getInt(FieldsNames.HASHCODE);

			roomChecker.isUserOnline(username);
			roomChecker.checkHashCode(username, hashCode);

		} catch (JSONException e) {
			throw new MissingFieldException();
		}

	}

	private void runService(String serviceType) throws MissingFieldException {

		try {

			switch (serviceType) {

			case FieldsNames.ROOM_CREATE:

				createRoom();
				break;

			case FieldsNames.ROOMS_LIST:
				messagesCreator.addRoomsList(jsonResponse, gameDataManager.getRooms());
				jsonResponse.put(FieldsNames.SERVICE_TYPE,
						FieldsNames.ROOMS_LIST);
				break;
			case FieldsNames.ROOM_JOIN:
				jsonResponse.put(FieldsNames.SERVICE_TYPE,
						FieldsNames.ROOM_JOIN);
				addPlayer();
				break;
			default:
				break;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected void createRoom() throws MissingFieldException, JSONException {
		try {

			String roomName;
			try {
				roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			} catch (JSONException e) {
				throw new MissingFieldException();
			}

			if (!roomChecker.checkRoomName(roomName)) {
				jsonResponse.put(FieldsNames.SERVICE_TYPE,
						FieldsNames.ROOM_CREATE);
				return;
			}

			gameDataManager.newRoom(roomName);

		} catch (RoomAlreadyExistsException e) {
			roomChecker.getRoomAlreadyExistsError();
			jsonResponse.put(FieldsNames.SERVICE_TYPE,
					FieldsNames.ROOM_CREATE);
			return;
		}

		jsonResponse.put(FieldsNames.SERVICE_TYPE,
				FieldsNames.ROOMS_LIST);
		messagesCreator.addRoomsList(jsonResponse, gameDataManager.getRooms());
		return;
	}

	private void addPlayer() throws MissingFieldException {

		Player player;
		String roomName;

		try {

			player = new Player(jsonRequest.getString(FieldsNames.USERNAME));
			InetAddress address = OnlineManager.getInstance()
					.getIpAddressByUsername(player.getUsername());
			player.getAudioData().setIp(NetUtils.getIpByInetAddress(address));
			roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);

		} catch (JSONException e) {
			throw new MissingFieldException();
		}

		try {

			Room room = gameDataManager.getRoomByName(roomName);
			jsonResponse.put(FieldsNames.ROOM_NAME, roomName);
			room.addPlayer(player);
			jsonResponse.put(FieldsNames.RESULT, true);

		} catch (JSONException e) {
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			joinFailed();
			e.printStackTrace();
		} catch (FullRoomException e) {
			// TODO Auto-generated catch block
			roomChecker.getFullRoomError();
			joinFailed();
			e.printStackTrace();
		}
	}

	private void joinFailed() {
		try {

			jsonResponse.put(FieldsNames.RESULT, false);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void generateResponse() {
		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.ROOMS);
			jsonResponse.put(FieldsNames.NO_ERRORS, roomChecker.noErrors());

			if (!roomChecker.noErrors())
				jsonResponse.put(FieldsNames.ERRORS, roomChecker.getErrors());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		roomChecker = new RoomChecker();
	}
}
