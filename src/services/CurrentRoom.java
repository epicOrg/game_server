package services;

import org.json.JSONObject;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class CurrentRoom implements Service {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;
	
	public CurrentRoom(JSONObject json) {
		jsonRequest = json;
		jsonResponse = new JSONObject();
	}


	@Override
	public String start() {
		// TODO
		return null;
	}

}
