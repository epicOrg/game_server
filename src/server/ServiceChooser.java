package server;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import services.Audio;
import services.Encrypt;
import services.Game;
import services.IService;
import services.Logout;
import services.Polling;
import services.Register;
import services.Unknown;
import services.current_room.CurrentRoom;
import services.rooms.Rooms;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public class ServiceChooser {

	private HashMap<String, IService> services;
	private ServicesInitializer initializer;

	public ServiceChooser() {
		initializer = new ServicesInitializer();
		services = new HashMap<>();
		initMap();
	}

	private void initMap() {
		
		ArrayList<IService> services = initializer.getServices();
		for (IService service : services) {
			addService(service.getName(), service);
		}

	}

	public IService chooseService(JSONObject json) {

		String serviceName;
		try {
			serviceName = json.getString(FieldsNames.SERVICE);
		} catch (JSONException e) {
			e.printStackTrace();
			serviceName = null;
		}

		IService service = services.get(serviceName);
		if (service == null)
			service = services.get(FieldsNames.UNKNOWN);

		return service;
	}

	public void addService(String name, IService service) {
		services.put(name, service);
	}
}