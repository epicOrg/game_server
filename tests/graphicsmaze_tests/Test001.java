package graphicsmaze_tests;

import game.map.maze.DrawMaze;
import game.map.maze.Maze;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * @author Hermann Tom
 * @date 2015/05/05
 */

@SuppressWarnings("serial")
class Test001 extends JFrame {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
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
