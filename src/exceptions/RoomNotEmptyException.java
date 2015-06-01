package exceptions;

import game.model.Room;

/**
 * Thrown while removing a <code>Room</code> that isn't empty.
 * 
 * @author Micieli
 * @see Room#cancel()
 */
@SuppressWarnings("serial")
public class RoomNotEmptyException extends Exception {

}
