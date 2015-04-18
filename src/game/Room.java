package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import exception.FullRoomException;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 */

public class Room {

	public static final int MAX_PLAYERS = 10;

	private String roomName;

	private HashMap<String, Player> players = new HashMap<String, Player>();

	private Team teamA;
	private Team teamB;

	private ArrayList<Team> teams;

	public Room(String roomName) {
		super();
		this.roomName = roomName;
		generateTeams();
	}

	public void addPlayer(Player player) throws FullRoomException {

		if (isFull()) {
			throw new FullRoomException();
		}

		players.put(player.getUsername(), player);
	}

	/**
	 * Remove the player from the room (and obviously from the team).
	 * 
	 * @param player
	 */
	public void removePlayer(Player player) {
		player.getTeam().removePlayer(player);
		player.setRoom(null);
		players.remove(player.getUsername());
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public boolean isFull() {
		return getCurrentPlayers() >= MAX_PLAYERS;
	}

	public int getCurrentPlayers() {
		return players.size();
	}

	public String getName() {
		return roomName;
	}

	/**
	 * Team generator: this method generate two team with different colors.
	 */
	public void generateTeams() {

		teamA = new Team();
		teamB = new Team();

		while (teamA.getTeamColor().equals(teamB.getTeamColor())) {
			teamB.setRandomTeamColor();
		}
	}

	/**
	 * Select a team for the incoming player.
	 * 
	 * @return the team with less players or, if the teams has the same number
	 *         of players, a random team.
	 */
	public Team getRandomTeam() {

		if (teamA.getSize() < teamB.getSize())
			return teamA;

		else if (teamA.getSize() > teamB.getSize())
			return teamB;

		if (new Random().nextBoolean())
			return teamA;

		return teamB;
	}

	public ArrayList<Team> getTeams() {
		teams.add(teamA);
		teams.add(teamB);
		return teams;
	}

}
