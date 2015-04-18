package game;

public class Player {
	
	private String username;
	private float xPosition;
	private float yPposition;

	public Player(String username) {
		super();
		this.username = username;
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPposition() {
		return yPposition;
	}

	public void setyPposition(float yPposition) {
		this.yPposition = yPposition;
	}
}
