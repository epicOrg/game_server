package check_fields;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestFieldChecher {	
	
	private ClientIdentityCecker cecker;
	private static ArrayList<String> serviceNotToBeChecked;
	
	public RequestFieldChecher() {
		super();
		cecker = new ClientIdentityCecker();
	}
	
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
