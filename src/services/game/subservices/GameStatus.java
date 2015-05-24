package services.game.subservices;

import java.util.HashMap;

import org.json.JSONObject;

import check_fields.FieldsNames;
import services.IExtendedService;
import services.IService;
import services.game.Game;

/**
 * 
 * A {@link Game} subservice.
 * Allows the player to update his status during the game.
 * 
 * @author Micieli
 */
public class GameStatus implements IExtendedService {
	
	private HashMap<String, IService> subservices;

	public GameStatus() {
		super();
		subservices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		
		if (request.has(FieldsNames.GAME_READY)) {
			return subservices.get(FieldsNames.GAME_READY).start(request);
		} else if (request.has(FieldsNames.GAME_EXIT)) {
			return subservices.get(FieldsNames.GAME_EXIT).start(request);
		}
		return null;
	}

	@Override
	public String getName() {
		return FieldsNames.GAME_STATUS;
	}

	@Override
	public void addSubService(IService... subservices) {
		for (IService service : subservices) {
			this.subservices.put(service.getName(), service);
		}		
	}

}
