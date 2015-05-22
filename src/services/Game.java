package services;

import game.model.Player;
import game.model.Room;
import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class Game implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;
	private Room room;
	private GameMessagesCreator messagesCreator;

	public Game() {
		gameDataManager = GameDataManager.getInstance();
		messagesCreator = new GameMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();

		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return jsonResponse;
	}

	private void runService(String serviceType) {

		switch (serviceType) {
		case FieldsNames.GAME_STATUS:
			executeStatusService();
			break;
			
		case FieldsNames.GAME_MAP:
			executeMapService();
			break;
			
		case FieldsNames.GAME_POSITIONS:
			executePositionsService();
			break;
		}
	}

	private void executeStatusService() {
		if (jsonRequest.has(FieldsNames.GAME_READY)) {
			playerReady();
		} else if (jsonRequest.has(FieldsNames.GAME_EXIT)) {
			removePlayer();
		}
	}

	private void removePlayer() {

		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			String username = jsonRequest.getString(FieldsNames.USERNAME);
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

		jsonResponse = null;
	}

	protected void playerReady() {
		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			String username = jsonRequest.getString(FieldsNames.USERNAME);
			Player player = gameDataManager.getRoomByName(roomName).getPlayerByName(username);
			player.setStatus(jsonRequest.getBoolean(FieldsNames.GAME_READY));
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonResponse = null;
	}

	private void executePositionsService() {
		try {

			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			room = gameDataManager.getRoomByName(roomName);
			String username = jsonRequest.getString(FieldsNames.USERNAME);
			
			UpdatePlayerStatus(username);
			jsonResponse = messagesCreator.generatePositionMessage(username, room);
		} catch (JSONException e) {
			e.printStackTrace();
			jsonResponse = null;
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonResponse = null;
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void UpdatePlayerStatus(String username) throws JSONException,
			NoSuchPlayerException {
		JSONObject posObject = jsonRequest.getJSONObject(FieldsNames.GAME_POSITION);
		JSONObject dirObject = jsonRequest.getJSONObject(FieldsNames.GAME_DIRECTION);
		float xPos = (float) posObject.getDouble(FieldsNames.GAME_X);
		float yPos = (float) posObject.getDouble(FieldsNames.GAME_Y);
		float zPos = (float) posObject.getDouble(FieldsNames.GAME_Z);
		float xDir = (float) dirObject.getDouble(FieldsNames.GAME_X);
		float yDir = (float) dirObject.getDouble(FieldsNames.GAME_Y);
		float zDir = (float) dirObject.getDouble(FieldsNames.GAME_Z);

		

		Player player = room.getPlayerByName(username);
		player.getPlayerStatus().setPosition(xPos, yPos, zPos);
		player.getPlayerStatus().setDirection(xDir, yDir, zDir);
	}

	private void executeMapService() {

		try {

			try {
				room = gameDataManager.getRoomByName(jsonRequest.getString(FieldsNames.ROOM_NAME));
			} catch (NoSuchRoomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonResponse = messagesCreator.generateMapMessage(room.getRoomMapSelector().getMap(), 
					room.getRoomMapSelector().getSpawnPoint());			

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
