package services.current_room.subservices;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import services.IExtendedService;
import services.IService;
import check_fields.FieldsNames;

public class RoomActions implements IService, IExtendedService {
	
	private HashMap<String, IService> subServices;

	public RoomActions() {
		super();
		subServices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {
		String roomAction = null;
		try {
			roomAction = request.getString(FieldsNames.ROOM_ACTION);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		IService subService = subServices.get(roomAction);
		
		if(subService != null)
			return subService.start(request);

		return null;
	}

	@Override
	public void addSubService(IService... subservices) {
		for (IService subService : subservices) {
			this.subServices.put(subService.getName(), subService);
		}		
	}

	@Override
	public String getName() {
		return FieldsNames.ROOM_ACTIONS;
	}
}
