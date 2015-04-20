package game;

import java.util.HashMap;

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

	private TeamGenerator teamGenerator;

	public Room(String roomName) {
		this.roomName = roomName;
		teamGenerator = new TeamGenerator();
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
		teamGenerator.getRandomTeam().addPlayer(player);

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

	public TeamGenerator getTeamGenerator() {
		return teamGenerator;
	}

}
