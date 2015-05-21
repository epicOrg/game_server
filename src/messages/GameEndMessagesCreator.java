package messages;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

public class GameEndMessagesCreator {
	
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
