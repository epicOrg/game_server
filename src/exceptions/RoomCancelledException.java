package exceptions;

import game.model.RoomRemoverThread;

/**
 * Thrown when trying to do something with a {@link Room} that has been removed.
 * 
 * @author Micieli
 * @see RoomRemoverThread
 */

@SuppressWarnings("serial")
public class RoomCancelledException extends Exception {

}
