package game;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import exception.FullTeamException;

/**
 * @author Noris
 * @date 2015/04/18
 */

public class Team {

	private String teamName;
	private Color teamColor;

	private HashMap<String, Player> players = new HashMap<String, Player>();

	public static final int MAX_PLAYERS = Room.MAX_PLAYERS / 2;

	public Team() {
		super();
		setRandomTeamColor();
	}

	public Team(String teamName) {
		super();
		this.teamName = teamName;
		setRandomTeamColor();
	}

	public void addPlayer(Player player) throws FullTeamException {

		if (isFull()) {
			throw new FullTeamException();
		}

		players.put(player.getUsername(), player);
	}

	public void removePlayer(Player player) {
		player.setTeam(null);
		players.remove(player.getUsername());
	}

	public int getSize() {
		return players.size();
	}
	
	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public boolean isFull() {
		return getSize() >= MAX_PLAYERS;
	}

	public void setTeamColor(Color teamColor) {
		this.teamColor = teamColor;
	}

	/**
	 * Generate a random team color.
	 */
	public void setRandomTeamColor() {
		Random random = new Random();
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		teamColor = new Color(r, g, b);
	}

	public Color getTeamColor() {
		return teamColor;
	}

}
