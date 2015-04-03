package game_server;

import org.json.JSONException;
import org.json.JSONObject;

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

	private JSONObject json;

	public RequestElaborator(JSONObject json) {
		super();
		// TODO (DEBUG PRINT)
		// System.out.println(json.toString());
		this.json = json;
	}

	public Service setService() throws JSONException {
		switch (json.getString("SERVICE")) {
		case "REGISTER":
			return new Register(json);
		case "LOGIN":
			return new Login(json);
		case "CALL":
			return new Call(json);
		default:
			// TODO
			return new Unknown();

		}
	}
}
