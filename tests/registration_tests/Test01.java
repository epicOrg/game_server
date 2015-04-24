package registration_tests;

import static org.junit.Assert.assertEquals;
import game_server.ServerInitializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import check_fields.FieldsNames;
import services.Register;

/**
 * Test for client register message without errors. Note: I can check only if
 * the expected fields are wrapped into the JSONObject, and not their order,
 * because "An object is an unordered set of name/value pairs" (from the JSON
 * specification <http://www.json.org/>).
 * 
 * @author Noris
 * @date 2015/03/31
 *
 */

public class Test01 {

	@Test
	public void test() throws JSONException {

		// Initialize the server
		new ServerInitializer().initDataManager();

		JSONObject jsonFromClient = new JSONObject();
		jsonFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonFromClient.put(FieldsNames.USERNAME, "Schopenhauer");
		jsonFromClient.put(FieldsNames.PASSWORD, "DieWeltAlsWilleUndL0L");
		jsonFromClient.put(FieldsNames.EMAIL, "arthur@paralipomena.org");

		Register register = new Register(jsonFromClient);
		String response = register.start();

		/*
		 * Console print "fake" control: it can not work because "An object is
		 * an unordered set of name/value pairs".
		 */
		String expectedResponse = "{\"" + FieldsNames.SERVICE + "\":\""
				+ FieldsNames.REGISTER + "\",\"" + FieldsNames.NO_ERRORS
				+ "\":" + "true}";

		// assertEquals(expectedResponse, response);
		System.out.println(expectedResponse + "\n" + response);

		/*
		 * The "real" JUnit test: I check only if the expected fields are
		 * wrapped into the JSONObject.
		 */
		JSONObject jsonResponse = new JSONObject(response);

		String serviceField;
		if (jsonResponse.has(FieldsNames.SERVICE))
			serviceField = jsonResponse.getString(FieldsNames.SERVICE);
		else
			serviceField = null;

		assertEquals(FieldsNames.REGISTER, serviceField);

		Boolean noErrorsField;
		if (jsonResponse.has(FieldsNames.NO_ERRORS))
			noErrorsField = (Boolean) jsonResponse.get(FieldsNames.NO_ERRORS);
		else
			noErrorsField = false;

		assertEquals(true, noErrorsField);
	}
}
