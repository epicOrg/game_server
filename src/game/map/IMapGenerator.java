package game.map;

import java.util.ArrayList;

public interface IMapGenerator {
	
	public ArrayList<MapObject> generate();
	public float getWidth();
	public float getHeight();

}
