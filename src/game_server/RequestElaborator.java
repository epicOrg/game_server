package game_server;

import org.json.JSONException;
import org.json.JSONObject;

import services.Register;
import services.Service;
import services.Unknown;

public class RequestElaborator {
	
	private JSONObject json;
	
	public RequestElaborator(JSONObject json) {
		super();
		this.json = json;
	}
	
	public Service setService() throws JSONException {
		switch( json.getString("SERVICE") ) {
			case "REGISTER":
				return new Register(json);
			default:
				//TODO
				return new Unknown();
		
		}
	}
}
