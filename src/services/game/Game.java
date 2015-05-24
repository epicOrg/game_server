package services.game;

import java.util.HashMap;

import game.model.Player;
import game.model.Room;
import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import services.IExtendedService;
import services.IService;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class Game implements IExtendedService {

	private HashMap<String, IService> subServices;

	public Game() {
		subServices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {
		IService subservice = null;
		try {
			String serviceType = request.getString(FieldsNames.SERVICE_TYPE);
			subservice = subServices.get(serviceType);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return subservice == null? null: subservice.start(request);
	}

	@Override
	public String getName() {
		return FieldsNames.GAME;
	}

	@Override
	public void addSubService(IService... subservices) {
		for (IService service : subservices) {
			this.subServices.put(service.getName(), service);
		}		
	}
}
