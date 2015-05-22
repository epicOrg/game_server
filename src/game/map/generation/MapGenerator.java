package game.map.generation;

import game.map.MapConstructor;

/**
 * Interface for the map generators.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public interface MapGenerator {

	/**
	 * It generate a map.
	 * 
	 * @return a description of the map
	 */
	public MapConstructor generateMap();

}
