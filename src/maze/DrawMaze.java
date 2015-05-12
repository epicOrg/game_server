package maze;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Hermann Tom
 * @date 2015/05/05
 */

public class DrawMaze extends JPanel {

	private Maze maze = new Maze(4,4);

	public void paintComponent(Graphics g) {

		int alpha = this.getHeight();
		int beta = this.getWidth();

		int cellWidth = alpha / maze.getxSize(); // 20
		int cellHeight = beta / maze.getySize(); // 20
		int boardWidth = alpha + 1; // cellWidth * (maze.xSize + 1);
		int boardHeight = beta + 1; // cellHeight * (maze.ySize + 1);

		for (int i = 0; i < maze.getxSize(); i++) {
			for (int j = 0; j < maze.getySize(); j++) {
				Coordinate2D c = new Coordinate2D(this.maze, i, j);
				Node n = maze.nodeAt(c);
				int x1 = cellWidth * i + 2;
				int y1 = cellHeight * j + 2;
				int x2 = x1 + cellWidth;
				int y2 = y1 + cellHeight;
				g.setColor(Color.BLACK);

				if (!n.isLinkedTo(n.minusY())) {
					g.drawLine(x1, y1, x2, y1);
				}
				g.setColor(Color.BLUE);

				if (!n.isLinkedTo(n.plusY()))

					g.drawLine(x1, y2, x2, y2);

				g.setColor(Color.ORANGE);
				if (!n.isLinkedTo(n.minusX()))

					g.drawLine(x1, y1, x1, y2);
				g.setColor(Color.RED);
				if (!n.isLinkedTo(n.plusX()))

					g.drawLine(x2, y1, x2, y2);
			}
		}
	}

	public DrawMaze(Maze maze) {
		this.maze = maze;
	}
}
