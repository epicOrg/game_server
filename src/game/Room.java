package game;

import java.util.ArrayList;

import exception.FullRoomException;

/**
 * @author Micieli
 * @date 2015/04/18
 */

public class Room {

	public static final int MAX_PLAYERS = 2;

	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayers;
	private boolean full = false;

	public Room(String name) {
		super();
		this.name = name;
		currentPlayers = 0;
	}

	public void addPlayer(Player player) throws FullRoomException {
		if (currentPlayers >= MAX_PLAYERS) {
			throw new FullRoomException();
		}
		players.add(player);
		currentPlayers++;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public boolean isFull() {
		return full;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public String getName() {
		return name;
	}

}
