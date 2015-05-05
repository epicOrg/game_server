package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maze.Coordinate2D;
import maze.Node;


/**
 * 
 * @author HERMANN TOM
 *
 */
public class Maze {
	
	private int xSize ;
	private int ySize;	
	
	
	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	private  Map<Coordinate2D, Node> nodes;
	private  Node start;
	
	   public Maze(int x, int y) {
           this.xSize = x;
           this.ySize = y;
           nodes = new HashMap<>(x * y);
           fill();
           this.start = chooseRandomNode();
           growMaze();
       }
	   
	   /**
	    * 
	    */
	    private void fill() {
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    Coordinate2D coord = new Coordinate2D(this, i, j);
                    nodes.put(coord, new Node(coord));
                }
            }
        }
	    
        public Node nodeAt(Coordinate2D coord) {
            if (coord == null) return null;
            return nodes.get(coord);
        }

        private Node chooseRandomNode() {
            int n = (int) (Math.random() * xSize * ySize);
            return new ArrayList<>(nodes.values()).get(n);
        }

        private void growMaze() {
            List<Node> frontier = new ArrayList<>(xSize * ySize);
            frontier.add(start);
            start.linked = true;
            while (!frontier.isEmpty()) {
                Collections.shuffle(frontier);
                Node n = frontier.get(0);
                Node next = n.linkRandomUnlinkedNeighbour();
                if (next != null) {
                    frontier.add(next);
                } else {
                    frontier.remove(0);
                }
            }
        }

}
