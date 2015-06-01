package exceptions;

/**
 * Thrown if the login fails (it fails both if the password is wrong and the
 * user is not registered).
 * 
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
