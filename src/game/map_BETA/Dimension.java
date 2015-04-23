package game.map_BETA;

/**
 * @author Noris
 * @date 2015/04/23
 */

public class Dimension {

	private double width;
	private double length;
	private double heigh;

	public Dimension(double width, double length, double heigh) {
		this.width = width;
		this.length = length;
		this.heigh = heigh;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public double getHeigh() {
		return heigh;
	}

	public void setHeigh(float heigh) {
		this.heigh = heigh;
	}

}
