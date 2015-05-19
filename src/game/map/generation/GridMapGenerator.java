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
	private static final int MAX_DOORS = 4;

	private MapJSONizer mapJSONizer;
	private Dimension mapSize;
	private int numberOfPlayers;

	private ArrayList<Dimension> spawnPoints;
	private ArrayList<Dimension> doors;
	private Dimension winPoint;

	private ArrayList<Dimension> wallsPosition;
	private ArrayList<Dimension> wallsSize;

	private boolean isNotVertical = true;

	public GridMapGenerator(Dimension mapSize, int numberOfPlayers) {
		super();
		this.mapSize = mapSize;
		mapJSONizer = new MapJSONizer();
		spawnPoints = new ArrayList<Dimension>();
		doors = new ArrayList<Dimension>();
		this.numberOfPlayers = numberOfPlayers;

		wallsPosition = new ArrayList<Dimension>();
		wallsSize = new ArrayList<Dimension>();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(mapSize);
		setBorders();

		generateSpawnPoints();
		generateWin();

		generateGrid();
		// generateVerticalWall();

		return mapJSONizer.getJSONMap();
	}

	private void setBorders() {

		String bordersTexture = Texture.HEDGE4;

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

		Dimension mapSizeWithTolerance = new Dimension(mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE
				/ 2, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE / 2);

		for (int i = 0; i < numberOfPlayers; i++) {
			Dimension tmp = MapUtils.getRandomSpawnPoint(mapSize);
			spawnPoints.add(tmp);
			mapJSONizer.addSpawnPoint(tmp, new Dimension(0, 0, 0));
			System.out.println("PLAYER: " + tmp);

		}

	}

	// private void generateSpawnPoints2() {
	//
	// Dimension mapSizeWithTolerance = new Dimension(
	// mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE, mapSize.getHeight(),
	// mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE);
	//
	// ArrayList<Dimension> points;
	// for (int i = 0; i < numberOfPlayers; i++) {
	//
	//
	//
	// Dimension tmp = MapUtils.getRandomSpawnPoint(mapSizeWithTolerance);
	// spawnPoints.add(tmp);
	// mapJSONizer.addSpawnPoint(tmp, new Dimension(0, 0, 0));
	// System.out.println("PLAYER: " + tmp);
	//
	//
	//
	// if (!MapUtils.isCircleOnSegment(tmp, PLAYER_SIZE, points.get(0),
	// points.get(1)))
	// break;
	//
	// for (int j = 0; j < wallsPosition.size(); j++) {
	// points = MapUtils.getWallPoints(wallsPosition.get(j), wallsSize.get(j));
	// }
	//
	//
	//
	//
	//
	// }

	private void generateWin() {

		do {
		
		Dimension mapSizeWithTolerance = new Dimension(
				mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE, mapSize.getHeight(),
				mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE);

		winPoint = MapUtils.getRandomPosition(mapSizeWithTolerance);

		System.out.println("Win: " + winPoint);
		
		} while(MapUtils.checkIfUsed(winPoint, spawnPoints, PLAYER_SIZE));

		mapJSONizer.addMapObject(new MapObject(Item.VASE, Texture.CERAMIC1, winPoint,
				new Dimension(0.5, 1, 0)));
		mapJSONizer.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1, winPoint,
				new Dimension(0.5, 1, 0)));

	}

	private void generateGrid() {

		double newLenght = -mapSize.getLength();

		int loop = (int) Math.round(mapSize.getLength() * 2 / GRID_TOLERANCE) + 1;

		for (int i = 0; i < loop; i++) {

			Dimension position = new Dimension(newLenght, -1, 0);

			Dimension size = new Dimension(WALL_SIZE, 2, mapSize.getWidth() * 2);

			wallsPosition.add(position);
			wallsSize.add(size);

			ArrayList<Dimension> points = MapUtils.getWallPoints(position, size);

			if (!MapUtils.isOneCircleAtLeastOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
					points.get(1))
					&& !MapUtils.isCircleOnSegment(winPoint, PLAYER_SIZE, points.get(0),
							points.get(1))) {

				ArrayList<Dimension> segments = generateDoors(position, size,
						MapUtils.getRandomInt(MIN_DOORS, MAX_DOORS));

				for (int j = 0; j < segments.size() - 1; j++) {

					if (!(i == 0 || i == loop - 1))
						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.HEDGE4, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newLenght += GRID_TOLERANCE;
		}

	}

	private ArrayList<Dimension> generateDoors(Dimension position, Dimension size, int num) {

		ArrayList<Dimension> walls = new ArrayList<Dimension>();

		ArrayList<Dimension> points;

		if (isNotVertical)
			points = MapUtils.getWallPointsOnLine(position, size);
		else
			points = MapUtils.getWallPointsOnWidth(position, size);

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

		else if (p1.getLength() == p2.getLength()) {

			double w1 = p1.getWidth();
			double w2 = p2.getWidth();

			double width = w1 < w2 ? w1 : w2;

			for (int i = 0; i < num + 1; i++) {

				width += (dividedSize / 2);
				walls.add(new Dimension(width, position.getHeight(), position.getLength()));
				width += (dividedSize / 2 + PLAYER_SIZE);
			}

			walls.add(new Dimension(dividedSize, size.getHeight(), size.getLength()));

		}

		return walls;
	}

	private void generateVerticalWall2() {

		isNotVertical = false;

		double newWidth = -mapSize.getWidth();

		int loop = (int) (Math.round(mapSize.getWidth() * 2 / GRID_TOLERANCE) + 1) / 2;

		for (int i = 0; i < loop; i++) {

			Dimension position = new Dimension(0, -1, newWidth);

			Dimension size = new Dimension(mapSize.getLength() * 2, 2, WALL_SIZE);

			ArrayList<Dimension> points = MapUtils.getWallPoints(position, size);

			if (!MapUtils.isOneCircleAtLeastOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
					points.get(1))
					&& !MapUtils.isOneCircleAtLeastOnSegment(doors, PLAYER_SIZE, points.get(0),
							points.get(1))) {

				ArrayList<Dimension> segments = generateDoors(position, size,
						MapUtils.getRandomInt(MAX_DOORS - 1, MAX_DOORS + 1));

				for (int j = 0; j < segments.size() - 1; j++) {

					if (i != 0)
						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newWidth += GRID_TOLERANCE * 2;

		}

	}

	private void generateVerticalWall() {

		isNotVertical = false;

		double newWidth = -mapSize.getWidth();

		int loop = (int) (Math.round(mapSize.getWidth() * 2 / GRID_TOLERANCE) + 1) / 2;

		for (int i = 0; i < loop; i++) {

			Dimension position = new Dimension(0, -1, newWidth);

			Dimension size = new Dimension(mapSize.getLength() * 2, 2, WALL_SIZE);

			ArrayList<Dimension> points = MapUtils.getWallPoints(position, size);

			ArrayList<Dimension> segments = generateDoors(position, size,
					MapUtils.getRandomInt(MAX_DOORS - 1, MAX_DOORS + 1));

			for (int j = 0; j < segments.size() - 1; j++) {

				if (!MapUtils.isOneCircleAtLeastOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
						points.get(1))
						&& !MapUtils.isOneCircleAtLeastOnSegment(doors, PLAYER_SIZE, points.get(0),
								points.get(1))) {

					if (i != 0)
						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newWidth += GRID_TOLERANCE * 2;

		}

	}

}
