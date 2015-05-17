package game.map;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * It represents a three dimension point.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class Dimension implements Comparable<Dimension> {

	private double width;
	private double height;
	private double length;

	public Dimension(double width, double height, double length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * @return a string which represents the dimension in float values
	 */
	public String toString() {

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);

		String string = df.format(width) + " " + df.format(height) + " " + df.format(length);

		string = string.replace(',', '.');

		return string;
	}

	/**
	 * @return a string which represents the dimension in double values
	 */
	public String toStringLong() {
		return width + " " + height + " " + length;
	}

	@Override
	public int compareTo(Dimension other) {
		return Double.compare(width, other.width) + Double.compare(height, other.height)
				+ Double.compare(length, other.length);
	}

}
