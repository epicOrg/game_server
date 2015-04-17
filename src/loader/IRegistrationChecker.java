package loader;

import java.io.IOException;

import exception.EmailAlreadyUsedException;
import exception.UsernameAlreadyUsedException;

/**
 * An interface for registration data checking
 * @author Gavina
 * @author Modica
 * @date 2015/04/17
 */
public interface IRegistrationChecker {
	
	/**
	 * 
	 * Check if username already exist
	 * @param username  
	 * @throws UsernameAlreadyUsedException
	 */
	public void checkUsername(String username) throws UsernameAlreadyUsedException;
	
	/**
	 * 
	 * Check if email already exist
	 * @param email
	 * @throws EmailAlreadyUsedException
	 * @throws IOException
	 */
	public void checkEmail(String email) throws EmailAlreadyUsedException, IOException;


}
