package game;

import game.map.Dimension;
import game.map.MapJSONizer;
import game.map.generation.MapGenerator;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class RoomMapSelector {

	private MapGenerator mapGenerator;
	private JSONObject map;

	private LinkedList<PlayerStatus> spawnPoints;
	private Dimension winPoint;

	public RoomMapSelector(MapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
		generateMap();
	}

	private void generateMap() {

		MapJSONizer mapJSONizer = new MapJSONizer(mapGenerator.generateMap());

		try {

			JSONObject jsonMap = new JSONObject();
			jsonMap.put(FieldsNames.GAME_WIDTH, mapJSONizer.getAdaptedWidth());
			jsonMap.put(FieldsNames.GAME_HEIGHT, mapJSONizer.getAdaptedHeight());
			jsonMap.put(FieldsNames.GAME_ITEMS, mapJSONizer.getAdaptedItems());

			map = jsonMap;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		spawnPoints = mapJSONizer.getAdaptedSpawnPoints();

		winPoint = mapJSONizer.getAdaptedWinPoint();

	}

	public JSONObject getMap() {
		return map;
	}

	public PlayerStatus getSpawnPoint() {

		if (spawnPoints.size() == 1)
			return spawnPoints.getFirst();

		return spawnPoints.remove();

	}

	public Dimension getWinPoint() {
		return winPoint;
	}

}
