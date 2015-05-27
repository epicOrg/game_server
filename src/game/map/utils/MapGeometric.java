package game.map.utils;

import game.map.MapDimension;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Geometric methods for the map generation.
 * 
 * @author Noris
 * @date 2015/05/20
 */

public class MapGeometric {

	/**
	 * @param position
	 *            a position
	 * @param center
	 *            the point that must be checked
	 * @param radius
	 *            the tolerance radius
	 * 
	 * @return true if the area is already used, false otherwise
	 */
	public static boolean checkIfUsed(MapDimension position, MapDimension center, double radius) {

		double x = (position.getWidth() - center.getWidth());
		double x2 = x * x;

		double y = (position.getLength() - center.getLength());
		double y2 = y * y;

		if ((x2 + y2) < radius * radius)
			return true;

		return false;
	}

	/**
	 * 
	 * @param position
	 *            a position
	 * @param positions
	 *            the points that must be checked
	 * @param radius
	 *            the tolerance radius
	 * 
	 * @return true if the area of at least one circle's center of the array is
	 *         already used, false otherwise
	 */
	public static boolean checkIfUsed(MapDimension position, ArrayList<MapDimension> positions,
			double radius) {

		for (MapDimension p : positions) {
			if (checkIfUsed(position, p, radius))
				return true;
		}

		return false;
	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment
	 */
	public static ArrayList<MapDimension> getWallPoints(MapDimension position, MapDimension size) {

		ArrayList<MapDimension> points = new ArrayList<MapDimension>(2);

		double length1 = position.getLength() + size.getLength() / 2;
		double length2 = position.getLength() - size.getLength() / 2;

		double width1 = position.getWidth() + size.getWidth() / 2;
		double width2 = position.getWidth() - size.getWidth() / 2;

		points.add(new MapDimension(width1, 0, length1));
		points.add(new MapDimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment, without the wall size
	 */
	public static ArrayList<MapDimension> getWallPointsOnLength(MapDimension position,
			MapDimension size) {

		ArrayList<MapDimension> points = new ArrayList<MapDimension>(2);

		double length1 = position.getLength() + size.getLength() / 2;
		double length2 = position.getLength() - size.getLength() / 2;

		double width1 = position.getWidth();
		double width2 = position.getWidth();

		points.add(new MapDimension(width1, 0, length1));
		points.add(new MapDimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param position
	 *            of the wall
	 * @param size
	 *            of the wall
	 * 
	 * @return the extremes point of the wall segment
	 */
	public static ArrayList<MapDimension> getWallPointsOnWidth(MapDimension position,
			MapDimension size) {

		ArrayList<MapDimension> points = new ArrayList<MapDimension>(2);

		double length1 = position.getLength();
		double length2 = position.getLength();

		double width1 = position.getWidth() + size.getWidth() / 2;
		double width2 = position.getWidth() - size.getWidth() / 2;

		points.add(new MapDimension(width1, 0, length1));
		points.add(new MapDimension(width2, 0, length2));

		return points;

	}

	/**
	 * @param p
	 *            point position
	 * @param l1
	 *            a point of the line
	 * @param l2
	 *            another point of the line
	 * 
	 * @return true if the point is on the line of the wall, false otherwise
	 */
	public static boolean isPointOnLine(MapDimension p, MapDimension l1, MapDimension l2) {

		if (Line2D.ptLineDist(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength(),
				p.getWidth(), p.getLength()) == 0)
			return true;

		return false;
	}

	/**
	 * 
	 * @param points
	 *            an array of positions
	 * @param l1
	 *            a point of the line
	 * @param l2
	 *            another point of the line
	 * 
	 * @return true if at least one point of the array is on the line, false
	 *         otherwise
	 */
	public static boolean isPointOnLine(ArrayList<MapDimension> points, MapDimension l1,
			MapDimension l2) {

		for (MapDimension p : points) {
			if (isPointOnLine(p, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param p
	 *            point position
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if the point is on the segment, false otherwise
	 */
	public static boolean isPointOnSegment(MapDimension p, MapDimension l1, MapDimension l2) {

		if (Line2D.ptSegDist(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength(),
				p.getWidth(), p.getLength()) == 0)
			return true;

		return false;
	}

	/**
	 * @param points
	 *            an array of positions
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if at least one point of the array is on the segment, false
	 *         otherwise
	 */
	public static boolean isPointOnSegment(ArrayList<MapDimension> points, MapDimension l1,
			MapDimension l2) {

		for (MapDimension p : points) {
			if (isPointOnSegment(p, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param center
	 *            the center position of the circle
	 * @param radius
	 *            the radius of the circle
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if the circle intersects the segment, false otherwise
	 */
	public static boolean isCircleOnSegment(MapDimension center, double radius, MapDimension l1,
			MapDimension l2) {

		Line2D line = new Line2D.Double();

		line.setLine(l1.getWidth(), l1.getLength(), l2.getWidth(), l2.getLength());

		Point2D point = new Point2D.Double();
		point.setLocation(center.getWidth(), center.getLength());

		if (line.ptSegDist(point) <= radius)
			return true;

		return false;
	}

	/**
	 * @param centers
	 *            an array of circle's centers positions
	 * @param radius
	 *            the radius of every circle
	 * @param l1
	 *            the first end of the segment
	 * @param l2
	 *            the second end of the segment
	 * 
	 * @return true if at least one circle of the array intersects the segment,
	 *         false otherwise
	 */
	public static boolean isCircleOnSegment(ArrayList<MapDimension> centers, double radius,
			MapDimension l1, MapDimension l2) {

		for (MapDimension c : centers) {
			if (isCircleOnSegment(c, radius, l1, l2))
				return true;
		}

		return false;
	}

	/**
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * 
	 * @return the distance between p1 and p2
	 */
	public static double getDistance(MapDimension p1, MapDimension p2) {
		return Point.distance(p1.getWidth(), p1.getLength(), p2.getWidth(), p2.getLength());
	}

	/**
	 * @param point1
	 *            the first point
	 * @param point2
	 *            the second point
	 * 
	 * @return the distance between p1 and p2
	 */
	public static double getDistance3D(MapDimension point1, MapDimension point2) {

		return Math.sqrt((point1.getWidth() - point2.getWidth())
				* (point1.getWidth() - point2.getWidth())
				+ (point1.getLength() - point2.getLength())
				* (point1.getLength() - point2.getLength())
				+ (point1.getHeight() - point2.getHeight())
				* (point1.getHeight() - point2.getHeight()));
	}

}
