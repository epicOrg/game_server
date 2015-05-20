package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;

/**
 * It generate a test map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class TestMapGenerator implements MapGenerator {

	private MapConstructor mapConstructor = new MapConstructor();

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(new Dimension(10, 10, 10));

		MapObject o1 = new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(-10, -1, 0),
				new Dimension(0.2, 1, 20));

		MapObject o2 = new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(0, -1, 10),
				new Dimension(20, 1, 0.2));

		MapObject o3 = new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(0, -1, -10),
				new Dimension(20, 1, 0.2));

		MapObject o4 = new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(10, -1, 0),
				new Dimension(0.2, 1, 20));

		MapObject o5 = new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(-10, -1, 0),
				new Dimension(0.2, 1, 20));

		MapObject o6 = new MapObject(Item.WALL, Texture.WALL2, new Dimension(0, -1, 0),
				new Dimension(1, 5, 1));

		mapConstructor.addMapObjects(o1, o2, o3, o4, o5, o6);
		// mapJSONizer.addMapObjects(o1);

		return mapConstructor;
	}

}
