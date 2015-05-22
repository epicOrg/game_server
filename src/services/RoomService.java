package services;

import game.model.Player;
import game.model.Room;

import java.net.InetAddress;

import messages.RoomsMessagesCreator;
import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import voip.NetUtils;
import check_fields.FieldsNames;
import check_fields.RoomChecker;
import data_management.GameDataManager;
import exceptions.FullRoomException;
import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;

/**
 * 
 * <code>RoomService</code> manages {@link Room} actions.
 * Allows Room creation with a specific name request from client.
 * Provides current available <code>Rooms</code>.
 * Enable the client to enter in a specific existing <code>Room</code>
 * 
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
	public JSONObject start(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		roomChecker = new RoomChecker();

		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));

		} catch (JSONException  e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	private void runService(String serviceType) {

		switch (serviceType) {

		case FieldsNames.ROOM_CREATE:
			createRoom();
			break;

		case FieldsNames.ROOMS_LIST:
			jsonResponse = messagesCreator.generateRommListMessage(gameDataManager.getRooms());
			break;
			
		case FieldsNames.ROOM_JOIN:
			addPlayer();
			break;
			
		default:
			break;
		}
	}

	private void createRoom() {
		try {

			String roomName = null;
			try {
				roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (!roomChecker.checkRoomName(roomName)) {
				jsonResponse = messagesCreator.generateNameInvalidRespose();
				return;
			}

			gameDataManager.newRoom(roomName);

		} catch (RoomAlreadyExistsException e) {
			jsonResponse = messagesCreator.generateRoomExistMessage();
			return;
		}
		
		jsonResponse = messagesCreator.generateRommListMessage(gameDataManager.getRooms());
		return;
	}

	private void addPlayer(){

		Player player = null;
		String roomName = null;

		try {

			player = new Player(jsonRequest.getString(FieldsNames.USERNAME));
			InetAddress address = OnlineManager.getInstance()
					.getIpAddressByUsername(player.getUsername());
			player.getAudioData().setIp(NetUtils.getIpByInetAddress(address));
			roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {

			Room room = gameDataManager.getRoomByName(roomName);			
			room.addPlayer(player);
			jsonResponse = messagesCreator.generateJoinResponse(true, roomName);

		} catch (NoSuchRoomException | FullRoomException e ) {
			// TODO Auto-generated catch block
			jsonResponse = messagesCreator.generateJoinResponse(false, roomName);
			e.printStackTrace();
		} 
	}
}
