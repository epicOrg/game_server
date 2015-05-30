package services.encrypt;

import java.util.HashMap;

import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IExtendedService;
import services.IService;

/**
 * @author Noris
 * @date 2015/04/25
 */

public class Encrypt implements IExtendedService {

	private HashMap<String, IService> subServices;

	public Encrypt() {
		subServices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject jsonRequest) {

		String serviceType = null;

		try {
			serviceType = jsonRequest.getString(ServicesFields.SERVICE_TYPE.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		IService subservice = subServices.get(serviceType);
		if (subservice == null)
			return null;

		return subservice.start(jsonRequest);
	}

	@Override
	public void addSubService(IService... subservices) {
		for (IService service : subservices) {
			this.subServices.put(service.getName(), service);
		}

	}

	@Override
	public String getName() {
		return ServicesFields.ENCRYPT.toString();
	}

}
