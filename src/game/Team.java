package game;

import java.awt.Color;
import java.util.ArrayList;
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
		setRandomTeamColorFromList();
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	private boolean isFull() {
		return getSize() >= MAX_PLAYERS;
	}

	public void setTeamColor(Color teamColor) {
		this.teamColor = teamColor;
	}

	/**
	 * Generate a random team color. If used twice it can generate two different
	 * colors but very very similar.
	 */
	public void setRandomTeamColor() {
		Random random = new Random();
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		teamColor = new Color(r, g, b);
	}

	/**
	 * Select a random color from a predefined colors list.
	 */
	public void setRandomTeamColorFromList() {

		ArrayList<Color> admittedColors = new ArrayList<Color>();
		admittedColors.add(Color.BLUE);
		admittedColors.add(Color.RED);
		admittedColors.add(Color.GREEN);

		int randomNumber = new Random().nextInt(admittedColors.size());

		this.teamColor = admittedColors.get(randomNumber);
	}

	public Color getTeamColor() {
		return teamColor;
	}

}
