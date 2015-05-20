package services;

import org.json.JSONObject;

/**
 * 
 * General interface for server <code>Service</code>.
 * <code>Service</code> are the center of all server functionalities.
 * 
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 * @see Audio
 * @see Call
 * @see CurrentRoom
 * @see Encrypt
 * @see Game
 * @see Login
 * @see Logout
 * @see Polling
 * @see Register
 * @see RoomService
 * @see Unknown
 */

public interface IService {

	/**
	 * Elaborates client requests end responding to them according to the <code>Service</code> type.
	 * 
	 * @return service response message to send
	 */	
	public JSONObject start(JSONObject request);
}
