package maze;

import java.util.Objects;

import maze.Maze;
import maze.Coordinate2D;


/**
 * 
 * @author HERMANN TOM
 *
 */
public class Coordinate2D {

	  private  Maze maze;
      private  int x, y;

      public Coordinate2D(Maze maze, int x, int y) {
          Objects.requireNonNull(maze);
          if (x < 0 || x >= maze.getxSize() || y < 0 || y >= maze.getySize()) throw new IndexOutOfBoundsException();
          this.maze = maze;
          this.x = x;
          this.y = y;
      }
      
      @Override
      public int hashCode() {
          return Objects.hash(maze, x, y);
      }

      @Override
      public boolean equals(Object another) {
          if (!(another instanceof Coordinate2D)) return false;
          Coordinate2D c4d = (Coordinate2D) another;
          return maze == c4d.maze && x == c4d.x && y == c4d.y;
      }

      public int squareDistance(Coordinate2D another) {
          Objects.requireNonNull(another);
          if (maze != another.maze) throw new IllegalArgumentException();
          int dx = Math.abs(x - another.x);
          int dy = Math.abs(y - another.y);
          return dx + dy;
      }

      public Coordinate2D minusX() { return x == 0              ? null : new Coordinate2D(maze, x - 1, y); };
      public Coordinate2D plusX()  { return x == maze.getxSize() - 1 ? null : new Coordinate2D(maze, x + 1, y); };
      public Coordinate2D minusY() { return y == 0              ? null : new Coordinate2D(maze, x, y - 1); };
      public Coordinate2D plusY()  { return y == maze.getySize() - 1 ? null : new Coordinate2D(maze, x, y + 1); };
      public Maze getMaze() { return maze; }
}
