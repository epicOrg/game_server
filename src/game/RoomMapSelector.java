package game;

import fields_name.FieldsNames;
import game.map.MapDimension;
import game.map.MapJSONizer;
import game.map.generation.MapGenerator;
import game.model.PlayerStatus;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class RoomMapSelector {

	private MapGenerator mapGenerator;
	private JSONObject map;

	private LinkedList<PlayerStatus> spawnPoints;
	private ArrayList<MapDimension> winPoints;

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
		winPoints = mapJSONizer.getAdaptedWinPoint();

	}

	public JSONObject getMap() {
		return map;
	}

	public PlayerStatus getSpawnPoint() {

		if (spawnPoints.size() == 1)
			return spawnPoints.getFirst();

		return spawnPoints.remove();

	}

	public ArrayList<MapDimension> getWinPoint() {
		return winPoints;
	}

}
