package game_server;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.CurrentRoom;
import services.Game;
import services.IService;
import services.Login;
import services.Register;
import services.RoomService;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public class RequestElaborator {

	private HashMap<String, IService> services = new HashMap<>();

	public RequestElaborator() {
		initMap();
	}

	private void initMap() {
		addService(FieldsNames.REGISTER, new Register());
		addService(FieldsNames.LOGIN, new Login());
		addService(FieldsNames.CALL, new Call());
		addService(FieldsNames.ROOMS, new RoomService());
		addService(FieldsNames.CURRENT_ROOM, new CurrentRoom());
		addService(FieldsNames.GAME, new Game());
	}

	public IService chooseService(JSONObject json) {

		String serviceName;
		try {
			serviceName = json.getString(FieldsNames.SERVICE);
		} catch (JSONException e) {
			serviceName = null;
		}

		IService service = services.get(serviceName);
		if (service == null)
			service = services.get(FieldsNames.UNKNOWN);

		service.setRequest(json);
		return service;
	}

	public void addService(String name, IService service) {
		services.put(name, service);
	}
}
