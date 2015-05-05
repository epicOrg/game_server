package graphicsmaze_tests;

import java.awt.Color;

import javax.swing.JFrame;

import maze.DrawMaze;
import maze.Maze;

/**
 * @author Hermann Tom
 * @date 2015/05/05
 */

class Test001 extends JFrame {

	public static void main(String[] args) {
		Test001 test = new Test001();
	}

	Maze maze = new Maze(4, 4);
	DrawMaze drawing = new DrawMaze(maze);

	public Test001() {
		this.setTitle("Maze");
		this.setSize(400, 400);
		this.setBackground(Color.WHITE);
		this.setContentPane(drawing);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
