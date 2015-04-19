package exceptions;

/**
 * This exception is launched when the registration is failed.
 * 
 * @author Micieli
 * @date 2015/04/17
 */

@SuppressWarnings("serial")
public class RegistrationFailedException extends Exception {

	public RegistrationFailedException(String string) {
		super(string);
	}

}
