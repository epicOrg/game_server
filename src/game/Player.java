package game;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 */

public class Player {

	private String username;
	private Room room;
	private Team team;

	private float xPosition;
	private float yPosition;

	public Player(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public float getXPosition() {
		return xPosition;
	}

	public void setXPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getYPosition() {
		return yPosition;
	}

	public void setYPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
