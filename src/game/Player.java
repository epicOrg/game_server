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

	private PlayerStatus playerStatus;

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

	public PlayerStatus getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		this.playerStatus = playerStatus;
	}

}
