package services;

import java.net.InetAddress;

import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import voip.Caller;
import check_fields.CallFieldsChecker;
import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class Call implements Service {

	private JSONObject jsonRequest;

	private OnlineUser caller;
	private String callerUsername;
	private int callerHashCode;
	private int callerPort;

	private String calleeUsername;
	private InetAddress calleeIpAddress;

	private JSONObject jsonResponse = new JSONObject();
	private boolean noErrors = true;

	public Call(JSONObject jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	@Override
	public String start() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CALL);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readFields();
		checkFields();
		if (noErrors)
			call();
		
		generateResponse();
		return jsonResponse.toString();
	}

	private void readFields() {

		try {

			callerUsername = jsonRequest.getString(FieldsNames.CALLER);
			callerHashCode = jsonRequest.getInt(FieldsNames.HASHCODE);
			callerPort = jsonRequest.getInt(FieldsNames.PORT);
			calleeUsername = jsonRequest.getString(FieldsNames.CALLEE);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {
		
		JSONObject errors = new JSONObject();

		CallFieldsChecker callFieldsChecker = new CallFieldsChecker(
				errors);

		noErrors &= callFieldsChecker.isUserOnline(callerUsername);
		noErrors &= callFieldsChecker.checkHashCode(callerUsername, callerHashCode);		
		if(noErrors){
		try {
			caller = callFieldsChecker
					.getOnlineUser(callerUsername);
		} catch (UserNotOnlineException e) {
			noErrors = false;			
			e.printStackTrace();
			return;
		}
		}
		noErrors &= callFieldsChecker.checkIfCalleeIsOnline(calleeUsername);
		if(noErrors){
			
			try {
				OnlineUser callee = callFieldsChecker.getOnlineUser(calleeUsername);
				calleeIpAddress = callee.getIpAddress();
			} catch (UserNotOnlineException e) {
				noErrors = false;
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		if (!callFieldsChecker.checkPortLegality(callerPort)) {
			noErrors = false;
			return;
		}
	}

	private void call() {
		new Caller(caller, callerPort, calleeIpAddress);
	}

	private void generateResponse() {

		try {

			if (noErrors) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
			
			}


		} catch (JSONException e) {
			// TODO
		}
	}
}
