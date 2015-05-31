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

		return generateEndMessage(GameFields.GAME_LOSE.toString());
	}

	/**
	 * Generates message to inform about players who win the game.
	 * 
	 * @return the message to send
	 */
	public JSONObject generateWinMessage() {

		return generateEndMessage(GameFields.GAME_WIN.toString());
	}

	/**
	 * Generates message to inform about players who draw the game.
	 * 
	 * @return the message to send
	 */
	public JSONObject generateDrawMessage() {

		return generateEndMessage(GameFields.GAME_DRAW.toString());
	}

	private JSONObject generateEndMessage(String status) {
		JSONObject message = new JSONObject();
		try {

			message.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_STATUS.toString());
			message.put(GameFields.GAME_END.toString(), status);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
}
