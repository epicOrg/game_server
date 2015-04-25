package data_management;

import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.Room;
import game.RoomEventListener;
import game.RoomPlayersUpdater;

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

	public void newRoom(String name) throws RoomAlreadyExistsException {

		try {

			getRoomByName(name);

		} catch (NoSuchRoomException e) {
			Room room = new Room(name);
			RoomEventListener listener = new RoomPlayersUpdater(room);
			room.setEventListener(listener);
			rooms.add(room);
			return;
		}

		throw new RoomAlreadyExistsException();
	}

	public Room getRoomByName(String name) throws NoSuchRoomException {

		for (Room room : rooms) {
			if (name.equals(room.getName()))
				return room;
		}

		throw new NoSuchRoomException();
	}

}
