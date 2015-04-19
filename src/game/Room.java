package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import exceptions.FullRoomException;

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

	public Room(String roomName) {
		this.roomName = roomName;
		generateTeams();
	}

	/**
	 * Add a player to the room, and then to a random team.
	 * 
	 * @param player
	 * @throws FullRoomException
	 */
	public void addPlayer(Player player) throws FullRoomException {

		if (isFull()) {
			throw new FullRoomException();
		}

		players.put(player.getUsername(), player);
		getRandomTeam().addPlayer(player);

	}

	/**
	 * Remove the player from the room (and from the team).
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

	/**
	 * @return true if the team is full (no more players can join the room),
	 *         false otherwise (there are more users slot).
	 */
	private boolean isFull() {
		return getSize() >= MAX_PLAYERS;
	}

	/**
	 * @return the numbers of the users currently playing in the team.
	 */
	public int getSize() {
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
			teamB.setRandomTeamColorFromList();
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

	/**
	 * @return an ArrayList containing the two teams.
	 */
	public ArrayList<Team> getTeams() {

		ArrayList<Team> teams = new ArrayList<Team>(2);
		teams.add(teamA);
		teams.add(teamB);
		return teams;
	}

}
