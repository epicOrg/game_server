package game;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.FullRoomException;
import exceptions.NoSuchPlayerException;
import game.map.Dimension;
import game.map.MapAdapter;
import game.map.generation.GridMapGenerator;
import game.map.generation.MapGenerator;

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
	private boolean inPlay = false;
	private RoomThread roomThread;

	private JSONObject map;
	private LinkedList<PlayerStatus> spawnPoints;
	private Dimension winPoint;

	public Room(String roomName) {
		this.roomName = roomName;
		teamGenerator = new TeamGenerator();
		generateMap();
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
		if (inPlay) {
			playersUpdater.onExtingFromGame();
			setInPlay(false);
			System.out.println("Game interrupted.");

		} else {

			player.getTeam().removePlayer(player);
			player.setRoom(null);
			playersUpdater.onPlayerRemoved(player);
		}
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

	public void setInPlay(boolean inPlay) {
		if (this.inPlay && !inPlay) {
			playersUpdater.onGameEnded();
			teamGenerator.emptyTeams();
		}

		this.inPlay = inPlay;
	}

	public boolean isInPlayng() {
		return inPlay;
	}

	public void setRoomThread(RoomThread roomThread) {
		this.roomThread = roomThread;
	}

	public RoomThread getRoomThread() {
		return roomThread;
	}

	private void generateMap() {

		MapGenerator mapGenerator = new GridMapGenerator(new Dimension(20, 20, 20), MAX_PLAYERS);

		// MapGenerator mapGenerator
		// = new DivisionMapGenerator(new Dimension(20, 20, 20));

		// MapGenerator mapGenerator
		// = new ForestMapGenerator(new Dimension(20, 20, 20), 30);

		// MapGenerator mapGenerator = new SimpleMapGenerator();

		// MapGenerator mapGenerator = new TestMapGenerator();

		MapAdapter mapAdapter = new MapAdapter(mapGenerator.generateMap());

		try {

			JSONObject jsonMap = new JSONObject();
			jsonMap.put(FieldsNames.GAME_WIDTH, mapAdapter.getAdaptedWidth());
			jsonMap.put(FieldsNames.GAME_HEIGHT, mapAdapter.getAdaptedHeight());
			jsonMap.put(FieldsNames.GAME_ITEMS, mapAdapter.getAdaptedItems());

			map = jsonMap;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		spawnPoints = mapAdapter.getAdaptedSpawnPoints();

		winPoint = mapAdapter.getAdaptedWinPoint();

	}

	public JSONObject getMap() {
		return map;
	}

	public PlayerStatus getSpawnPoint() {

		if (spawnPoints.size() == 1)
			return spawnPoints.getFirst();

		return spawnPoints.remove();

	}

	public Dimension getWinPoint() {
		return winPoint;
	}

}
