package game.map.generation;

import game.map.Dimension;
import game.map.MapConstructor;
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
	private MapConstructor mapConstructor;

	public SegmentMapGenerator() {
		super();
		this.segments = new ArrayList<ArrayList<MapObject>>();
		mapConstructor = new MapConstructor();
	}

	@Override
	public MapConstructor generateMap() {

		for (ArrayList<MapObject> segment : segments) {
			int random = new Random().nextInt(segment.size());
			mapConstructor.addMapObject(segment.get(random));
		}

		return mapConstructor;
	}

	public void setMapSize(Dimension size) {
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
