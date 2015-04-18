package game;

import java.util.ArrayList;

import exception.FullRoomException;

public class Room {
	
	public static final int MAX_PLAYER = 2;
	
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayers;
	private boolean full = false;
	
	public Room(String name) {
		super();
		this.name = name;
		currentPlayers = 0;
	}

	public void addPlayer(Player player) throws FullRoomException{
		if(players.size() >= MAX_PLAYER){
			throw new FullRoomException();
		}
		players.add(player);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;		
	}
	
	public boolean isFull(){
		return full;
	}
	
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	public String getName() {
		return name;
	}
	
}
