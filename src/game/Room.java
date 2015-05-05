package game;

import exceptions.FullRoomException;
import exceptions.NoSuchPlayerException;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 */

public class Room {

	public static final int MAX_PLAYERS = 3;

	private String roomName;
	private TeamGenerator teamGenerator;
	private RoomEventListener playersUpdater;

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

		teamGenerator.getRandomTeam().addPlayer(player);
		playersUpdater.onNewPlayerAdded(player);
	}

	/**
	 * Remove the player from the room (and from the team).
	 * 
	 * @param player
	 */
	public void removePlayer(Player player) {
		player.getTeam().removePlayer(player);
		player.setRoom(null);
		playersUpdater.onPlayerRemoved(player);
	}

	public Player getPlayerByName(String name) throws NoSuchPlayerException {

		for (Team t : teamGenerator.getTeams()) {
			for (Player p : t.getPlayers()) {
				if (p.getUsername().equals(name))
					return p;
			}
		}

		throw new NoSuchPlayerException();
	}

	/**
	 * @return true if the team is full (no more players can join the room),
	 *         false otherwise (there are more users slot).
	 */
	private boolean isFull() {

		int size = 0;

		for (Team team : teamGenerator.getTeams()) {
			size += team.getSize();
		}

		return size >= MAX_PLAYERS;
	}

	public int getSize() {

		int size = 0;

		for (Team team : teamGenerator.getTeams()) {
			size += team.getSize();
		}

		return size;
	}

	public String getName() {
		return roomName;
	}

	public TeamGenerator getTeamGenerator() {
		return teamGenerator;
	}

	public void setEventListener(RoomEventListener roomPlayersUpdater) {
		this.playersUpdater = roomPlayersUpdater;
	}

	public void checkIfFull() {
		if (isFull())
			playersUpdater.onRoomFull();

	}

}
