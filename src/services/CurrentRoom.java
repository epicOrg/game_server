package services;

import game.RoomPlayersUpdater;
import game.model.PlayerEventListener;
import game.model.Room;

import java.util.HashMap;

import messages.CurrentRoomMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * CurrentRoom Service provides information about the <code>Room</code> in which the client is entered.
 * Gives the complete Players list currently in the <code>Room</code> updated in real time according to 
 * Player getting in or out from the <code>Room</code>
 * It elaborates also the client request to exit from a particular <code>Room</code>
 * 
 * 
 * @author Torlaschi
 * @author Luca
 * @date 2015/04/18
 * @see PlayerEventListener
 * @see RoomPlayersUpdater
 */

public class CurrentRoom implements IExtendedService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;
	private CurrentRoomMessagesCreator messagesCreator;
	private HashMap<String, IService> subServices;

	public CurrentRoom() {
		gameDataManager = GameDataManager.getInstance();
		messagesCreator = new CurrentRoomMessagesCreator();
		subServices = new HashMap<>();
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

	@Override
	public void addSubService(IService... subservices) {
		for (IService subService : subservices) {
			this.subServices.put(subService.getName()	, subService);
		}		
	}

	@Override
	public String getName() {
		return FieldsNames.CURRENT_ROOM;
	}
}
