package game.model;

/**
 * Interface that defines listener for player status changing. During the game a
 * player have to send a "ready" message when he has finished to load the game
 * and is ready to start playing.
 * 
 * @author Micieli
 * @date 2015/04/25
 * @see Player
 */

public interface PlayerEventListener {

	/**
	 * It is called when a player changes his status in order to allow the
	 * reaction to that event.
	 */
	public void onPlayerStatusChanged();

}
