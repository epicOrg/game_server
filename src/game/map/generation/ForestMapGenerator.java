package game.map.generation;

import game.PlayerStatus;
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

import java.util.ArrayList;

/**
 * Generate a map with N trees, like a forest.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class ForestMapGenerator implements MapGenerator {

	private static final double MIN_RAY = 0.4;
	private static final double MAX_RAY = 0.4;

	private static final double MIN_HEIGHT = 2;
	private static final double MAX_HEIGHT = 2;

	private static final double DISTANCE = MapConst.PLAYER_SIZE * 2;

	private static final double FOLIAGE_TOLERANCE = -0.1;

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
					foliageHeight * 4, 0));
			mapConstructor.addMapObject(foliage);

		}

		generateSpawnPoints();
		generateWin();

		return mapConstructor;
	}

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

	private void setRandomSize() {
		size = new MapDimension(MapRandom.getRandomDouble(MIN_RAY, MAX_RAY),
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
