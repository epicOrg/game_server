package data_management;

import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.RoomPlayersUpdater;
import game.map.generation.IMapGenerator;
import game.model.Player;
import game.model.Room;
import game.model.RoomEventListener;

import java.util.ArrayList;

import voip.RoomAudioCall;

/**
 * Provides methods to manage Game data. Allows a shared knowledge of current
 * server state as regarding gaming.
 * 
 * @author Micieli
 * @date 2015/04/18
 * @see Room
 * @see RoomAudioCall
 */

public class GameDataManager {

	private static GameDataManager instance = new GameDataManager();
	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<RoomAudioCall> audioCalls = new ArrayList<>();

	private GameDataManager() {
	}

	public static GameDataManager getInstance() {
		return instance;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * Creates a new {@link Room} with the given name.
	 * 
	 * @param name
	 *            the name of the room
	 * @throws RoomAlreadyExistsException
	 *             if another <code>Room</code> with the same name exists
	 */
	public void newRoom(String roomName, int numberOfTeam, int teamDimension,
			IMapGenerator mapGenerator) throws RoomAlreadyExistsException {

		try {

			getRoomByName(roomName);

		} catch (NoSuchRoomException e) {
			Room room = new Room(roomName, numberOfTeam, teamDimension,
					mapGenerator);
			RoomEventListener listener = new RoomPlayersUpdater(room);
			room.setEventListener(listener);
			rooms.add(room);
			return;
		}

		throw new RoomAlreadyExistsException();
	}

	/**
	 * @param name
	 *            the name of the room
	 * @return the room that has the given name
	 * @throws NoSuchRoomException
	 *             if there isn't any room with the given name
	 */
	public Room getRoomByName(String name) throws NoSuchRoomException {
		for (Room room : rooms) {
			if (name.equals(room.getName()))
				return room;
		}

		throw new NoSuchRoomException();
	}

	/**
	 * Remove the player from the room in which he is in (if there is one).
	 * 
	 * @param username
	 *            the username of the player to remove
	 * @throws NoSuchPlayerException
	 *             if the player isn't in any room.
	 */
	public void removePlayerFromAnyRooms(String username)
			throws NoSuchPlayerException {

		for (Room room : rooms) {
			try {
				Player player = room.getPlayerByName(username);
				room.removePlayer(player);
				return;
			} catch (NoSuchPlayerException e) {
				e.printStackTrace();
			}
		}

		throw new NoSuchPlayerException();
	}

	/**
	 * 
	 * Ends {@link RoomAudioCall} for the room with the given name.
	 * 
	 * @param roomName
	 *            the name of the room
	 * @throws NoSuchRoomException
	 *             if there isn't any room with the given name
	 * @see RoomAudioCall
	 */
	public void stopCallForRoom(String roomName) throws NoSuchRoomException {
		RoomAudioCall audioCall = getCallbyRoomName(roomName);
		audioCall.endCall();
		audioCalls.remove(audioCall);
	}

	/**
	 * Adds a new instance of <code>RoomAudioCall</code> to the calls list. It
	 * doesn't starts audio conversation neither prepare it.
	 * 
	 * @param room
	 *            the room for which the Calls have to be create
	 */
	public void newAudioCallForRoom(Room room) {
		RoomAudioCall audioCall = new RoomAudioCall(room);
		audioCalls.add(audioCall);
	}

	/**
	 * @param name
	 *            the name of the room
	 * @return the <code>RoomAudioCall</code> of the room that has the given
	 *         name
	 * @throws NoSuchRoomException
	 *             if there isn't any room with the given name
	 */
	public RoomAudioCall getCallbyRoomName(String roomName)
			throws NoSuchRoomException {

		for (RoomAudioCall roomAudioCall : audioCalls) {
			if (roomAudioCall.getRoom().getName().equals(roomName))
				return roomAudioCall;
		}

		throw new NoSuchRoomException();
	}
}
