package game.map.utils;

import game.map.MapDimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class MapDefault {

	private static final double WALL_SIZE = 0.2;

	/**
	 * It constructs the borders of the map.
	 */
	public static void constructBorders(MapConstructor mapJSONizer, MapDimension mapSize,
			Double wallSize, String texture) {

		double mapWidth = mapSize.getWidth();

		MapDimension position1 = new MapDimension(0, -1, mapWidth);
		MapDimension size1 = new MapDimension(mapWidth * 2, 2, wallSize);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position1, size1));

		MapDimension position2 = new MapDimension(0, -1, mapWidth * -1);
		MapDimension size2 = new MapDimension(mapWidth * 2, 2, wallSize);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position2, size2));

		MapDimension position3 = new MapDimension(mapWidth, -1, 0);
		MapDimension size3 = new MapDimension(wallSize, 2, mapWidth * 2);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position3, size3));

		MapDimension position4 = new MapDimension(mapWidth * -1, -1, 0);
		MapDimension size4 = new MapDimension(wallSize, 2, mapWidth * 2);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position4, size4));
	}

	/**
	 * It constructs the borders of the map with the default wall size.
	 */
	public static void constructBorders(MapConstructor mapJSONizer, MapDimension mapSize, String texture) {
		constructBorders(mapJSONizer, mapSize, WALL_SIZE, texture);

	}

}
