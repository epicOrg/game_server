package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.messages.CurrentRoomMessagesCreator;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.MissingFieldException;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.Player;
import game.Room;
import game.Team;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class CurrentRoom implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;
	private CurrentRoomMessagesCreator messagesCreator;

	public CurrentRoom() {
		gameDataManager = GameDataManager.getInstance();
		messagesCreator = new CurrentRoomMessagesCreator();
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
		}

		return jsonResponse;
	}

	private void runService(String serviceType) {

		switch (serviceType) {
		case FieldsNames.ROOM_PLAYER_LIST:
			playerList();
			break;
		case FieldsNames.ROOM_ACTIONS:
			executeAction();
			break;
		}
	}

	private void playerList() {

		try {

			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);
			jsonResponse = messagesCreator.generatePlayersListMessage(room);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void executeAction() {

		String roomAction = null;
		try {
			roomAction = jsonRequest.getString(FieldsNames.ROOM_ACTION);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		switch (roomAction) {
		case FieldsNames.ROOM_EXIT:
			actionExit();
			break;
		case FieldsNames.ROOM_LIST_RECEIVED:
			actionListReceived();
			break;
		}
	}
	
	private void actionExit() {

		try {
			String playerName = jsonRequest.getString(FieldsNames.USERNAME);
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);
			room.removePlayer(room.getPlayerByName(playerName));
			jsonResponse = messagesCreator.generateExitResponse(true);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException | NoSuchPlayerException e) {
			jsonResponse = messagesCreator.generateExitResponse(false);
		}
	}

	private void actionListReceived() {
		jsonResponse = null;

		try {
			// String playerName = jsonRequest.getString(FieldsNames.USERNAME);
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);

			room.checkIfFull();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
