package exceptions;

import game.model.RoomRemoverThread;

/**
 * Thrown whene trying to do something with a <code>Room</code> that has been
 * removed.
 * 
 * @author Micieli
 * @see RoomRemoverThread
 */
@SuppressWarnings("serial")
public class RoomCancelledException extends Exception {

}
