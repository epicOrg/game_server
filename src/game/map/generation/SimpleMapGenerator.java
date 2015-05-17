package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapJSONizer;
import game.map.MapObject;
import game.map.Texture;

import org.json.JSONObject;

/**
 * It generate an example map.
 *
 * @author Noris
 * @author Torlaschi
 * @date 2015/04/23
 */

public class SimpleMapGenerator implements MapGenerator {

	private MapJSONizer mapJSONizer;

	public SimpleMapGenerator() {
		mapJSONizer = new MapJSONizer();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(new Dimension(20, 20, 20));

		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(-8.25, -1, -1.75), new Dimension(1.5, 2.5, 14.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(-5.75, -1, -3.25), new Dimension(3.5, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(0, -1, -8.25), new Dimension(15, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(8.25, -1, -1.75), new Dimension(1.5, 2.5, 14.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(5, -1, -3.25), new Dimension(5, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.OBSTACLE, Texture.WOOD1, new Dimension(1.7, -1, -3.25), new Dimension(0.3, 2.5, 0)));
		mapJSONizer.addMapObject(new MapObject(Item.OBSTACLE, Texture.WOOD1, new Dimension(-1.9, -1, -3.25), new Dimension(0.3, 2.5, 0)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(4.75, -1, 4.75), new Dimension(5.5, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(-3.75, -1, 4.75), new Dimension(7.5, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new Dimension(0, -1, 1.25), new Dimension(10, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(0, -1, 20), new Dimension(40, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(0, -1, -20), new Dimension(40, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(20, -1, 0), new Dimension(2, 2, 40)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.HEDGE3, new Dimension(-20, -1, 0), new Dimension(2, 2, 40)));
		mapJSONizer.addMapObject(new MapObject(Item.OBSTACLE, Texture.WALL2, new Dimension(0, -1, 18), new Dimension(0.3, 2.5, 0)));

		return mapJSONizer.getJSONMap();
	}

}
