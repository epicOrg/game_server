package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import maze.Coordinate2D;
import maze.Node;
import maze.Maze;


/**
 * 
 * @author HERMANN TOM
 *
 */
public class Node {
	
	 private  Coordinate2D coord;
     private  List<Node> linkedNeighbours;
     private List<Node> neighbours;
      boolean linked;
      
     
     public Node(Coordinate2D coord) {
         Objects.requireNonNull(coord);
         this.coord = coord;
         linkedNeighbours = new ArrayList<>(8);
     }

     public Node linkRandomUnlinkedNeighbour() {
         List<Node> list = new ArrayList<>(getNeighbours());
     //    list.removeIf(  n -> n.linked  );
         if (list.isEmpty()) return null;
         Collections.shuffle(list);
         Node next = list.get(0);
         next.getNeighbours();
         linkedNeighbours.add(next);
         next.linkedNeighbours.add(this);
         next.linked = true;
         return next;
     }
     
     public List<Node> getNeighbours() {
         if (neighbours == null) {
             List<Node> nodes = new ArrayList<>(Arrays.asList(minusX(), plusX(), minusY(), plusY()));
        //     nodes.removeIf(x -> x == null);
             neighbours = Collections.unmodifiableList(nodes);
         }
         return neighbours;
     }
     
     public boolean isDeadEnd() {
         return linkedNeighbours.size() == 1;
     }

     public boolean isBranch() {
         return linkedNeighbours.size() > 2;
     }

     public boolean isLinkedTo(Node node) {
         return linkedNeighbours.contains(node);
     }
     
     public Maze getMaze() { return coord.getMaze(); }
     public Coordinate2D getCoord() { return coord; }
     public Node minusX() { return getMaze().nodeAt(coord.minusX()); };
     public Node plusX()  { return getMaze().nodeAt(coord.plusX());  };
     public Node minusY() { return getMaze().nodeAt(coord.minusY()); };
     public Node plusY()  { return getMaze().nodeAt(coord.plusY());  };
}
