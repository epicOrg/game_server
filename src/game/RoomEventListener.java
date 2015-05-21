package game;

import services.CurrentRoom;
import services.Game;
import services.RoomService;

/**
 * 
 * Defines a generic interface for an implementation of a components that react to Room Event.
 * 
 * @author Micieli
 * @date 2015/04/25
 * @see Room
 */

public interface RoomEventListener {

	/**
	 * Reacts to an entrance of a new player in a <code>Room</code>.
	 * 
	 * @param player		the player just entered
	 * @see RoomService
	 */
	public void onNewPlayerAdded(Player player);

	/**
	 * 
	 * Reacts to the room full event, starting the game.
	 * @see Game
	 */
	public void onRoomFull();

	/**
	 * Reacts to the exit of a player from a Room.
	 * 
	 * @param player	the player removed
	 * @see CurrentRoom
	 */
	public void onPlayerRemoved(Player player);

	/**
	 * 
	 * Reacts to a forced game stops.
	 * @see Game
	 */
	public void onExtingFromGame();
	
	/**
	 * Reacts to the end of the game preparing the room for another round.
	 * 
	 */
	public void onGameEnded();
}
