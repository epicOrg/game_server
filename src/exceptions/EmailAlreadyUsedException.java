package exceptions;

/**
 * This exception is launched when an email is already used.
 * 
 * @author Modica
 * @author Gavina
 * @data 2015/04/17
 */

@SuppressWarnings("serial")
public class EmailAlreadyUsedException extends Exception {

	public EmailAlreadyUsedException() {
		super();
	}

}
