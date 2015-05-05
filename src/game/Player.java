package game;

import voip.AudioData;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 */

public class Player {

	private String username;
	private boolean status;
	
	private Room room;
	private Team team;
	
	private AudioData audioData = new AudioData();

	private PlayerStatus playerStatus;
	private PlayerEventListener playerEventListener;

	public Player(String username) {
		super();
		this.username = username;

		playerStatus = new PlayerStatus(5, 0.5f, -7, -1, -0.25f, 0);
	}
	
	public void setPlayerEventListener(PlayerEventListener playerEventListener) {
		this.playerEventListener = playerEventListener;
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
	
	public AudioData getAudioData() {
		return audioData;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
		playerEventListener.onPlayerStatusChanged();
	}
	
	public boolean getStatus() {
		return status;
	}
	
}
