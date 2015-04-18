package data_management;

import game.Room;

import java.io.EOFException;
import java.util.ArrayList;

public class GameDataManager {
	
	private static GameDataManager instance = new  GameDataManager();
	private ArrayList<Room> rooms = new ArrayList<>();
	
	private GameDataManager(){}
	
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public static GameDataManager getInstance() {
		return instance;
	}	
	
	public boolean newRoom(String name) {
		// TODO rooms stesso nome
		rooms.add(new Room(name));
		return true;
		
	}
	
}
