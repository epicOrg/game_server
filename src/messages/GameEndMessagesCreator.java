package messages;

import fields_names.GameFields;
import fields_names.ServicesFields;
import game.model.RoomThread;

import org.json.JSONException;
import org.json.JSONObject;

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

			loseMessage.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			loseMessage.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			loseMessage.put(GameFields.GAME_END.toString(), GameFields.GAME_LOSE.toString());

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

			winMessage.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			winMessage.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			winMessage.put(GameFields.GAME_END.toString(), GameFields.GAME_WIN.toString());

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

			message.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			message.put(GameFields.GAME_END.toString(), GameFields.GAME_DRAW.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}
}
