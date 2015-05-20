package services;

import java.net.InetAddress;
import java.util.ArrayList;

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

		} catch (JSONException  e) {
			e.printStackTrace();
		}
		return jsonResponse;

	}

	private void checkFields() {

		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			int hashCode = jsonRequest.getInt(FieldsNames.HASHCODE);

			roomChecker.isUserOnline(username);
			roomChecker.checkHashCode(username, hashCode);

		} catch (JSONException e) {
			e.printStackTrace();
		}

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

	protected void createRoom() {
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

	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		roomChecker = new RoomChecker();
	}
}
