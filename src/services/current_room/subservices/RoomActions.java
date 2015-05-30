package services.current_room.subservices;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import services.IExtendedService;
import services.IService;
import services.game.Game;
import fields_names.RoomFields;

/**
 * A {@link Game} sub-service that is in turn an instance of
 * <code>IExtendedService</code> executing the different Room Actions requested
 * by the client.
 * 
 * @author Micieli
 * @date 2015/05/24
 */

public class RoomActions implements IExtendedService {

	private HashMap<String, IService> subServices;

	public RoomActions() {
		super();
		subServices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {

		String roomAction = null;

		try {
			roomAction = request.getString(RoomFields.ROOM_ACTION.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		IService subService = subServices.get(roomAction);

		if (subService != null)
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
		return RoomFields.ROOM_ACTIONS.toString();
	}
}
