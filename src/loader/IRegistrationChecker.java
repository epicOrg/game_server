package loader;

import java.io.IOException;

import exception.EmailAlreadyUsedException;
import exception.UsernameAlreadyUsedException;

/**
 * 
 * @author Gavina
 * @author Modica
 * @date 2015/04/17
 */
public interface IRegistrationChecker {
	
	/**
	 * 
	 * @param username
	 * @return true if username not exist
	 * @throws UsernameAlreadyUsedException
	 */
	public void checkUsername(String username) throws UsernameAlreadyUsedException;
	
	/**
	 * 
	 * @param email
	 * @return true if email not exist
	 * @throws EmailAlreadyUsedException
	 * @throws IOException
	 */
	public void checkEmail(String email) throws EmailAlreadyUsedException, IOException;


}
