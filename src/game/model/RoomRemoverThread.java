package game.model;

import java.util.Timer;
import java.util.TimerTask;

import data_management.GameDataManager;
import exceptions.RoomNotEmptyException;

/**
 * A thread related to Room making same cleaning if the room is empty. It checks
 * at fixed time if the room can be cancelled. This is a temporary solutions. It
 * doesn't take into account the inactivity time of a <code>Room</code>, it just
 * checks if the <code>Room</code> is empty.
 * 
 * @author Micieli
 * @see Room
 * @see GameDataManager
 */

public class RoomRemoverThread extends Thread {

	private static final int CHECKING_DELAY_MIN = 1;
	private static final int MILLIS = CHECKING_DELAY_MIN * 60 * 1000;

	private Room room;
	private Timer timer;

	public RoomRemoverThread(Room room) {
		super();
		this.room = room;
		timer = new Timer();
	}

	@Override
	public void run() {

		timer.schedule(new RemoverTask(), MILLIS);
	}

	private class RemoverTask extends TimerTask {

		@Override
		public void run() {
			try {
				room.cancel();
				GameDataManager.getInstance().deleteRoom(room);
				timer.cancel();
				// TODO DEBUG PRINT
				System.out.println("Room \"" + room.getName() + "\" deleted!");
				room = null;
			} catch (RoomNotEmptyException e) {
				// TODO DEBUG PRINT
				System.out
						.println("Room \"" + room.getName() + "\" not empty!");
				timer.schedule(new RemoverTask(), MILLIS);
			}
		}
	}

}
