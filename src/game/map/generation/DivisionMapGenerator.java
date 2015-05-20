package game.map.generation;

import game.map.MapDimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapGeometric;
import game.map.utils.MapRandom;

import java.util.ArrayList;

/**
 * It generates a maze using a division algorithm.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class DivisionMapGenerator implements MapGenerator {

	private MapDimension mapSize;
	private MapConstructor mapConstructor;

	private static final double PLAYER_SIZE = 2;
	private static final double CENTER_TOLERANCE = 3;
	private static final double WALL_TOLERANCE = PLAYER_SIZE;
	private static final double BORDER_TOLERANCE = PLAYER_SIZE * 2;

	private ArrayList<MapDimension> spawnPoints;

	private MapDimension availableSize;

	private MapDimension position;
	private MapDimension size;

	private MapDimension nextPosition;
	private MapDimension nextSize;

	private MapDimension verticalPosition;
	private MapDimension verticalSize;

	private double doorCenter;

	public DivisionMapGenerator(MapDimension mapSize) {
		super();
		this.mapSize = mapSize;
		mapConstructor = new MapConstructor();
		spawnPoints = new ArrayList<MapDimension>();
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		// MapDefault.constructBorders(mapJSONizer, mapSize, Texture.WALL3);
		generateSpawnPoints();

		availableSize = mapSize;

		ArrayList<MapDimension> points;

		do {

			generatePosition();
			generateSize();

			points = MapGeometric.getWallPoints(position, size);

		} while (MapGeometric.isCircleOnSegment(spawnPoints, WALL_TOLERANCE, points.get(0),
				points.get(1)));

		MapObject randomWall = new MapObject(Item.WALL, Texture.WALL3, position, size);
		mapConstructor.addMapObject(randomWall);

		do {

			generateNextSize();
			generateNextPosition();

			points = MapGeometric.getWallPoints(nextPosition, nextSize);

		} while (MapGeometric.isCircleOnSegment(spawnPoints, WALL_TOLERANCE, points.get(0),
				points.get(1)));

		MapObject nextRandomWall = new MapObject(Item.WALL, Texture.HEDGE3, nextPosition, nextSize);
		mapConstructor.addMapObject(nextRandomWall);

		do {

			generateVerticalPosition();
			generateVerticalSize();

			points = MapGeometric.getWallPoints(position, size);

		} while (MapGeometric.isCircleOnSegment(spawnPoints, WALL_TOLERANCE, points.get(0),
				points.get(1)));

		MapObject randomVerticalWall = new MapObject(Item.WALL, Texture.WALL3, position, size);
		mapConstructor.addMapObject(randomVerticalWall);

		return mapConstructor;
	}

	private void generateSpawnPoints() {
		// TODO add spawn points generation
		spawnPoints.add(new MapDimension(5, 0.5, -7));
	}

	/**
	 * It generate a random position into the boundaries of the available size.
	 * A tolerance value is subtracted for saving the random generation to the
	 * creation of a position extremely near to the edge of the actual segment.
	 */
	private void generatePosition() {

		double maxWidth = availableSize.getWidth() - BORDER_TOLERANCE;
		double randomWidth = MapRandom.getRandomDouble(CENTER_TOLERANCE, maxWidth)
				* MapRandom.getRandomSign();

		double maxLength = availableSize.getLength() - BORDER_TOLERANCE;
		double randomLength = MapRandom.getRandomDouble(CENTER_TOLERANCE, maxLength)
				* MapRandom.getRandomSign();

		position = new MapDimension(randomWidth, -1, randomLength);
	}

	/**
	 * It generate a random value for the first segment's size of the random
	 * wall. The size is equal to the distance from the actual position of the
	 * center of the wall to the edge of the segment.
	 */
	private void generateSize() {

		double tmpPositionLength = position.getLength();
		double positionLength = tmpPositionLength > 0 ? tmpPositionLength : -tmpPositionLength;
		double length = 2 * (availableSize.getLength() - positionLength);

		size = new MapDimension(0.5, 2, length);
	}

	private void generateNextSize() {

		double tmpOldWallCenter = position.getLength();
		double oldWallCenter = tmpOldWallCenter < 0 ? tmpOldWallCenter : -tmpOldWallCenter;

		double oldWallSize = size.getLength();

		double nextSizeLength = oldWallCenter + oldWallSize / 2 + PLAYER_SIZE;
		nextSizeLength = (availableSize.getLength() - nextSizeLength);

		nextSize = new MapDimension(0.5, 2, nextSizeLength);
	}

	/**
	 * It generate the position of the second and last segment of the actual
	 * wall. This position is generated based on the center of the previous wall
	 * segment and the door (equal to the player size).
	 */
	private void generateNextPosition() {

		double oldWallCenter = position.getLength();
		double oldWallSize = size.getLength();

		double nextPositionLength;

		if (oldWallCenter < 0) {
			nextPositionLength = oldWallCenter + oldWallSize / 2 + PLAYER_SIZE
					+ nextSize.getLength() / 2;

			doorCenter = oldWallCenter + oldWallSize / 2 + PLAYER_SIZE / 2;
		}

		else {
			nextPositionLength = oldWallCenter - oldWallSize / 2 - PLAYER_SIZE
					- nextSize.getLength() / 2;

			doorCenter = oldWallCenter - oldWallSize / 2 - PLAYER_SIZE / 2;
		}

		nextPosition = new MapDimension(position.getWidth(), -1, nextPositionLength);
	}

	private void generateVerticalPosition() {

		double width = (availableSize.getWidth() - position.getWidth()) / 2;

		double maxLength = position.getLength() - BORDER_TOLERANCE;
		double randomLength = MapRandom.getRandomDouble(CENTER_TOLERANCE, maxLength)
				* MapRandom.getRandomSign();

		verticalPosition = new MapDimension(width, -1, randomLength);

	}

	// private void generateVerticalSize() {
	//
	// double tmpPositionWidth = position.getWidth();
	// double positionWidth = tmpPositionWidth > 0 ? tmpPositionWidth :
	// -tmpPositionWidth;
	// double width = 2 * (availableSize.getWidth() - positionWidth);
	//
	// size = new Dimension(width, 2, 0.5);
	// }

	private void generateVerticalSize() {

		MapDimension wallWidth = new MapDimension(availableSize.getWidth() - position.getWidth(), -1, 0);
		MapDimension borderWidth = new MapDimension(availableSize.getWidth(), -1, 0);

		size = new MapDimension(MapGeometric.getDistance(wallWidth, borderWidth), 2, 0.5);
	}

}
