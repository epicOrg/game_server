package game_server;

import org.json.JSONException;
import org.json.JSONObject;

import services.Login;
import services.Register;
import services.Service;
import services.Unknown;

/**
 * @author	Noris
 * @since	2015-03-26
 */

public class RequestElaborator {
	
	private JSONObject json;
	
	public RequestElaborator(JSONObject json) {
		super();
		//TODO (DEBUG PRINT)
		//System.out.println(json.toString());
		this.json = json;
	}
	
	public Service setService() throws JSONException {
		switch( json.getString("SERVICE") ) {
			case "REGISTER":
				return new Register(json);
			case "LOGIN":
				return new Login(json);
			default:
				//TODO
				return new Unknown();

		}
	}
}
