package messages;

import fields_name.FieldsNames;
import game.model.Room;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Generate messages to update the player on room/game events.
 * 
 * @author Micieli
 * @date 2015/05/21
 */
public class UpdatingMessagesCreator {

	private CurrentRoomMessagesCreator currentRoomMessagesCreator;

	public UpdatingMessagesCreator() {
		super();
		this.currentRoomMessagesCreator = new CurrentRoomMessagesCreator();
	}

	/**
	 * Creates the message who informs the player that the game is started.
	 * 
	 * @return the message
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
	 * Creates the message who informs the player about a changing in
	 * {@link Room} players list.
	 * 
	 * @return the message
	 */
	public JSONObject generatePlayersListMessage(Room room) {
		return currentRoomMessagesCreator.generatePlayersListMessage(room);
	}

	/**
	 * Creates the message who inform the player that all the others player are
	 * ready to play, so the game can really starts.
	 * 
	 * @return the message
	 */
	public JSONObject generateGoMessage() {
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
	 * @return the complete message
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
