package map_editor;

import game.map.Item;
import game.map.MapDimension;
import game.map.MapObject;
import game.map.Texture;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Easy map editor.
 *
 * @author Noris
 * @date 2015/05/25
 */

public class MapEditor {

	private double wallSize;
	private double wallHeight;
	private String wallTexture;

	private Point originalMapSize;
	private Point originalP1;
	private Point originalP2;

	private MapDimension size;
	private MapDimension center;

	private ArrayList<MapObject> items;

	public MapEditor(Point mapSize) {

		this.originalMapSize = mapSize;
		items = new ArrayList<MapObject>();

		wallSize = 0.3;
		wallHeight = 2;
		wallTexture = Texture.WALL2;

	}

	public void addWall(Point p1, Point p2) {

		this.originalP1 = p1;
		this.originalP2 = p2;

		convertCoordinates();

		items.add(new MapObject(Item.WALL, wallTexture, center, size));

	}

	public void addWall(Point p1, Point p2, double wallSize, double wallHeight, String wallTexture) {

		addWall(p1, p2);

		this.wallSize = wallSize;
		this.wallHeight = wallHeight;
		this.wallTexture = wallTexture;

	}

	private void convertCoordinates() {

		if (originalP1.getX() == originalP2.getX()) {
			
			System.out.println("1");

			double originalSize = originalP1.getY() - originalP2.getY();
			originalSize = originalSize > 0 ? originalSize : -originalSize;

			Point originalCenter = new Point();
			originalCenter.setLocation(originalSize / 2, originalP1.getX());

			double newX = -originalMapSize.getX() / 2 + originalCenter.getX();
			double newY = originalMapSize.getY() / 2 - originalCenter.getY();

			center = new MapDimension(newX, -1, newY);
			size = new MapDimension(originalSize, wallHeight, wallSize);
		}

		else if (originalP1.getY() == originalP2.getY()) {
			
			System.out.println("2");

			double originalSize = originalP1.getX() - originalP2.getX();
			originalSize = originalSize > 0 ? originalSize : -originalSize;

			Point originalCenter = new Point();
			originalCenter.setLocation(originalSize / 2, originalP1.getY());

			double newY = originalMapSize.getX() / 2 - originalCenter.getX();
			double newX = -originalMapSize.getY() / 2 + originalCenter.getY();

			center = new MapDimension(newX, -1, newY);
			size = new MapDimension(wallSize, wallHeight, originalSize);

		}

		else {
			System.err.println("The game doesn't allow diagonal walls.");
			return;
		}

	}

	// private int getQuadrant(Point p) {
	//
	// if (p.getX() > originalMapSize.getX() / 2 && p.getY() >
	// originalMapSize.getY() / 2) {
	// return 1;
	// }
	//
	// if (p.getX() < originalMapSize.getX() / 2 && p.getY() >
	// originalMapSize.getY() / 2) {
	// return 2;
	// }
	//
	// if (p.getX() < originalMapSize.getX() / 2 && p.getY() <
	// originalMapSize.getY() / 2) {
	// return 3;
	// }
	//
	// else if (p.getX() > originalMapSize.getX() / 2 && p.getY() <
	// originalMapSize.getY() / 2) {
	// return 4;
	// }
	//
	// else {
	// return 0;
	// }
	// }
	
	public String getJavaCode() {
		
		StringBuilder s = new StringBuilder();
		
		s.append("mapConstructor.setMapSize(");
		s.append("new MapDimension(");
		s.append(originalMapSize.getX()/2);
		s.append(", ");
		s.append("0");
		s.append(", ");
		s.append(originalMapSize.getY()/2);
		s.append("));");
		s.append("\n");
		
		for (MapObject i : items) {
			s.append("mapConstructor.addMapObject(");
			s.append("new MapObject(");
			s.append(i.getObjectName());
			s.append(", ");
			s.append(i.getTextureName());
			s.append(", ");
			s.append("new MapDimension(");
			s.append(i.getPosition().getWidth());
			s.append(", ");
			s.append(i.getPosition().getHeight());
			s.append(", ");
			s.append(i.getPosition().getLength());
			s.append("), new MapDimension(");
			s.append(i.getSize().getWidth());
			s.append(", ");
			s.append(i.getSize().getHeight());
			s.append(", ");
			s.append(i.getSize().getLength());
			s.append(")));");
			s.append("\n");
		}
		
		return s.toString();
	}

}
