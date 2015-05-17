package game.map;

import org.json.JSONObject;

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
	 * @return a map in json format
	 */
	public JSONObject generateMap();

}
