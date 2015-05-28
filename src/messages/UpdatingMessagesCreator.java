package messages;

import fields_names.CommonFields;
import fields_names.FieldsNames;
import fields_names.GameFields;
import fields_names.RoomFields;
import fields_names.ServicesFields;
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

			message.put(ServicesFields.SERVICE.toString(), ServicesFields.CURRENT_ROOM.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), RoomFields.ROOM_ACTIONS.toString());
			message.put(RoomFields.ROOM_ACTION.toString(), RoomFields.ROOM_START.toString());
			message.put(CommonFields.NO_ERRORS.toString(), true);

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
			message.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			message.put(GameFields.GAME_GO.toString(), true);
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
			message.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			message.put(GameFields.GAME_END.toString(), GameFields.GAME_INTERRUPTED.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
}
