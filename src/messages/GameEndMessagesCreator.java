package messages;

import game.model.RoomThread;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * Provides a builder for messages that the server has to send while the game is
 * over.
 * 
 * @author Micieli
 * @date 2015/05/21
 * @see RoomThread
 */
public class GameEndMessagesCreator {

	/**
	 * Generates message to inform about players who lose the game.
	 * 
	 * @return the message to send
	 */
	public JSONObject generateLoseMessage() {

		JSONObject loseMessage = new JSONObject();

		try {

			loseMessage.put(FieldsNames.SERVICE, FieldsNames.GAME);
			loseMessage.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			loseMessage.put(FieldsNames.GAME_END, FieldsNames.GAME_LOSE);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return loseMessage;
	}

	/**
	 * Generates message to inform about players who win the game.
	 * 
	 * @return the message to send
	 */
	public JSONObject generateWinMessage() {

		JSONObject winMessage = new JSONObject();

		try {

			winMessage.put(FieldsNames.SERVICE, FieldsNames.GAME);
			winMessage.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			winMessage.put(FieldsNames.GAME_END, FieldsNames.GAME_WIN);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return winMessage;
	}

	/**
	 * Generates message to inform about players who draw the game.
	 * 
	 * @return the message to send
	 */
	public JSONObject generateDrawMessage() {

		JSONObject message = new JSONObject();

		try {

			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			message.put(FieldsNames.GAME_END, FieldsNames.GAME_DRAW);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}
}
