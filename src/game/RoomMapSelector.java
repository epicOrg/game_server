package game;

import fields_names.GameFields;
import game.map.MapDimension;
import game.map.MapJSONizer;
import game.map.generation.IMapGenerator;
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

	private IMapGenerator mapGenerator;
	private JSONObject map;

	private LinkedList<PlayerStatus> spawnPoints;
	private ArrayList<MapDimension> winPoints;

	public RoomMapSelector(IMapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
		generateMap();
	}

	private void generateMap() {

//		MapJSONizer mapJSONizer = new MapJSONizer(mapGenerator.generateMap());
//
//		try {
//
//			JSONObject jsonMap = new JSONObject();
//			jsonMap.put(GameFields.GAME_WIDTH.toString(), mapJSONizer.getAdaptedWidth());
//			jsonMap.put(GameFields.GAME_HEIGHT.toString(), mapJSONizer.getAdaptedHeight());
//			jsonMap.put(GameFields.GAME_ITEMS.toString(), mapJSONizer.getAdaptedItems());
//
//			map = jsonMap;
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		spawnPoints = mapJSONizer.getAdaptedSpawnPoints();
//		winPoints = mapJSONizer.getAdaptedWinPoint();

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
