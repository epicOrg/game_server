package game;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class PlayerStatus {

	private float xPosition;
	private float yPosition;
	private float zPosition;

	private float xDirection;
	private float yDirection;
	private float zDirection;

	public PlayerStatus(float xPosition, float yPosition, float zPosition,
			float xDirection, float yDirection, float zDirection) {

		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.zPosition = zPosition;

		this.xDirection = xDirection;
		this.yDirection = yDirection;
		this.zDirection = zDirection;
	}

	public void setPosition(float xPosition, float yPosition, float zPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.zPosition = zPosition;
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPosition() {
		return yPosition;
	}

	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public float getzPosition() {
		return zPosition;
	}

	public void setzPosition(float zPosition) {
		this.zPosition = zPosition;
	}

	public void setDirection(float xDirection, float yDirection,
			float zDirection) {
		this.xDirection = xDirection;
		this.yDirection = yDirection;
		this.zDirection = zDirection;
	}

	public float getxDirection() {
		return xDirection;
	}

	public void setxDirection(float xDirection) {
		this.xDirection = xDirection;
	}

	public float getyDirection() {
		return yDirection;
	}

	public void setyDirection(float yDirection) {
		this.yDirection = yDirection;
	}

	public float getzDirection() {
		return zDirection;
	}

	public void setzDirection(float zDirection) {
		this.zDirection = zDirection;
	}

}
