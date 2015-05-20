package game.map.utils;

import game.map.MapDimension;

import java.util.Random;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class MapPosition {

	private static double OBJECT_FLOOR = -1;
	private static double PLAYER_FLOOR = 0.5;

	/**
	 * It generates a random position in the map area.
	 * 
	 * @param mapSize
	 *            the size of the map
	 * 
	 * @return a random position inside the map
	 */
	public static MapDimension getRandomPosition(MapDimension mapSize) {

		Random random = new Random();

		return new MapDimension((-1 * mapSize.getWidth() / 2)
				+ (mapSize.getWidth() / 2 - (-1 * mapSize.getWidth() / 2)) * random.nextDouble(),
				OBJECT_FLOOR, (-1 * mapSize.getLength() / 2)
						+ (mapSize.getLength() / 2 - (-1 * mapSize.getLength() / 2))
						* random.nextDouble());

	}

	/**
	 * It generates a random player position in the map area.
	 * 
	 * @param mapSize
	 *            the size of the map
	 * 
	 * @return a random player position inside the map
	 */
	public static MapDimension getRandomSpawnPoint(MapDimension mapSize) {

		MapDimension position = getRandomPosition(mapSize);

		return new MapDimension(position.getWidth(), PLAYER_FLOOR, position.getLength());

	}

}
