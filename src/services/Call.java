package services;

import java.net.InetAddress;

import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import voip.Caller;
import check_fields.CallChecker;
import check_fields.FieldsNames;
import exceptions.MissingFieldException;
import exceptions.UserNotOnlineException;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class Call implements IService {

	private JSONObject jsonRequest;

	private OnlineUser caller;
	private String callerUsername;
	private int callerHashCode;
	private int callerPort;

	private String calleeUsername;
	private InetAddress calleeIpAddress;

	private JSONObject jsonResponse;
	CallChecker callChecker;

	public Call() {
	}

	@Override
	public String start() {

		try {
			readFields();
		} catch (MissingFieldException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		checkFields();
		if (callChecker.noErrors())
			call();

		generateResponse();
		return jsonResponse.toString();
	}

	private void readFields() throws MissingFieldException {

		try {

			callerUsername = jsonRequest.getString(FieldsNames.CALLER);
			callerHashCode = jsonRequest.getInt(FieldsNames.HASHCODE);
			callerPort = jsonRequest.getInt(FieldsNames.PORT);
			calleeUsername = jsonRequest.getString(FieldsNames.CALLEE);

		} catch (JSONException e) {
			throw new MissingFieldException();
		}
	}

	private void checkFields() {

		callChecker.isUserOnline(callerUsername);
		callChecker.checkHashCode(callerUsername, callerHashCode);

		if (callChecker.noErrors()) {
			try {
				caller = callChecker.getOnlineUser(callerUsername);
			} catch (UserNotOnlineException e) {
				callChecker.addError();
				e.printStackTrace();
				return;
			}
		}

		callChecker.checkIfCalleeIsOnline(calleeUsername);
		if (callChecker.noErrors()) {

			try {
				OnlineUser callee = callChecker.getOnlineUser(calleeUsername);
				calleeIpAddress = callee.getIpAddress();
			} catch (UserNotOnlineException e) {
				callChecker.addError();
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		callChecker.checkPortLegality(callerPort);
	}

	private void call() {
		new Caller(caller, callerPort, calleeIpAddress);
	}

	private void generateResponse() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CALL);
			jsonResponse.put(FieldsNames.NO_ERRORS, callChecker.noErrors());

			if (!callChecker.noErrors())
				jsonResponse.put(FieldsNames.ERRORS, callChecker.getErrors());

		} catch (JSONException e) {
		}
	}

	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
		callChecker = new CallChecker();
	}

}
