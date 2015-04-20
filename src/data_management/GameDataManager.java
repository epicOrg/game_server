package data_management;

import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.Room;

import java.util.ArrayList;

/**
 * @author Micieli
 * @date 2015/04/18
 */

public class GameDataManager {

	private static GameDataManager instance = new GameDataManager();
	private ArrayList<Room> rooms = new ArrayList<>();

	private GameDataManager() {
	}

	public static GameDataManager getInstance() {
		return instance;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * Create a new @see {@link Room}
	 * 
	 * @param name
	 *            the name of a new room
	 * @throws RoomAlreadyExistsException
	 */
	public void newRoom(String name) throws RoomAlreadyExistsException {

		try {

			getRoomByName(name);

		} catch (NoSuchRoomException e) {

			rooms.add(new Room(name));
			return;

		}

		throw new RoomAlreadyExistsException();
	}

	/**
	 * 
	 * @param name
	 *            the name of a room
	 * @return the instance of Room with the same name
	 * @throws NoSuchRoomException
	 */
	public Room getRoomByName(String name) throws NoSuchRoomException {
		for (Room room : rooms) {
			if (name.equals(room.getName()))
				return room;
		}
		throw new NoSuchRoomException();
	}

}
