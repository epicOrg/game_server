package game.map.utils;

import game.map.MapDimension;

/**
 * Random positions generation.
 * 
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

		double maxWidth = mapSize.getWidth() / 2;
		double minWidth = -1 * maxWidth;

		double maxLength = mapSize.getLength() / 2;
		double minLength = -1 * maxLength;

		return new MapDimension(MapRandom.getRandomDouble(minWidth, maxWidth), OBJECT_FLOOR,
				MapRandom.getRandomDouble(minLength, maxLength));

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
