package game.map;

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
		// System.out.println("Map Items: " + adaptedItems);

		return adaptedItems;

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
