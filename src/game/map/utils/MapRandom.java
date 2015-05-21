package game.map.utils;

import java.util.Random;

/**
 * @author Noris
 * @date 2015/05/20
 */

public class MapRandom {

	/**
	 * It generates a random int between min an max.
	 * 
	 * @param min
	 *            the minimum value that the random number can assume
	 * @param max
	 *            the maximum value that the random number can assume
	 * 
	 * @return a random int number between min and max
	 */
	public static int getRandomInt(int min, int max) {
		return min + new Random().nextInt((max - min) + 1);
	}

	/**
	 * It generates a random double between min an max.
	 * 
	 * @param min
	 *            the minimum value that the random number can assume
	 * @param max
	 *            the maximum value that the random number can assume
	 * 
	 * @return a random double number between min and max
	 */
	public static double getRandomDouble(double min, double max) {
		return min + (new Random().nextDouble()) * (max - min);
	}

	/**
	 * It generates a random int between min an max, but different from
	 * forbidden.
	 * 
	 * @param min
	 *            the minimum value that the random number can assume
	 * @param max
	 *            the maximum value that the random number can assume
	 * @param forbidden
	 *            forbidden numbers
	 * 
	 * @return a random int number between min and max and different from
	 *         forbidden
	 */
	public static int getRandomInt(int min, int max, int... forbidden) {

		int random = getRandomInt(min, max);

		for (int i = 0; i < forbidden.length; i++) {

			if (forbidden[i] == random) {
				random = getRandomInt(min, max);
				i = 0;
			}
		}

		return random;
	}

	/**
	 * It generates a random sign.
	 * 
	 * @return a random sign (1 or -1)
	 */
	public static int getRandomSign() {

		if (new Random().nextBoolean())
			return -1;

		return 1;
	}

}
