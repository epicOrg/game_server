package game_server;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.Call;
import services.Login;
import services.Register;
import services.Service;
import services.Unknown;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class RequestElaborator {
	public RequestElaborator() {
		super();
		// TODO REMOVE (DEBUG PRINT)
		// System.out.println(json.toString());
	}

	public Service chooseService(JSONObject json) throws JSONException {

		switch (json.getString(FieldsNames.SERVICE)) {
		case FieldsNames.REGISTER:
			return new Register(json);
		case FieldsNames.LOGIN:
			return new Login(json);
		case FieldsNames.CALL:
			return new Call(json);
		default:
			return new Unknown();

		}
	}
}
