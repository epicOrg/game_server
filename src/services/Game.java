package services;

import exceptions.MissingFieldException;
import game.map.IMapGenerator;
import game.map.MapObject;
import game.map.SimpleMapGenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class Game implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private IMapGenerator mapGenerator;

	public Game() {
	}

	@Override
	public String start() {

		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));
		} catch (JSONException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		return jsonResponse.toString();
	}

	private void runService(String serviceType) {

		switch (serviceType) {
		case FieldsNames.GAME_STATUS:
			// TODO
			break;
		case FieldsNames.GAME_MAP:
			generateMapResponse();
			break;
		case FieldsNames.GAME_POSITIONS:
			// TODO
			break;
		}
	}

	private void generateMapResponse() {
		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.GAME);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_MAP);

			JSONArray items = new JSONArray();

			ArrayList<MapObject> mapObjects = mapGenerator.generate();

			for (MapObject o : mapObjects) {
				JSONObject jObject = new JSONObject();
				jObject.put(FieldsNames.GAME_OBJECT, o.object);
				jObject.put(FieldsNames.GAME_TEXTURE, o.texture);
				jObject.put(FieldsNames.GAME_POSITION, o.position);
				jObject.put(FieldsNames.GAME_SIZE, o.size);
				items.put(jObject);
			}

			jsonResponse.put(FieldsNames.GAME_WIDTH, mapGenerator.getWidth());
			jsonResponse.put(FieldsNames.GAME_HEIGHT, mapGenerator.getHeight());
			jsonResponse.put(FieldsNames.GAME_ITEMS, items);

		} catch (JSONException e) {
		}
	}

	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		mapGenerator = new SimpleMapGenerator();
	}

}
