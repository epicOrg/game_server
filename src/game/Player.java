package game;

public class Player {
	
	private String usename;
	private float xPosition;
	private float yPposition;

	public Player(String usename) {
		super();
		this.usename = usename;
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
