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

	private float[] position = new float[3];
	private float[] direction = new float[3];

	public Player(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
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

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}

	public float[] getDirection() {
		return direction;
	}

	public void setDirection(float[] direction) {
		this.direction = direction;
	}

}
