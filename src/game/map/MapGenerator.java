package game.map;

import java.util.ArrayList;

import maze.Coordinate2D;
import maze.Maze;
import maze.Node;

/**
 * @author Hermann Tom
 * @date 2015/05/08
 */

public class MapGenerator implements IMapGenerator {

	private int groundDim = 20;
	private String str = null , str2 ;
	Maze maze = new Maze(4,4);
	private float cellLength = 38/4 ;
	private float cellWidth = 40/4;

	@Override
	public ArrayList<MapObject> generate() {
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();

		for(int i =0 ; i<maze.getxSize() ; i++){
			for (int j = 0 ; j<maze.getySize() ; j++){
				Coordinate2D c = new Coordinate2D(this.maze, i, j);
				Node n = maze.nodeAt(c);
				int x1 = 40/4 * i ;
				float y1 = 38/4 * j ;
				int x2 = x1 + (10);
				float y2 = y1 + (9);

				if (!n.isLinkedTo(n.minusY())) {               	
					if(x1<x2){               		
						str =(Integer.toString((x1-20)/2+ (x2-20)/2)) +  " 0 " + Integer.toString((int) ((y1-19)/2 + (y2-19)/2)) ; 
						str2 = "10 1 1,5";
						mapObjects.add(new MapObject("Wall", "wall_texture_03", str , str2));
					}   
				}

				if (!n.isLinkedTo(n.minusX())) 
				{
					if(y1<y2){
						str =(Integer.toString((x1-20)/2+ (x2-20)/2)) +  " 0 " + Integer.toString((int) ((y1-19/2) + (y2-19)/2)) ;
						str2 = "1.5 1 9";
						mapObjects.add(new MapObject("Wall", "wall_texture_03", str , str2));

					}
				}
			}

		}
		mapObjects.add(new MapObject("Obstacle", "obstacle_texture_01", "-0.7 -1 -4.25", "0.3 2.5"));
		mapObjects.add(new MapObject("Obstacle", "obstacle_texture_01", "-2.9 -1 -4.25", "0.3 2.5"));
		mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "0 -1 20", "40 2 2"));
		mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "0 -1 -20", "40 2 2"));
		mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "20 -1 0", "2 2 38"));
		mapObjects.add(new MapObject("Wall", "hedge_texture_02_1", "-20 -1 0", "2 2 38"));
		//win 
		mapObjects.add(new MapObject("Obstacle", "arrow_texture_02", "0 -1 17", "0.3 2.5"));
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
