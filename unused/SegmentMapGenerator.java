package game.map.generation;

import game.map.IMap;
import game.map.MapDimension;
import game.map.Map;
import game.map.MapObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * It generate a random map starting from previously loaded segments.
 * 
 * @author Noris
 * @date 2015/04/24
 */

public class SegmentMapGenerator implements MapGenerator {

	private ArrayList<ArrayList<MapObject>> segments;
	private IMap mapConstructor;

	public SegmentMapGenerator() {
		super();
		this.segments = new ArrayList<ArrayList<MapObject>>();
		mapConstructor = new Map();
	}

	@Override
	public IMap generateMap() {

		for (ArrayList<MapObject> segment : segments) {
			int random = new Random().nextInt(segment.size());
			mapConstructor.addMapObject(segment.get(random));
		}

		return mapConstructor;
	}

	public void setMapSize(MapDimension size) {
		mapConstructor.setMapSize(size);
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
