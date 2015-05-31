package registration_tests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import fields_names.CommonFields;
import fields_names.RegisterFields;
import fields_names.ServicesFields;
import server.ServerInitializer;
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
		new ServerInitializer().init();

		JSONObject jsonFromClient = new JSONObject();
		jsonFromClient.put(ServicesFields.SERVICE.toString(), ServicesFields.REGISTER.toString());
		jsonFromClient.put(CommonFields.USERNAME.toString(), "Schopenhauer");
		jsonFromClient.put(CommonFields.PASSWORD.toString(), "DieWeltAlsWilleUndL0L");
		jsonFromClient.put(RegisterFields.EMAIL.toString(), "arthur@paralipomena.org");

		Register register = new Register();
		String response = register.start(jsonFromClient).toString();

		/*
		 * Console print "fake" control: it can not work because "An object is
		 * an unordered set of name/value pairs".
		 */
		String expectedResponse = "{\"" + ServicesFields.SERVICE.toString() + "\":\""
				+ ServicesFields.REGISTER.toString() + "\",\"" + CommonFields.NO_ERRORS.toString()
				+ "\":" + "true}";

		// assertEquals(expectedResponse, response);
		System.out.println(expectedResponse + "\n" + response);

		/*
		 * The "real" JUnit test: I check only if the expected fields are
		 * wrapped into the JSONObject.
		 */
		JSONObject jsonResponse = new JSONObject(response);

		String serviceField;
		if (jsonResponse.has(ServicesFields.SERVICE.toString()))
			serviceField = jsonResponse.getString(ServicesFields.SERVICE.toString());
		else
			serviceField = null;

		assertEquals(ServicesFields.REGISTER.toString(), serviceField);

		Boolean noErrorsField;
		if (jsonResponse.has(CommonFields.NO_ERRORS.toString()))
			noErrorsField = (Boolean) jsonResponse.get(CommonFields.NO_ERRORS.toString());
		else
			noErrorsField = false;

		assertTrue(noErrorsField);
	}
}
