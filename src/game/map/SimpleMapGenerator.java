package game.map;

import java.util.ArrayList;

/**
 * @author Torlaschi
 * @date 2015/04/22
 */

public class SimpleMapGenerator implements IMapGenerator {
	
	private int groundDim = 20;

	@Override
	public ArrayList<MapObject> generate() {
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
		
		double h = 2.5;
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "-9.25 -1 -2.75", "1.5 2.5 14.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "-6.75 -1 -4.25", "3.5 2.5 1.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "-1 -1 -9.25", "15 2.5 1.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "7.25 -1 -2.75", "1.5 2.5 14.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "4 -1 -4.25", "5 2.5 1.5"));
        mapObjects.add(new MapObject("Obstacle", "obstacle_texture_01", "-0.7 -1 -4.25", "0.3 2.5"));
        mapObjects.add(new MapObject("Obstacle", "obstacle_texture_01", "-2.9 -1 -4.25", "0.3 2.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "3.75 -1 3.75", "5.5 2.5 1.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "-4.75 -1 3.75", "7.5 2.5 1.5"));
        mapObjects.add(new MapObject("Wall", "wall_texture_03", "-1 -1 -0.25", "10 2.5 1.5"));

        double h2 = 2;
        mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "0 -1 20", "40 2 2"));
        mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "0 -1 -20", "40 2 2"));
        mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "20 -1 0", "2 2 38"));
        mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "-20 -1 0", "2 2 38"));
		return mapObjects;
	}

	@Override
	public float getWidth() {
		return groundDim;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return groundDim;
	}

}
