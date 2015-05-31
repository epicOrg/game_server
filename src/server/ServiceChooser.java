package server;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import fields_names.ServicesFields;
import services.IService;

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
			serviceName = json.getString(ServicesFields.SERVICE.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			serviceName = null;
		}

		IService service = services.get(serviceName);
		if (service == null)
			service = services.get(ServicesFields.UNKNOWN.toString());

		return service;
	}

	public void addService(String name, IService service) {
		services.put(name, service);
	}
}