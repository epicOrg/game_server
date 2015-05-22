package data_management;

import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.RoomPlayersUpdater;
import game.model.Room;
import game.model.RoomEventListener;

import java.util.ArrayList;

import voip.RoomAudioCall;

/**
 * 
 * Provides methods to manage Game data. 
 * Allows a shared knowledge of current server state as regarding gaming.
 * 
 * @author Micieli
 * @date 2015/04/18
 */

public class GameDataManager {

	private static GameDataManager instance = new GameDataManager();
	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<RoomAudioCall> audioCalls = new ArrayList<>();

	private GameDataManager() {}

	public static GameDataManager getInstance() {
		return instance;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * 
	 * Creates a new {@link Room} with the given name.
	 * 
	 * @param name							the name of the room
	 * @throws RoomAlreadyExistsException	if another <code>Room</code> with the same name exists
	 */
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

	/**
	 * 
	 * 
	 * @param name					the name of the room
	 * @return						the room that has the given name
	 * @throws NoSuchRoomException	if there isn't any room with the given name
	 */
	public Room getRoomByName(String name) throws NoSuchRoomException {
		for (Room room : rooms) {
			if (name.equals(room.getName()))
				return room;
		}

		throw new NoSuchRoomException();
	}
	
	
	/**
	 * 
	 * Ends {@link RoomAudioCall} for the room with the given name
	 * 
	 * @param roomName				the name of the room
	 * @throws NoSuchRoomException	if there isn't any room with the given name
	 * @see RoomAudioCall
	 */
	public void stopCallForRoom(String roomName) throws NoSuchRoomException{
		RoomAudioCall audioCall = getCallbyRoomName(roomName);
		audioCall.endCall();
		audioCalls.remove(audioCall);
	}
	
	/**
	 * 
	 * Adds a new instance of <code>RoomAudioCall</code> to the calls list.
	 * It doesn't starts audio conversation neither prepare it
	 * 
	 * @param room		the room for which the Calls have to be create
	 */
	public void newAudioCallForRoom(Room room){
		RoomAudioCall audioCall = new RoomAudioCall(room);
		audioCalls.add(audioCall);		
	}
	
	/**
	 * 
	 * 
	 * @param name					the name of the room
	 * @return						the <code>RoomAudioCall</code> of the room that has the given name
	 * @throws NoSuchRoomException	if there isn't any room with the given name
	 */	
	public RoomAudioCall getCallbyRoomName(String roomName) throws NoSuchRoomException{
		
		for (RoomAudioCall roomAudioCall : audioCalls) {
				if(roomAudioCall.getRoom().getName().equals(roomName))
					return roomAudioCall;
		}
		
		throw new NoSuchRoomException();
	}
	
	public ArrayList<RoomAudioCall> getAudioCalls() {
		return audioCalls;
	}
}
