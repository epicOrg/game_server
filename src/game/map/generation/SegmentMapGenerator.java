package game.map.generation;

import game.map.Dimension;
import game.map.MapJSONizer;
import game.map.MapObject;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

/**
 * It generate a random map starting from previously loaded segments.
 * 
 * @author Noris
 * @date 2015/04/24
 */

public class SegmentMapGenerator implements MapGenerator {

	private ArrayList<ArrayList<MapObject>> segments;
	private MapJSONizer mapJSONizer;

	public SegmentMapGenerator() {
		super();
		this.segments = new ArrayList<ArrayList<MapObject>>();
		mapJSONizer = new MapJSONizer();
	}

	@Override
	public JSONObject generateMap() {

		for (ArrayList<MapObject> segment : segments) {
			int random = new Random().nextInt(segment.size());
			mapJSONizer.addMapObject(segment.get(random));
		}

		return mapJSONizer.getJSONMap();
	}

	public void setMapSize(Dimension size) {
		mapJSONizer.setMapSize(size);
	}

	public void loadObject(MapObject mapObject, int segment) {
		segments.get(segment).add(mapObject);
	}

	public void loadObjectsList(ArrayList<MapObject> mapObjects, int segment) {
		for (MapObject mapObject : mapObjects) {
			segments.get(segment).add(mapObject);
		}

	}

}
