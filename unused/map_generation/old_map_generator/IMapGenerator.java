package game.map;

import java.util.ArrayList;

/**
 * @author Torlaschi
 * @date 2015/04/22
 */

public interface IMapGenerator {

	public ArrayList<MapObject> generate();

	public float getWidth();

	public float getHeight();

}
