package game_server;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.CurrentRoom;
import services.Game;
import services.Login;
import services.Register;
import services.RoomService;
import services.Service;
import services.Unknown;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class RequestElaborator {

	public Service chooseService(JSONObject json) {
		try {
			
			switch (json.getString(FieldsNames.SERVICE)) {
			case FieldsNames.REGISTER:
				return new Register(json);
			case FieldsNames.LOGIN:
				return new Login(json);
			case FieldsNames.CALL:
				return new Call(json);
			case FieldsNames.ROOMS:
				return new RoomService(json);
			case FieldsNames.CURRENT_ROOM:
				return new CurrentRoom(json);
			case FieldsNames.GAME:
				return new Game(json);
			default:
				return new Unknown();
			}
			
		} catch (JSONException e) {
			return new Unknown();
		}
	}

}
