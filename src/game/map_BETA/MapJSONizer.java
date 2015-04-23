package game.map_BETA;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/23
 */

public class MapJSONizer {

	JSONObject jsonMap;

	public MapJSONizer() {
		jsonMap = new JSONObject();
	}

	public void setMapSize(Dimension dimension) {

		if (jsonMap.has(FieldsNames.GAME_SIZE))
			jsonMap.remove(FieldsNames.GAME_SIZE);

		JSONObject jsonMapSize = new JSONObject();

		try {

			jsonMapSize.put(FieldsNames.GAME_WIDTH, dimension.getWidth());
			jsonMapSize.put(FieldsNames.GAME_LENGTH, dimension.getLength());
			jsonMapSize.put(FieldsNames.GAME_HEIGHT, dimension.getHeigh());

			jsonMap.put(FieldsNames.GAME_SIZE, jsonMapSize);

		} catch (JSONException e) {
		}

	}

	public void addMapObject(MapObject mapObject) {

		JSONObject jsonObject = new JSONObject();

		try {

			// jsonObject.put(FieldsNames.GAME_OBJECT,
			// mapObject.getObjectName());
			jsonObject
					.put(FieldsNames.GAME_TEXTURE, mapObject.getTextureName());
			jsonObject.put(FieldsNames.GAME_OBJECT, mapObject.getObjectName());

			JSONObject jsonPosition = new JSONObject();
			jsonPosition.put(FieldsNames.GAME_WIDTH, mapObject.getPosition()
					.getWidth());
			jsonPosition.put(FieldsNames.GAME_LENGTH, mapObject.getPosition()
					.getLength());
			jsonPosition.put(FieldsNames.GAME_HEIGHT, mapObject.getPosition()
					.getHeigh());

			JSONObject jsonSize = new JSONObject();
			jsonSize.put(FieldsNames.GAME_WIDTH, mapObject.getSize().getWidth());
			jsonSize.put(FieldsNames.GAME_LENGTH, mapObject.getSize()
					.getLength());
			jsonSize.put(FieldsNames.GAME_HEIGHT, mapObject.getSize()
					.getHeigh());

			jsonObject.put(FieldsNames.GAME_POSITION, jsonPosition);
			jsonObject.put(FieldsNames.GAME_SIZE, jsonSize);

			jsonMap.put(FieldsNames.GAME_OBJECT, mapObject.getObjectName());

		} catch (JSONException e) {
		}

	}

	public JSONObject getJsonMap() {
		return jsonMap;
	}

}
