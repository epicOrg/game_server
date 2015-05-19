package game.map;

import game.PlayerStatus;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * Adapt a server string-format map to a client string-format map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class MapAdapter {

	private static final int PHASE = -1;

	private JSONObject jsonMap;
	private JSONObject mapSize;

	public MapAdapter(JSONObject jsonMap) {

		this.jsonMap = jsonMap;

		try {
			this.mapSize = jsonMap.getJSONObject(FieldsNames.GAME_SIZE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return all the adapted items
	 */
	public JSONArray getAdaptedItems() {

		JSONArray adaptedItems = new JSONArray();

		try {

			JSONArray originalItems = jsonMap.getJSONArray(FieldsNames.GAME_ITEMS);

			for (int i = 0; i < originalItems.length(); i++) {

				JSONObject original = originalItems.getJSONObject(i);
				JSONObject adapted = new JSONObject();

				adapted.put(FieldsNames.GAME_OBJECT, original.get(FieldsNames.GAME_OBJECT));
				adapted.put(FieldsNames.GAME_TEXTURE, original.get(FieldsNames.GAME_TEXTURE));

				JSONObject jsonPosition = original.getJSONObject(FieldsNames.GAME_POSITION);
				String position = new Dimension(jsonPosition.getDouble(FieldsNames.GAME_WIDTH)
						+ PHASE, jsonPosition.getDouble(FieldsNames.GAME_HEIGHT),
						jsonPosition.getDouble(FieldsNames.GAME_LENGTH) + PHASE).toString();
				adapted.put(FieldsNames.GAME_POSITION, position);

				JSONObject jsonSize = original.getJSONObject(FieldsNames.GAME_SIZE);
				String size = new Dimension(jsonSize.getDouble(FieldsNames.GAME_WIDTH),
						jsonSize.getDouble(FieldsNames.GAME_HEIGHT),
						jsonSize.getDouble(FieldsNames.GAME_LENGTH)).toString();
				adapted.put(FieldsNames.GAME_SIZE, size);

				adaptedItems.put(adapted);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// TODO DEBUG: map items
		// System.out.println("MAP ITEMS: " + adaptedItems);

		return adaptedItems;

	}

	/**
	 * @return all the spawn points
	 */
	public LinkedList<PlayerStatus> getAdaptedSpawnPoints() {

		LinkedList<PlayerStatus> spawnPoints = new LinkedList<PlayerStatus>();

		try {

			JSONArray originalSpawnPoints = jsonMap.getJSONArray(FieldsNames.GAME_STATUS);

			for (int i = 0; i < originalSpawnPoints.length(); i++) {

				JSONObject original = originalSpawnPoints.getJSONObject(i);

				JSONObject jsonPosition = original.getJSONObject(FieldsNames.GAME_POSITION);

				JSONObject jsonDirection = original.getJSONObject(FieldsNames.GAME_SIZE);

				PlayerStatus spawnPoint = new PlayerStatus(
						jsonPosition.getDouble(FieldsNames.GAME_WIDTH) + PHASE,
						jsonPosition.getDouble(FieldsNames.GAME_HEIGHT),
						jsonPosition.getDouble(FieldsNames.GAME_LENGTH) + PHASE,
						jsonDirection.getDouble(FieldsNames.GAME_WIDTH),
						jsonDirection.getDouble(FieldsNames.GAME_HEIGHT),
						jsonDirection.getDouble(FieldsNames.GAME_LENGTH));

				spawnPoints.add(spawnPoint);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return spawnPoints;

	}

	/**
	 * @return all the adapted items
	 */
	public Dimension getAdaptedWinPoint() {

		JSONArray adaptedWinPoints = new JSONArray();

		try {

			JSONArray originalWinPoints = jsonMap.getJSONArray(FieldsNames.GAME_WIN);

			// TODO

			for (int i = 0; i < originalWinPoints.length(); i++) {

				JSONObject original = originalWinPoints.getJSONObject(i);
				JSONObject adapted = new JSONObject();

				JSONObject jsonPosition = original.getJSONObject(FieldsNames.GAME_POSITION);

				return new Dimension(jsonPosition.getDouble(FieldsNames.GAME_WIDTH) + PHASE,
						jsonPosition.getDouble(FieldsNames.GAME_HEIGHT),
						jsonPosition.getDouble(FieldsNames.GAME_LENGTH) + PHASE);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	public String getAdaptedWidth() {
		try {
			return new Double(mapSize.getDouble(FieldsNames.GAME_WIDTH)).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getAdaptedHeight() {
		try {
			return new Double(mapSize.getDouble(FieldsNames.GAME_HEIGHT)).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}
