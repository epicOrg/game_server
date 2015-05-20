package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapDefault;
import game.map.utils.MapGeometric;
import game.map.utils.MapRandom;

import java.util.ArrayList;

/**
 * Generate a map with N trees, like a forest.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class ForestMapGenerator implements MapGenerator {

	private static final double MIN_RAY = 0.1;
	private static final double MAX_RAY = 1;

	private static final double MIN_HEIGHT = 1;
	private static final double MAX_HEIGHT = 6;

	private static final double DISTANCE = 3;

	private MapConstructor mapConstructor;

	private Dimension mapSize;
	private int numTrees;

	private ArrayList<Dimension> positions;

	private Dimension position;
	private Dimension size;
	private double diameter;

	public ForestMapGenerator(Dimension mapSize, int numTrees) {
		super();
		this.mapSize = mapSize;
		this.numTrees = numTrees;
		mapConstructor = new MapConstructor();
		positions = new ArrayList<Dimension>(numTrees);
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.HEDGE4);
		setSpawnPoint();

		for (int i = 0; i < numTrees; i++) {

			setRandomSize();
			diameter = size.getWidth() * 2 + DISTANCE;

			if (i == 0) {

				double width = MapRandom.getRandomDouble(-mapSize.getWidth() + diameter,
						mapSize.getWidth() - diameter);

				double length = MapRandom.getRandomDouble(-mapSize.getLength() + diameter,
						mapSize.getLength() - diameter);

				position = new Dimension(width, -1, length);
				positions.add(position);
			}

			else {
				setRandomPosition();
			}

			MapObject trunk = new MapObject(Item.OBSTACLE, Texture.WOOD1, position, size);
			mapConstructor.addMapObject(trunk);

			// MapObject foliage = new MapObject(Item.WALL, Texture.HEDGE, new
			// Dimension(
			// position.getWidth(), size.getHeigh(), position.getLength()), new
			// Dimension(
			// diameter * 2, size.getHeigh() / 3, diameter * 2));
			// mapJSONizer.addMapObject(foliage);

		}

		return mapConstructor;
	}

	private void setRandomPosition() {

		Dimension actualPosition = position;

		double width = 0, length = 0;

		while (MapGeometric.checkIfUsed(actualPosition, positions, DISTANCE)) {

			width = MapRandom.getRandomDouble(-mapSize.getWidth() + diameter, mapSize.getWidth()
					- diameter);

			length = MapRandom.getRandomDouble(-mapSize.getLength() + diameter, mapSize.getLength()
					- diameter);

			actualPosition = new Dimension(width, -1, length);
		}

		position = new Dimension(width, -1, length);
		positions.add(position);

	}

	private void setRandomSize() {
		size = new Dimension(MapRandom.getRandomDouble(MIN_RAY, MAX_RAY), MapRandom.getRandomDouble(
				MIN_HEIGHT, MAX_HEIGHT), 0);
	}

	private void setSpawnPoint() {
		positions.add(new Dimension(-7, 0.5, 5));
	}

}
