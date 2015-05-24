package services;

import game.RoomPlayersUpdater;
import game.model.PlayerEventListener;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * CurrentRoom Service provides information about the <code>Room</code> in which the client is entered.
 * Gives the complete Players list currently in the <code>Room</code> updated in real time according to 
 * Player getting in or out from the <code>Room</code>
 * It elaborates also the client request to exit from a particular <code>Room</code>
 * 
 * 
 * @author Torlaschi
 * @author Luca
 * @date 2015/04/18
 * @see PlayerEventListener
 * @see RoomPlayersUpdater
 */

public class CurrentRoom implements IExtendedService {

	private HashMap<String, IService> subServices;

	public CurrentRoom() {
		subServices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {
		String serviceType = null;
		try {
			serviceType = request.getString(FieldsNames.SERVICE_TYPE);
			//System.out.println(serviceType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		IService subService = subServices.get(serviceType);
		
		return subService == null? null : subService.start(request);
	}

	@Override
	public void addSubService(IService... subservices) {
		for (IService subService : subservices) {
			System.out.println(subService.getName() + " " + "added" );
			this.subServices.put(subService.getName()	, subService);
		}		
	}

	@Override
	public String getName() {
		return FieldsNames.CURRENT_ROOM;
	}
}
