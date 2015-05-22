package game.map.generation;

import game.map.MapDimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapConst;
import game.map.utils.MapDefault;
import game.map.utils.MapGeometric;
import game.map.utils.MapPosition;
import game.map.utils.MapRandom;
import game.model.PlayerStatus;

import java.util.ArrayList;

/**
 * Generate a map with N trees, like a forest.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class ForestMapGenerator implements MapGenerator {

	/*
	 * Minimum and maximum radius of the trunk.
	 */
	private static final double MIN_RADIUS = 0.4;
	private static final double MAX_RADIUS = 0.4;

	/*
	 * Minimum and maximum height of the trunk (not of the tree). To get the
	 * total height of the tree (called generally HEIGHT) use this formula:
	 * TotalHeight = FOLIAGE_HEIGHT_FACTOR * HEIGHT / 2.
	 */
	private static final double MIN_HEIGHT = 2;
	private static final double MAX_HEIGHT = 2;

	/*
	 * Minimum distance between objects (trees, spawn points, win points...).
	 */
	private static final double DISTANCE = MapConst.PLAYER_SIZE * 2;

	/*
	 * Tolerance of the center of the foliage compared to the trunk.
	 */
	private static final double FOLIAGE_TOLERANCE = -0.1;

	/*
	 * Increase/decrease this value to increase/decrease foliage height.
	 */
	private static final double FOLIAGE_HEIGHT_FACTOR = 4;

	private MapConstructor mapConstructor;

	private MapDimension mapSize;
	private int numberOfPlayers;
	private int numberOfTrees;

	private ArrayList<MapDimension> positions;

	private MapDimension position;
	private MapDimension size;
	private double diameter;

	public ForestMapGenerator(MapDimension mapSize, int numberOfTrees, int numberOfPlayers) {

		this.mapSize = mapSize;
		this.numberOfTrees = numberOfTrees;
		this.numberOfPlayers = numberOfPlayers;

		mapConstructor = new MapConstructor();
		positions = new ArrayList<MapDimension>(numberOfTrees + numberOfPlayers);
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.HEDGE3);

		generateTrees();

		generateSpawnPoints();
		generateWin();

		return mapConstructor;
	}

	/*
	 * It generate the trees. A tree is made of a column with a woody texture
	 * (that form the trunk of the tree) and a vase with a leaves texture (for
	 * the foliage). The radius and the height of the tree are random (the
	 * random value is generated between min and max, see the constants at the
	 * beginning of this class: MIN_RADIUS and MAX_RADIUS for the radius of the
	 * trunk, MIN_HEIGHT and MIN_HEIGHT for the height of the trunk).
	 */
	private void generateTrees() {

		for (int i = 0; i < numberOfTrees; i++) {

			setRandomSize();
			diameter = size.getWidth() * 2 + DISTANCE;

			if (i == 0) {

				double width = MapRandom.getRandomDouble(-mapSize.getWidth() + diameter,
						mapSize.getWidth() - diameter);

				double length = MapRandom.getRandomDouble(-mapSize.getLength() + diameter,
						mapSize.getLength() - diameter);

				position = new MapDimension(width, -1, length);
				positions.add(position);
			}

			else {
				setRandomPosition();
				positions.add(position);
			}

			MapObject trunk = new MapObject(Item.OBSTACLE, Texture.WOOD1, position, size);
			mapConstructor.addMapObject(trunk);

			double foliageHeight = size.getHeight() / 2;

			MapObject foliage = new MapObject(Item.VASE, Texture.HEDGE4, new MapDimension(
					position.getWidth(), size.getHeight() - foliageHeight - 0.1,
					position.getLength() + FOLIAGE_TOLERANCE), new MapDimension(diameter / 4,
					foliageHeight * FOLIAGE_HEIGHT_FACTOR, 0));
			mapConstructor.addMapObject(foliage);

		}

	}

	/*
	 * Generate a random position for every tree.
	 */
	private void setRandomPosition() {

		MapDimension actualPosition = position;

		double width = 0, length = 0;

		while (MapGeometric.checkIfUsed(actualPosition, positions, DISTANCE)) {

			width = MapRandom.getRandomDouble(-mapSize.getWidth() + diameter, mapSize.getWidth()
					- diameter);

			length = MapRandom.getRandomDouble(-mapSize.getLength() + diameter, mapSize.getLength()
					- diameter);

			actualPosition = new MapDimension(width, -1, length);
		}

		position = new MapDimension(width, -1, length);

	}

	/*
	 * Generate a random size (radius and height of the trunk) for every tree.
	 */
	private void setRandomSize() {
		size = new MapDimension(MapRandom.getRandomDouble(MIN_RADIUS, MAX_RADIUS),
				MapRandom.getRandomDouble(MIN_HEIGHT, MAX_HEIGHT), 0);
	}

	private void generateSpawnPoints() {

		MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth()
				- MapConst.PLAYER_SIZE / 2, mapSize.getHeight(), mapSize.getWidth()
				- MapConst.PLAYER_SIZE / 2);

		for (int i = 0; i < numberOfPlayers; i++) {

			MapDimension tmp;

			do {

				tmp = MapPosition.getRandomSpawnPoint(mapSizeWithTolerance);

			} while (MapGeometric.checkIfUsed(tmp, positions, DISTANCE));

			mapConstructor.addSpawnPoint(new PlayerStatus(tmp, tmp));
			positions.add(tmp);
		}

	}

	private void generateWin() {

		MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth()
				- MapConst.PLAYER_SIZE / 2, mapSize.getHeight(), mapSize.getWidth()
				- MapConst.PLAYER_SIZE / 2);

		MapDimension tmp;

		do {

			tmp = MapPosition.getRandomPosition(mapSizeWithTolerance);

		} while (MapGeometric.checkIfUsed(tmp, positions, DISTANCE));

		mapConstructor.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1, tmp,
				new MapDimension(0.5, 1, 0)));

	}

}
