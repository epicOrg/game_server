package game.map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * MapJSONizer can be used to create a map in JSON format, starting from single
 * mapObjects (one, more, or none) and the size of the map.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class MapJSONizer {

	private JSONObject jsonMap;
	private JSONArray mapObjects;
	private JSONArray spawnPoints;
	private JSONArray winPoints;
	private int numObjects = 0;

	public MapJSONizer() {
		jsonMap = new JSONObject();
		mapObjects = new JSONArray();
		spawnPoints = new JSONArray();
		winPoints = new JSONArray();
	}

	public void setMapSize(Dimension dimension) {

		if (jsonMap.has(FieldsNames.GAME_SIZE))
			jsonMap.remove(FieldsNames.GAME_SIZE);

		JSONObject jsonMapSize = new JSONObject();

		try {

			jsonMapSize.put(FieldsNames.GAME_WIDTH, dimension.getWidth());
			jsonMapSize.put(FieldsNames.GAME_LENGTH, dimension.getLength());
			jsonMapSize.put(FieldsNames.GAME_HEIGHT, dimension.getHeight());

			jsonMap.put(FieldsNames.GAME_SIZE, jsonMapSize);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void addMapObject(MapObject mapObject) {

		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put(FieldsNames.GAME_TEXTURE, mapObject.getTextureName());
			jsonObject.put(FieldsNames.GAME_OBJECT, mapObject.getObjectName());

			JSONObject jsonPosition = new JSONObject();
			jsonPosition.put(FieldsNames.GAME_WIDTH, mapObject.getPosition().getWidth());
			jsonPosition.put(FieldsNames.GAME_LENGTH, mapObject.getPosition().getLength());
			jsonPosition.put(FieldsNames.GAME_HEIGHT, mapObject.getPosition().getHeight());

			JSONObject jsonSize = new JSONObject();
			jsonSize.put(FieldsNames.GAME_WIDTH, mapObject.getSize().getWidth());
			jsonSize.put(FieldsNames.GAME_LENGTH, mapObject.getSize().getLength());
			jsonSize.put(FieldsNames.GAME_HEIGHT, mapObject.getSize().getHeight());

			jsonObject.put(FieldsNames.GAME_POSITION, jsonPosition);
			jsonObject.put(FieldsNames.GAME_SIZE, jsonSize);

			mapObjects.put(jsonObject);
			numObjects++;

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void addMapObjects(MapObject... mapObjects) {
		for (MapObject mapObject : mapObjects) {
			addMapObject(mapObject);
		}
	}

	public void addSpawnPoint(Dimension position, Dimension direction) {

		JSONObject jsonObject = new JSONObject();

		try {

			JSONObject jsonPosition = new JSONObject();
			jsonPosition.put(FieldsNames.GAME_WIDTH, position.getWidth());
			jsonPosition.put(FieldsNames.GAME_LENGTH, position.getLength());
			jsonPosition.put(FieldsNames.GAME_HEIGHT, position.getHeight());

			JSONObject jsonDirection = new JSONObject();
			jsonDirection.put(FieldsNames.GAME_WIDTH, direction.getWidth());
			jsonDirection.put(FieldsNames.GAME_LENGTH, direction.getLength());
			jsonDirection.put(FieldsNames.GAME_HEIGHT, direction.getHeight());

			jsonObject.put(FieldsNames.GAME_POSITION, jsonPosition);
			jsonObject.put(FieldsNames.GAME_DIRECTION, jsonDirection);

			spawnPoints.put(jsonObject);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addWinPoint(MapObject mapObject) {

		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put(FieldsNames.GAME_TEXTURE, mapObject.getTextureName());
			jsonObject.put(FieldsNames.GAME_OBJECT, mapObject.getObjectName());

			JSONObject jsonPosition = new JSONObject();
			jsonPosition.put(FieldsNames.GAME_WIDTH, mapObject.getPosition().getWidth());
			jsonPosition.put(FieldsNames.GAME_LENGTH, mapObject.getPosition().getLength());
			jsonPosition.put(FieldsNames.GAME_HEIGHT, mapObject.getPosition().getHeight());

			JSONObject jsonSize = new JSONObject();
			jsonSize.put(FieldsNames.GAME_WIDTH, mapObject.getSize().getWidth());
			jsonSize.put(FieldsNames.GAME_LENGTH, mapObject.getSize().getLength());
			jsonSize.put(FieldsNames.GAME_HEIGHT, mapObject.getSize().getHeight());

			jsonObject.put(FieldsNames.GAME_POSITION, jsonPosition);
			jsonObject.put(FieldsNames.GAME_SIZE, jsonSize);

			winPoints.put(jsonObject);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void JSONizeMap() {

		try {

			if (jsonMap.has(FieldsNames.GAME_ITEMS))
				return;

			jsonMap.put(FieldsNames.GAME_ITEMS, mapObjects);
			jsonMap.put(FieldsNames.GAME_STATUS, spawnPoints);
			jsonMap.put(FieldsNames.GAME_WIN, winPoints);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getJSONMap() {
		JSONizeMap();
		return jsonMap;
	}

	public int getNumObjects() {
		return numObjects;
	}

}
