package game.map;

import java.util.ArrayList;

import messages.fields_names.GameFields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game.model.PlayerStatus;

/**
 * Adapt a server map to a client map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class MapJSONizer {

	private static final int PHASE = -1;

	private IMap map;
	private JSONObject jsonMap;
	private ArrayList<MapDimension> winPoints;

	public MapJSONizer(IMap map) {
		this.map = map;
	}

	public void generateMap() {

		try {

			JSONObject jsonMap = new JSONObject();
			jsonMap.put(GameFields.GAME_WIDTH.toString(), getAdaptedWidth());
			jsonMap.put(GameFields.GAME_HEIGHT.toString(), getAdaptedHeight());
			jsonMap.put(GameFields.GAME_ITEMS.toString(), getAdaptedItems());

			this.jsonMap = jsonMap;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		winPoints = getAdaptedWinPoint(map.getWinPoints());

	}

	public JSONObject getMap() {
		return jsonMap;
	}

	public ArrayList<MapDimension> getWinPoint() {
		return winPoints;
	}

	/**
	 * @return all the adapted items
	 */
	public JSONArray getAdaptedItems() {

		JSONArray adaptedItems = new JSONArray();

		try {

			ArrayList<MapObject> items = map.getItems();

			for (MapObject i : items) {

				JSONObject adapted = new JSONObject();

				adapted.put(GameFields.GAME_OBJECT.toString(), i.getObjectName());
				adapted.put(GameFields.GAME_TEXTURE.toString(), i.getTextureName());

				String position = new MapDimension(i.getPosition().getWidth() + PHASE, i
						.getPosition().getHeight(), i.getPosition().getLength() + PHASE).toString();
				adapted.put(GameFields.GAME_POSITION.toString(), position);

				String size = new MapDimension(i.getSize().getWidth(), i.getSize().getHeight(), i
						.getSize().getLength()).toString();
				adapted.put(GameFields.GAME_SIZE.toString(), size);

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
	public static PlayerStatus getAdaptedSpawnPoints(PlayerStatus spawnPoint) {

		PlayerStatus adaptedSpawnPoints = new PlayerStatus(spawnPoint.getxPosition(),
				spawnPoint.getyPosition(), spawnPoint.getzPosition(), spawnPoint.getxDirection(),
				spawnPoint.getyDirection(), spawnPoint.getzDirection());

		if (PHASE != 0) {

			MapDimension phasePosition = new MapDimension(spawnPoint.getxPosition() + PHASE,
					spawnPoint.getyPosition(), spawnPoint.getzPosition() + PHASE);

			MapDimension phaseDirection = new MapDimension(spawnPoint.getxDirection() + PHASE,
					spawnPoint.getyDirection(), spawnPoint.getzDirection() + PHASE);

			adaptedSpawnPoints = new PlayerStatus(phasePosition, phaseDirection);
		}

		return adaptedSpawnPoints;
	}

	/**
	 * @return all the adapted win points
	 */
	public static ArrayList<MapDimension> getAdaptedWinPoint(
			ArrayList<MapDimension> winPointsToAdapt) {

		ArrayList<MapDimension> winPoints = new ArrayList<>(winPointsToAdapt);

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
		return new Double(map.getMapSize().getWidth()).toString();
	}

	public String getAdaptedHeight() {
		return new Double(map.getMapSize().getHeight()).toString();
	}

}
