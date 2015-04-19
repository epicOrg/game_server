package data_management;

import exceptions.NoSuchRoomException;
import game.Room;

import java.util.ArrayList;

/**
 * @author Micieli
 * @date 2015/04/18
 */

public class GameDataManager {

	private static GameDataManager instance = new GameDataManager();
	private ArrayList<Room> rooms = new ArrayList<>();

	private GameDataManager() {}
	
	public static GameDataManager getInstance() {
		return instance;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public boolean newRoom(String name) {
		// TODO rooms stesso nome
		rooms.add(new Room(name));
		return true;
	}

	public Room getRoomByName(String name) throws NoSuchRoomException {
		for (Room room : rooms) {
			if (name.equals(room.getName()))
				return room;
		}
		throw new NoSuchRoomException();
	}

}
