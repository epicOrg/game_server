package services.game.subservices;

import fields_names.FieldsNames;
import game.model.Player;
import game.model.PlayerStatus;

import java.util.HashMap;

import org.json.JSONObject;

import services.IExtendedService;
import services.IService;
import services.game.Game;

/**
 * A {@link Game} sub-service. Allows the {@link Player} to update his
 * {@link PlayerStatus} during the game.
 * 
 * @author Micieli
 * @date 2015/05/24
 */
public class GameStatus implements IExtendedService {

	private HashMap<String, IService> subservices;

	public GameStatus() {
		super();
		subservices = new HashMap<>();
	}

	@Override
	public JSONObject start(JSONObject request) {

		// TODO DEBUG PRINT
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
