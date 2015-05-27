package game.map;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fields_names.FieldsNames;
import game.model.PlayerStatus;

/**
 * Adapt a server map to a client map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class MapJSONizer {

	private static final int PHASE = -1;

	private MapConstructor mapConstructor;

	public MapJSONizer(MapConstructor mapConstructor) {
		this.mapConstructor = mapConstructor;
	}

	/**
	 * @return all the adapted items
	 */
	public JSONArray getAdaptedItems() {

		JSONArray adaptedItems = new JSONArray();

		try {

			ArrayList<MapObject> items = mapConstructor.getItems();

			for (MapObject i : items) {

				JSONObject adapted = new JSONObject();

				adapted.put(FieldsNames.GAME_OBJECT, i.getObjectName());
				adapted.put(FieldsNames.GAME_TEXTURE, i.getTextureName());

				String position = new MapDimension(i.getPosition().getWidth() + PHASE, i
						.getPosition().getHeight(), i.getPosition().getLength() + PHASE).toString();
				adapted.put(FieldsNames.GAME_POSITION, position);

				String size = new MapDimension(i.getSize().getWidth(), i.getSize().getHeight(), i
						.getSize().getLength()).toString();
				adapted.put(FieldsNames.GAME_SIZE, size);

				adaptedItems.put(adapted);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return adaptedItems;

	}

	/**
	 * @return all the spawn points
	 */
	public LinkedList<PlayerStatus> getAdaptedSpawnPoints() {

		LinkedList<PlayerStatus> spawnPoints = mapConstructor.getSpawnPoints();

		if (PHASE != 0)
			for (int i = 0; i < spawnPoints.size(); i++) {

				MapDimension phasePosition = new MapDimension(spawnPoints.get(i).getxPosition()
						+ PHASE, spawnPoints.get(i).getyPosition(), spawnPoints.get(i)
						.getzPosition() + PHASE);

				MapDimension phaseDirection = new MapDimension(spawnPoints.get(i).getxDirection()
						+ PHASE, spawnPoints.get(i).getyDirection(), spawnPoints.get(i)
						.getzDirection() + PHASE);

				spawnPoints.set(i, new PlayerStatus(phasePosition, phaseDirection));
			}

		return spawnPoints;
	}

	/**
	 * @return all the adapted win points
	 */
	public ArrayList<MapDimension> getAdaptedWinPoint() {

		ArrayList<MapDimension> winPoints = mapConstructor.getWinPoints();

		if (PHASE != 0)
			for (int i = 0; i < winPoints.size(); i++) {
				winPoints.set(i, new MapDimension(winPoints.get(i).getWidth() + PHASE, winPoints
						.get(i).getHeight(), winPoints.get(i).getLength() + PHASE));
			}

		if (winPoints.isEmpty())
			winPoints.add(new MapDimension(0, 0, 0));

		return winPoints;
	}

	public String getAdaptedWidth() {
		return new Double(mapConstructor.getMapSize().getWidth()).toString();
	}

	public String getAdaptedHeight() {
		return new Double(mapConstructor.getMapSize().getHeight()).toString();
	}

}
