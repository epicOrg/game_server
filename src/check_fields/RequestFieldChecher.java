package check_fields;

import java.util.ArrayList;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import services.Login;

/**
 * A common request checker for all service.
 * Before elaborating a request use this class to check if the user is authorized to enjoy server service.
 * He must be logged end the hashCode provided must match with one given by the server during login
 * 
 * @author Luca
 * @see ClientIdentityCecker
 * @see OnlineManager
 * @see OnlineUser
 * @see Login
 */

public class RequestFieldChecher {	
	
	private ClientIdentityCecker cecker;
	private static ArrayList<String> serviceNotToBeChecked;
	
	public RequestFieldChecher() {
		super();
		cecker = new ClientIdentityCecker();
	}
	/**
	 * 
	 * Checks if a request satisfy the requirements
	 * 
	 * @param request		the request to check
	 * @return				true if the request is valid, false otherwise
	 */
	public boolean checkRequest(JSONObject request){
		
		try {
			String serviceName = request.getString(FieldsNames.SERVICE);
			
			if(serviceNotToBeChecked.contains(serviceName)){
				return true;
			}else{
				
				String username = request.getString(FieldsNames.USERNAME);
				int hashCode = request.getInt(FieldsNames.HASHCODE);
				System.out.println(serviceName + " " +username + " " + hashCode + " " + 
				cecker.isUserOnline(username)  + " " + cecker.checkHashCode(username, hashCode));
				return cecker.isUserOnline(username) && 
						cecker.checkHashCode(username, hashCode);
			}			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	

	public static void setServiceNotToBeChecked(ArrayList<String> serviceNotToBeChecked) {
		RequestFieldChecher.serviceNotToBeChecked = serviceNotToBeChecked;
	}
}
