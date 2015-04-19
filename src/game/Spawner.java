package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Noris
 * @date 2015/04/18
 */

public class Spawner {

	/**
	 * Get a random position into the boundaries of the map.
	 * 
	 * @param mapSize
	 * @return a random PlayerStatus
	 */
	public PlayerStatus randomSpawn(float[] mapSize) {

		Random random = new Random();

		float xPosition = random.nextFloat() * mapSize[0];
		float yPosition = random.nextFloat() * mapSize[1];
		float zPosition = random.nextFloat() * mapSize[2];

		float xDirection = random.nextFloat() * mapSize[0];
		float yDirection = random.nextFloat() * mapSize[1];
		float zDirection = random.nextFloat() * mapSize[2];

		return new PlayerStatus(xPosition, yPosition, zPosition, xDirection,
				yDirection, zDirection);
	}

	/**
	 * Select a random position from a positions list.
	 * 
	 * @param spawnList
	 * @return a random PlayerStatus randomly selected from a list.
	 */
	public PlayerStatus randomSpawnFromList(ArrayList<PlayerStatus> spawnList) {

		int randomNumber = new Random().nextInt(spawnList.size());

		return spawnList.get(randomNumber);

	}

}
