package services;

import java.net.InetAddress;

import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import voip.Caller;
import check_fields.CallFieldsChecker;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class Call implements Service {

	private JSONObject json;

	private OnlineUser caller;
	private String callerUsername;
	private int callerHashCode;
	private int callerPort;

	private String calleeUsername;
	private InetAddress calleeIpAddress;

	private JSONObject jsonResponse = new JSONObject();
	private boolean fieldsAreOk = true;

	public Call(JSONObject json) {
		this.json = json;
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
		if (fieldsAreOk)
			call();
		return getResponse().toString();
	}

	private void readFields() {

		try {

			callerUsername = json.getString(FieldsNames.CALLER);
			callerHashCode = (int) json.get(FieldsNames.HASHCODE);
			callerPort = (int) json.get(FieldsNames.PORT);
			calleeUsername = json.getString(FieldsNames.CALLEE);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFields() {

		CallFieldsChecker callFieldsChecker = new CallFieldsChecker(
				jsonResponse);

		caller = callFieldsChecker
				.checkHashCode(callerUsername, callerHashCode);

		if (caller == null) {
			fieldsAreOk = false;
			return;
		}

		calleeIpAddress = callFieldsChecker
				.checkIfCalleeIsOnline(calleeUsername);
		if (calleeIpAddress == null) {
			fieldsAreOk = false;
			return;
		}

		if (!callFieldsChecker.checkPortLegality(callerPort)) {
			fieldsAreOk = false;
			return;
		}

	}

	private void call() {
		new Caller(caller, callerPort, calleeIpAddress);
	}

	private JSONObject getResponse() {

		try {

			if (fieldsAreOk) {
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
				return jsonResponse;
			}

			return jsonResponse;

		} catch (JSONException e) {
			// TODO
			return new JSONObject();
		}
	}
}
