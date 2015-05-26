package exceptions;

/**
 * @author Noris
 * @date 2015/04/17
 * @see Login
 */

@SuppressWarnings("serial")
public class LoginFailedException extends Exception {

	public LoginFailedException(String string) {
		super(string);
	}

}
