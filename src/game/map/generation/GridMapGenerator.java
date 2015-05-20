package game.map.generation;

import game.PlayerStatus;
import game.map.MapDimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapDefault;
import game.map.utils.MapGeometric;
import game.map.utils.MapPosition;
import game.map.utils.MapRandom;

import java.util.ArrayList;

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

	private MapConstructor mapConstructor;
	private MapDimension mapSize;
	private int numberOfPlayers;

	private ArrayList<MapDimension> spawnPoints;
	private ArrayList<MapDimension> doors;
	private MapDimension winPoint;

	private ArrayList<MapDimension> wallsPosition;
	private ArrayList<MapDimension> wallsSize;

	private boolean isNotVertical = true;

	public GridMapGenerator(MapDimension mapSize, int numberOfPlayers) {
		super();
		this.mapSize = mapSize;
		mapConstructor = new MapConstructor();
		spawnPoints = new ArrayList<MapDimension>();
		doors = new ArrayList<MapDimension>();
		this.numberOfPlayers = numberOfPlayers;

		wallsPosition = new ArrayList<MapDimension>();
		wallsSize = new ArrayList<MapDimension>();
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.WALL3);

		generateSpawnPoints();
		generateWin();

		generateGrid();
		// generateVerticalWall();

		return mapConstructor;
	}

	private void generateSpawnPoints() {

		MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE
				/ 2, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE - PLAYER_SIZE / 2);

		for (int i = 0; i < numberOfPlayers; i++) {
			MapDimension tmp = MapPosition.getRandomSpawnPoint(mapSize);
			spawnPoints.add(tmp);
			mapConstructor.addSpawnPoint(new PlayerStatus(tmp, tmp));
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

			MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() - WALL_SIZE
					- PLAYER_SIZE, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE
					- PLAYER_SIZE);

			winPoint = MapPosition.getRandomPosition(mapSizeWithTolerance);

			System.out.println("Win: " + winPoint);

		} while (MapGeometric.checkIfUsed(winPoint, spawnPoints, PLAYER_SIZE));

		mapConstructor.addMapObject(new MapObject(Item.VASE, Texture.CERAMIC1, winPoint,
				new MapDimension(0.5, 1, 0)));
		mapConstructor.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1, winPoint, new MapDimension(
				0.5, 1, 0)));

	}

	private void generateGrid() {

		double newLenght = -mapSize.getLength();

		int loop = (int) Math.round(mapSize.getLength() * 2 / GRID_TOLERANCE) + 1;

		for (int i = 0; i < loop; i++) {

			MapDimension position = new MapDimension(newLenght, -1, 0);

			MapDimension size = new MapDimension(WALL_SIZE, 2, mapSize.getWidth() * 2);

			wallsPosition.add(position);
			wallsSize.add(size);

			ArrayList<MapDimension> points = MapGeometric.getWallPoints(position, size);

			if (!MapGeometric.isCircleOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
					points.get(1))
					&& !MapGeometric.isCircleOnSegment(winPoint, PLAYER_SIZE, points.get(0),
							points.get(1))) {

				ArrayList<MapDimension> segments = generateDoors(position, size,
						MapRandom.getRandomInt(MIN_DOORS, MAX_DOORS));

				for (int j = 0; j < segments.size() - 1; j++) {

					if (!(i == 0 || i == loop - 1))
						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.HEDGE4, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newLenght += GRID_TOLERANCE;
		}

	}

	private ArrayList<MapDimension> generateDoors(MapDimension position, MapDimension size, int num) {

		ArrayList<MapDimension> walls = new ArrayList<MapDimension>();

		ArrayList<MapDimension> points;

		if (isNotVertical)
			points = MapGeometric.getWallPointsOnLength(position, size);
		else
			points = MapGeometric.getWallPointsOnWidth(position, size);

		MapDimension p1 = points.get(0);
		MapDimension p2 = points.get(1);

		double totalSize = MapGeometric.getDistance(p1, p2);
		double dividedSize = (totalSize - PLAYER_SIZE * num) / (num + 1);

		if (p1.getWidth() == p2.getWidth()) {

			double l1 = p1.getLength();
			double l2 = p2.getLength();

			double length = l1 < l2 ? l1 : l2;

			for (int i = 0; i < num + 1; i++) {

				length += (dividedSize / 2);
				walls.add(new MapDimension(position.getWidth(), position.getHeight(), length));
				length += (dividedSize / 2 + PLAYER_SIZE);

				doors.add(new MapDimension(position.getWidth(), position.getHeight(), length / 2));
			}

			walls.add(new MapDimension(size.getWidth(), size.getHeight(), dividedSize));

		}

		else if (p1.getLength() == p2.getLength()) {

			double w1 = p1.getWidth();
			double w2 = p2.getWidth();

			double width = w1 < w2 ? w1 : w2;

			for (int i = 0; i < num + 1; i++) {

				width += (dividedSize / 2);
				walls.add(new MapDimension(width, position.getHeight(), position.getLength()));
				width += (dividedSize / 2 + PLAYER_SIZE);
			}

			walls.add(new MapDimension(dividedSize, size.getHeight(), size.getLength()));

		}

		return walls;
	}

	private void generateVerticalWall2() {

		isNotVertical = false;

		double newWidth = -mapSize.getWidth();

		int loop = (int) (Math.round(mapSize.getWidth() * 2 / GRID_TOLERANCE) + 1) / 2;

		for (int i = 0; i < loop; i++) {

			MapDimension position = new MapDimension(0, -1, newWidth);

			MapDimension size = new MapDimension(mapSize.getLength() * 2, 2, WALL_SIZE);

			ArrayList<MapDimension> points = MapGeometric.getWallPoints(position, size);

			if (!MapGeometric.isCircleOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
					points.get(1))
					&& !MapGeometric.isCircleOnSegment(doors, PLAYER_SIZE, points.get(0),
							points.get(1))) {

				ArrayList<MapDimension> segments = generateDoors(position, size,
						MapRandom.getRandomInt(MAX_DOORS - 1, MAX_DOORS + 1));

				for (int j = 0; j < segments.size() - 1; j++) {

					if (i != 0)
						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, segments
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

			MapDimension position = new MapDimension(0, -1, newWidth);

			MapDimension size = new MapDimension(mapSize.getLength() * 2, 2, WALL_SIZE);

			ArrayList<MapDimension> points = MapGeometric.getWallPoints(position, size);

			ArrayList<MapDimension> segments = generateDoors(position, size,
					MapRandom.getRandomInt(MAX_DOORS - 1, MAX_DOORS + 1));

			for (int j = 0; j < segments.size() - 1; j++) {

				if (!MapGeometric.isCircleOnSegment(spawnPoints, PLAYER_SIZE, points.get(0),
						points.get(1))
						&& !MapGeometric.isCircleOnSegment(doors, PLAYER_SIZE, points.get(0),
								points.get(1))) {

					if (i != 0)
						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, segments
								.get(j), segments.get(segments.size() - 1)));
				}

			}

			newWidth += GRID_TOLERANCE * 2;

		}

	}

}
