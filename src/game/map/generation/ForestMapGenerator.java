package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapJSONizer;
import game.map.MapObject;
import game.map.MapUtils;
import game.map.Texture;

import java.util.ArrayList;

import org.json.JSONObject;

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

	private MapJSONizer mapJSONizer;

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
		mapJSONizer = new MapJSONizer();
		positions = new ArrayList<Dimension>(numTrees);
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(mapSize);
		setBorders();
		setSpawnPoint();

		for (int i = 0; i < numTrees; i++) {

			setRandomSize();
			diameter = size.getWidth() * 2 + DISTANCE;

			if (i == 0) {

				double width = MapUtils.getRandomDouble(-mapSize.getWidth() + diameter,
						mapSize.getWidth() - diameter);

				double length = MapUtils.getRandomDouble(-mapSize.getLength() + diameter,
						mapSize.getLength() - diameter);

				position = new Dimension(width, -1, length);
				positions.add(position);
			}

			else {
				setRandomPosition();
			}

			MapObject trunk = new MapObject(Item.OBSTACLE, Texture.WOOD1, position, size);
			mapJSONizer.addMapObject(trunk);

			// MapObject foliage = new MapObject(Item.WALL, Texture.HEDGE, new
			// Dimension(
			// position.getWidth(), size.getHeigh(), position.getLength()), new
			// Dimension(
			// diameter * 2, size.getHeigh() / 3, diameter * 2));
			// mapJSONizer.addMapObject(foliage);

		}

		return mapJSONizer.getJSONMap();
	}

	private void setRandomPosition() {

		Dimension actualPosition = position;

		double width = 0, length = 0;

		while (MapUtils.checkIfUsed(actualPosition, positions, DISTANCE)) {

			width = MapUtils.getRandomDouble(-mapSize.getWidth() + diameter, mapSize.getWidth()
					- diameter);

			length = MapUtils.getRandomDouble(-mapSize.getLength() + diameter, mapSize.getLength()
					- diameter);

			actualPosition = new Dimension(width, -1, length);
		}

		position = new Dimension(width, -1, length);
		positions.add(position);

	}

	private void setRandomSize() {
		size = new Dimension(MapUtils.getRandomDouble(MIN_RAY, MAX_RAY), MapUtils.getRandomDouble(
				MIN_HEIGHT, MAX_HEIGHT), 0);
	}

	private void setBorders() {

		String bordersTexture = Texture.WALL2;

		double mapWidth = mapSize.getWidth();

		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth), new Dimension(mapWidth * 2, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth * -1), new Dimension(mapWidth * 2, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth,
				-1, 0), new Dimension(2, 2, mapWidth * 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth
				* -1, -1, 0), new Dimension(2, 2, mapWidth * 2)));
	}

	private void setSpawnPoint() {
		positions.add(new Dimension(-7, 0.5, 5));
	}

}
