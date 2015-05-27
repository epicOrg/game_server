package game.model;

import exceptions.FullRoomException;
import exceptions.NoSuchPlayerException;
import game.RoomMapSelector;
import game.map.MapDimension;
import game.map.generation.GridMapGenerator;
import services.current_room.CurrentRoom;
import services.game.Game;
import services.rooms.Rooms;

/**
 * <code>Room</code> is the fundamental piece of the multiplayer game. Players
 * can come in and go out from it, finding other players and waiting from the
 * player number is sufficient to start the game. While entering into a
 * <code>Room</code> a player is assigned to a random team. A
 * {@link RoomEventListener} is advised of everything that happens
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/18
 * @see CurrentRoom
 * @see Rooms
 * @see Game
 */

public class Room {

	public static final int MAX_PLAYERS = Team.MAX_PLAYERS * TeamManager.NUMBER_OF_TEAMS;

	private String roomName;
	private TeamManager teamManager;
	private RoomEventListener playersUpdater;
	private boolean inPlay = false;
	private int maxPlayers;

	private RoomMapSelector roomMapSelector;

	/**
	 * Create a room with the given name
	 * 
	 * @param roomName
	 *            the name of the room
	 */
	public Room(String roomName) {
		this.roomName = roomName;
		teamManager = new TeamManager();
		maxPlayers = MAX_PLAYERS;
		generateMap();
	}

	public Room(String roomName, int numberOfTeam, int numberOfPlayrXTeam) {
		this.roomName = roomName;
		teamManager = new TeamManager(numberOfTeam, numberOfPlayrXTeam);
		maxPlayers = numberOfPlayrXTeam * numberOfTeam;
		generateMap();
	}

	/**
	 * Generates a random map in which the player will move.
	 */
	public void generateMap() {

		roomMapSelector = new RoomMapSelector(new GridMapGenerator(new MapDimension(20, 20, 20),
				maxPlayers));

		// roomMapSelector = new RoomMapSelector(new DivisionMapGenerator(new
		// MapDimension(20, 20, 20)));

		// roomMapSelector = new RoomMapSelector(new ForestMapGenerator(new
		// MapDimension(20, 20, 20), 30, MAX_PLAYERS));

		// roomMapSelector = new RoomMapSelector(new SimpleMapGenerator());

		// roomMapSelector = new RoomMapSelector(new TestMapGenerator());
	}

	/**
	 * Add a player to the room, and then into a random team.
	 * 
	 * @param player
	 *            the player to be added
	 * @throws FullRoomException
	 *             if the room is already full
	 */
	public void addPlayer(Player player) throws FullRoomException {

		if (isFull()) {
			throw new FullRoomException();
		}

		teamManager.getRandomTeam().addPlayer(player);
		playersUpdater.onNewPlayerAdded(player);
	}

	/**
	 * Remove the player from the room (and from the team). If a player is
	 * removed from a room while playing then the game is interrupted and all
	 * participants removed from the room.
	 * 
	 * @param player
	 *            the player to be removed
	 */
	public void removePlayer(Player player) {

		if (inPlay) {
			playersUpdater.onExtingFromGame();
			setInPlay(false);
			System.out.println("Game interrupted.");

		} else {
			teamManager.removePlayer(player);
			playersUpdater.onPlayerRemoved(player);
		}
	}

	public Player getPlayerByName(String name) throws NoSuchPlayerException {

		for (Team t : teamManager.getTeams()) {
			for (Player p : t.getPlayers()) {
				if (p.getUsername().equals(name))
					return p;
			}
		}

		throw new NoSuchPlayerException();
	}

	/**
	 * @return true if the teams are full (no more players can join the room),
	 *         false otherwise (there are more users slot).
	 */
	private boolean isFull() {
		return getSize() >= maxPlayers;
	}

	public int getSize() {

		int size = 0;

		for (Team team : teamManager.getTeams()) {
			size += team.getSize();
		}

		return size;
	}

	public String getName() {
		return roomName;
	}

	public TeamManager getTeamGenerator() {
		return teamManager;
	}

	public void setEventListener(RoomEventListener roomPlayersUpdater) {
		this.playersUpdater = roomPlayersUpdater;
	}

	/**
	 * Provides an asynchronous check for room full not only while entering a
	 * player.
	 */
	public void checkIfFull() {
		if (isFull())
			playersUpdater.onRoomFull();
	}

	/**
	 * Set room status defining if the game is started, or finished
	 * 
	 * @param inPlay
	 *            true if the Player are playing, false otherwise
	 */
	public void setInPlay(boolean inPlay) {
		if (this.inPlay && !inPlay) {
			playersUpdater.onGameEnded();
			teamManager.emptyTeams();
		}

		this.inPlay = inPlay;
	}

	public boolean isInPlayng() {
		return inPlay;
	}

	public RoomMapSelector getRoomMapSelector() {
		return roomMapSelector;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}
}
