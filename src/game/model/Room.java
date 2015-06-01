package game.model;

import exceptions.FullRoomException;
import exceptions.NoSuchPlayerException;
import exceptions.RoomCancelledException;
import exceptions.RoomNotEmptyException;
import game.map.IMap;
import game.map.MapDimension;
import game.map.generation.IMapGenerator;
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
	private boolean allPlayerReady = false;
	private volatile boolean cancelled =  false;
	
	private IMap map;
	private IMapGenerator mapGenerator;

	/**
	 * Create a room with the given name
	 * 
	 * @param roomName
	 *            the name of the room
	 */
	public Room(String roomName) {
		this.roomName = roomName;
		this.teamManager = new TeamManager();
		this.maxPlayers = MAX_PLAYERS;
		generateMap();
	}

	public Room(String roomName, int numberOfTeam, int numberOfPlayrXTeam, IMapGenerator generator) {
		this.roomName = roomName;
		this.teamManager = new TeamManager(numberOfTeam, numberOfPlayrXTeam);
		this.maxPlayers = numberOfPlayrXTeam * numberOfTeam;
		this.mapGenerator = generator;
		generateMap();
	}

	/**
	 * Generates a random map in which the player will move.
	 */
	public void generateMap() {
		map = mapGenerator.generateMap(new MapDimension(20, 20, 20), maxPlayers);
	}

	/**
	 * Add a player to the room, and then into a random team.
	 * 
	 * @param player
	 *            the player to be added
	 * @throws FullRoomException
	 *             if the room is already full
	 * @throws RoomCancelledException 
	 */
	public synchronized void addPlayer(Player player) throws FullRoomException, RoomCancelledException {

		if(cancelled){
			throw new RoomCancelledException();
		}
		
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
	 * @throws RoomCancelledException 
	 */
	public synchronized void removePlayer(Player player) throws RoomCancelledException {
		
		if(cancelled){
			throw new RoomCancelledException();
		}

		if (inPlay && allPlayerReady) {
			playersUpdater.onExtingFromGame();

			// TODO DUBUG PRINT
			System.out.println("Game interrupted.");

		} else {
			teamManager.removePlayer(player);
			playersUpdater.onPlayerRemoved(player);
		}
	}

	public synchronized Player getPlayerByName(String name) throws NoSuchPlayerException {

		for (Team t : teamManager.getTeams()) {
			for (Player p : t.getPlayers()) {
				if (p.getUsername().equals(name))
					return p;
			}
		}

		throw new NoSuchPlayerException();
	}

	/**
	 * 
	 * @return	the number of current player in the <code>Room</code>
	 */
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

	public TeamManager getTeamManager() {
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
		if (isFull() && !inPlay)
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
			this.allPlayerReady = false;
		}

		this.inPlay = inPlay;
	}
	
	/**
	 * Set the <code>Room</code> cancelled avoiding other future operation.
	 * 
	 * @throws RoomNotEmptyException		if the room isn't empty.
	 */
	public synchronized void cancel() throws RoomNotEmptyException{
		if(!isEmpty() && !cancelled)
			throw new RoomNotEmptyException();
		this.cancelled  = true;
	}
	
	private boolean isFull() {
		return getSize() >= maxPlayers;
	}
	
	private boolean isEmpty(){
		return getSize() == 0;
	}

	public boolean isInPlayng() {
		return inPlay;
	}

	public boolean areAllPlayersReady() {
		return allPlayerReady;
	}

	public void setAllPlayerReady(boolean status) {
		this.allPlayerReady = status;
	}

	public void setMapGenerator(IMapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
	}

	public IMap getMap() {
		return map;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}
}
