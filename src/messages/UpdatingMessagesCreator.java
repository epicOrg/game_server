package messages;


import game.Room;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * 
 * Generate messages to update player on room/game events
 * 
 * @author Luca
 *
 */
public class UpdatingMessagesCreator {
	
	private CurrentRoomMessagesCreator currentRoomMessagesCreator;
	
	public UpdatingMessagesCreator() {
		super();
		this.currentRoomMessagesCreator = new CurrentRoomMessagesCreator();
	}

	/**
	 * Creates the message informing player that the game is started
	 * 
	 * @return		the complete message
	 */
	public JSONObject generateStartMessage() {

		JSONObject message = new JSONObject();

		try {

			message.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);
			message.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_START);
			message.put(FieldsNames.NO_ERRORS, true);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}
	
	/**
	 * Creates the message informing player about a changing in Room player list
	 * 
	 * @return		the complete message
	 */
	public JSONObject generatePlayersListMessage(Room room) {
		return currentRoomMessagesCreator.generatePlayersListMessage(room);
	}
	
	/**
	 * Creates the message informing player that all other player are ready to play, so the game can really starts
	 * 
	 * @return		the complete message
	 */
	public JSONObject generateGoMessage(){
		JSONObject message = new JSONObject();
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			message.put(FieldsNames.GAME_GO, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * Creates the message informing player that the game was interrupted
	 * 
	 * @return		the complete message
	 */
	public JSONObject generateExitMessage() {
		JSONObject message = new JSONObject();
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			message.put(FieldsNames.GAME_END, FieldsNames.GAME_INTERRUPTED);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
}
