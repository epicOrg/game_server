package map_generation;

import game.map.MapDimension;
import game.map.utils.MapConst;
import game.map.utils.MapGeometric;

import java.util.ArrayList;

/**
 * @author Noris
 * @date 2015/05/27
 */

class Test01 {

	public static void main(String[] args) {
		
		final double wallSize = 0.2;
		MapDimension wallPosition = new MapDimension(5, -1, 0);
		MapDimension wallLength = new MapDimension(0.2, 2, 20);
		
		MapDimension playerPosition = new MapDimension(5, -1, 12);

		ArrayList<MapDimension> points = MapGeometric.getWallPoints(wallPosition, wallLength);
		
		if (MapGeometric.isCircleOnSegment(playerPosition, MapConst.PLAYER_SIZE / 2 + wallSize,
				points.get(0), points.get(1))) {
			System.out.println("Player is on the wall!");
		}
		
		else {
			System.out.println("Player is free!");
		}

	}

}
