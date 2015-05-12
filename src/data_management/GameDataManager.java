package data_management;

import exceptions.NoSuchRoomException;
import exceptions.RoomAlreadyExistsException;
import game.Room;
import game.RoomEventListener;
import game.RoomPlayersUpdater;
import game.RoomThread;
import game.WinCheckerTest;

import java.util.ArrayList;

import voip.RoomAudioCall;

/**
 * @author Micieli
 * @date 2015/04/18
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

	public void newRoom(String name) throws RoomAlreadyExistsException {

		try {

			getRoomByName(name);

		} catch (NoSuchRoomException e) {
			Room room = new Room(name);
			RoomEventListener listener = new RoomPlayersUpdater(room);
			RoomThread roomThread = new RoomThread(room, new WinCheckerTest());
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
	
	public void newAudioCallForRoom(Room room){
		RoomAudioCall audioCall = new RoomAudioCall(room);
		audioCalls.add(audioCall);		
	}
	
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
