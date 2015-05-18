package game.map.generation;

import java.util.ArrayList;

import game.map.Dimension;
import game.map.Item;
import game.map.MapJSONizer;
import game.map.MapObject;
import game.map.MapUtils;
import game.map.Texture;

import org.json.JSONObject;

/**
 * Generate a maze by construct a default grid and by open random ports.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class GridMapGenerator implements MapGenerator {

	private static final double PLAYER_SIZE = 2;
	private static final double WALL_SIZE = 0.2;
	private static final double GRID_TOLERANCE = PLAYER_SIZE;

	private static final int MIN_DOORS = 1;
	private static final int MAX_DOORS = 3;

	private MapJSONizer mapJSONizer;
	private Dimension mapSize;
	private ArrayList<Dimension> spawnPoints;
	private ArrayList<Dimension> doors;
	private ArrayList<Dimension> walls;

	public GridMapGenerator(Dimension mapSize) {
		super();
		this.mapSize = mapSize;
		mapJSONizer = new MapJSONizer();
		spawnPoints = new ArrayList<Dimension>();
		doors = new ArrayList<Dimension>();
		walls = new ArrayList<Dimension>();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(mapSize);
		setBorders();
		generateSpawnPoints();

		generateGrid();

		return mapJSONizer.getJSONMap();
	}

	private void setBorders() {

		String bordersTexture = Texture.WALL2;

		double mapWidth = mapSize.getWidth();

		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth), new Dimension(mapWidth * 2, 2, WALL_SIZE)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth * -1), new Dimension(mapWidth * 2, 2, WALL_SIZE)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth,
				-1, 0), new Dimension(WALL_SIZE, 2, mapWidth * 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth
				* -1, -1, 0), new Dimension(WALL_SIZE, 2, mapWidth * 2)));
	}

	private void generateSpawnPoints() {
		// TODO add spawn points generation
		spawnPoints.add(new Dimension(5, 0.5, -7));
	}

	private void generateGrid() {

		double newLenght = -mapSize.getLength();

		int loop = (int) Math.round(mapSize.getLength() * 2 / GRID_TOLERANCE) + 1;

		for (int i = 0; i < loop; i++) {

			Dimension position = new Dimension(newLenght, -1, 0);

			Dimension size = new Dimension(WALL_SIZE, 2, mapSize.getWidth() * 2);

			ArrayList<Dimension> points = MapUtils.getWallPoints(position, size);

			if (!MapUtils.isOneCircleAtLeastOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
					points.get(1))) {

				ArrayList<Dimension> segments = generateDoors(position, size,
						MapUtils.getRandomInt(MIN_DOORS, MAX_DOORS));

				for (int j = 0; j < segments.size() - 1; j++) {

					if (!(i == 0 || i == loop - 1))
						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newLenght += GRID_TOLERANCE;
		}

	}

	private ArrayList<Dimension> generateDoors(Dimension position, Dimension size, int num) {

		ArrayList<Dimension> walls = new ArrayList<Dimension>();

		ArrayList<Dimension> points = MapUtils.getWallPointsOnLine(position, size);

		Dimension p1 = points.get(0);
		Dimension p2 = points.get(1);

		double totalSize = MapUtils.getPointsDistance2D(p1, p2);
		double dividedSize = (totalSize - PLAYER_SIZE * num) / (num + 1);

		if (p1.getWidth() == p2.getWidth()) {

			double l1 = p1.getLength();
			double l2 = p2.getLength();

			double length = l1 < l2 ? l1 : l2;

			for (int i = 0; i < num + 1; i++) {

				length += (dividedSize / 2);
				walls.add(new Dimension(position.getWidth(), position.getHeight(), length));
				length += (dividedSize / 2 + PLAYER_SIZE);

				doors.add(new Dimension(position.getWidth(), position.getHeight(), length / 2));
			}

			walls.add(new Dimension(size.getWidth(), size.getHeight(), dividedSize));

		}

		return walls;
	}

}
