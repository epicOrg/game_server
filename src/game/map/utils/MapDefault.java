package game.map.utils;

import game.map.Dimension;
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
	public static void constructBorders(MapConstructor mapJSONizer, Dimension mapSize,
			Double wallSize, String texture) {

		double mapWidth = mapSize.getWidth();

		Dimension position1 = new Dimension(0, -1, mapWidth);
		Dimension size1 = new Dimension(mapWidth * 2, 2, wallSize);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position1, size1));

		Dimension position2 = new Dimension(0, -1, mapWidth * -1);
		Dimension size2 = new Dimension(mapWidth * 2, 2, wallSize);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position2, size2));

		Dimension position3 = new Dimension(mapWidth, -1, 0);
		Dimension size3 = new Dimension(wallSize, 2, mapWidth * 2);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position3, size3));

		Dimension position4 = new Dimension(mapWidth * -1, -1, 0);
		Dimension size4 = new Dimension(wallSize, 2, mapWidth * 2);
		mapJSONizer.addMapObject(new MapObject(Item.WALL, texture, position4, size4));
	}

	/**
	 * It constructs the borders of the map with the default wall size.
	 */
	public static void constructBorders(MapConstructor mapJSONizer, Dimension mapSize, String texture) {
		constructBorders(mapJSONizer, mapSize, WALL_SIZE, texture);

	}

}
