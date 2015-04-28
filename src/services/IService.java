package services;

import org.json.JSONObject;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public interface IService {

	/**
	 * @return service response message
	 */
	
	public JSONObject start();

	public void setRequest(JSONObject request);
}
