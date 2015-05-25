package map_editor;

import java.awt.Point;

/**
 * Test of the {@link MapEditor}. It builds the java code of a map constructor
 * who contains the borders of a 40x40 map (real size: from -20 to +20).
 *
 * @author Noris
 * @date 2015/05/25
 */

class Test01 {

	public static void main(String[] args) {

		// Set the map size
		Point mapSize = new Point();
		mapSize.setLocation(40, 40);

		MapEditor m = new MapEditor(mapSize);

		// Wall 1
		Point p1 = new Point();
		p1.setLocation(0, 0);

		Point p2 = new Point();
		p2.setLocation(40, 0);

		m.addWall(p1, p2);

		// Wall 2
		Point p3 = new Point();
		p3.setLocation(40, 0);

		Point p4 = new Point();
		p4.setLocation(40, 40);

		m.addWall(p3, p4);

		// Wall 3
		Point p5 = new Point();
		p5.setLocation(40, 40);

		Point p6 = new Point();
		p6.setLocation(0, 40);

		m.addWall(p5, p6);

		// Wall 4
		Point p7 = new Point();
		p7.setLocation(0, 40);

		Point p8 = new Point();
		p8.setLocation(0, 0);

		m.addWall(p7, p8);

		// Print the java map
		System.out.println(m.getJavaCode());

	}

}
