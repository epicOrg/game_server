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

	private AudioData audioData ;

	private PlayerStatus playerStatus;
	private PlayerEventListener playerEventListener;

	public Player(String username) {
		super();
		this.username = username;
		playerStatus = new PlayerStatus();
		audioData = new AudioData();
	}

	public void setPlayerEventListener(PlayerEventListener playerEventListener) {
		this.playerEventListener = playerEventListener;
	}

	public String getUsername() {
		return username;
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
