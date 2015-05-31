package map_generation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import game.map.MapDimension;
import game.map.utils.MapConst;
import game.map.utils.MapGeometric;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Noris
 * @date 2015/05/27
 */

public class Test01 {

	public static final double wallSize = 0.2;
	private MapDimension wallPosition;
	private MapDimension wallLength;

	@Before
	public void setUp() {

		wallPosition = new MapDimension(5, -1, 0);
		wallLength = new MapDimension(0.2, 2, 20);
	}

	/**
	 * Test method for
	 * {@link game.map.utils.MapGeometric#isCircleOnSegment(game.map.MapDimension, double, game.map.MapDimension, game.map.MapDimension)}
	 * .
	 */
	@Test
	public void testIsCircleOnSegment() {
		MapDimension playerPosition = new MapDimension(5, -1, 12);
		ArrayList<MapDimension> points = MapGeometric.getWallPoints(
				wallPosition, wallLength);

		// Player is free!
		assertFalse(MapGeometric.isCircleOnSegment(playerPosition,
				MapConst.PLAYER_SIZE / 2 + wallSize, points.get(0),
				points.get(1)));

		playerPosition = new MapDimension(5, -1, 0);
		// Player is on the wall!
		assertTrue(MapGeometric.isCircleOnSegment(playerPosition,
				MapConst.PLAYER_SIZE / 2 + wallSize, points.get(0),
				points.get(1)));

	}

}
