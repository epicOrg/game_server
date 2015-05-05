package game.map;

import java.util.ArrayList;

import maze.Coordinate2D;
import maze.Maze;
import maze.Node;

/**
 * @author Torlaschi
 * @date 2015/04/22
 */

public class SimpleMapGenerator implements IMapGenerator {
	
	private int groundDim = 20;
	private int x , y;

	@Override
	public ArrayList<MapObject> generate() {
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
		Maze maze = new Maze(4, 4);
		

		double h = 2.5;
		 for (int i = 0; i < maze.getxSize(); i++) {
	            for (int j = 0; j < maze.getySize(); j++) {
	            	 Coordinate2D c = new Coordinate2D(maze , i, j);
	                 Node n = maze.nodeAt(c);
	                 int x1 = 10 * i +2;
	                 int y1 = 10 * j +2;
	                 int x2 = x1 + 10;
	                 int y2 = y1 + 10;
	                 String str = null;
	                 
	                 mapObjects.add(new MapObject("Wall", "wall_texture_03", "-9.25 -1 -2.75", "1.5 2.5 14.5"));

	                 if (!n.isLinkedTo(n.minusY())) {
	                 	if(x1<x2){
//	                 		g.fillRect(x1, y1, (x2-x1), 5);
	                 		str = str.concat(Integer.toString(y1)) +" "+str.concat(Integer.toString(x1));
	                 	}
	                 	
	                 	else{
//	                 		g.fillRect(x2, y1, (x2-x1), 5);
	                 	}
	                 }
	                 
	                 if (!n.isLinkedTo(n.plusY()))  {
	                 	if(x1<x2){
//	                		g.fillRect(x2, y2 , (x1-x2), 5); 
	                 	}
	                 	
	                 	else{
//	                 		g.fillRect(x1, y1, (x1-x2), 5); 
	                 	}
	                 }
	                 	
	                 
	                 if (!n.isLinkedTo(n.minusX())) 
	                 {
	                 	if(y1<y2){
//	                 		g.fill3DRect(x1, y1, 5, (y2-y1)+5 , true);
	                 	}
	                 	else{
//	                 		g.fillRect(x1, y2, 5, (y2-y1)+5);
	                 	}

	                 }
	                 if (!n.isLinkedTo(n.plusX()))  {
	                 	if(y1<y2){
//	                 		g.fillRect(x2 , y2 , 5 , (y1-y2));
	                 	}
	                 	else {
//	                 		g.fillRect(x2, y1, 5 , (y1-y2));
	                 	}               
	            }
	       }
	            
		 }
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
