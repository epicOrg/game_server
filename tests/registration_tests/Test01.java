package registration_tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import services.Register;
import check_fields.FieldsNames;

/**
 * Test for client register message without errors.
 * [ TEST PASSED ]
 * 
 * @author	Noris
 * @since	2015/03/31
 *
 */

public class Test01 {

	@Test
	public void test() throws JSONException {
		
		JSONObject jsonFromClient = new JSONObject();
		jsonFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonFromClient.put(FieldsNames.USERNAME, "EpicOrg");
		jsonFromClient.put(FieldsNames.PASSWORD, "pass12345678");
		jsonFromClient.put(FieldsNames.EMAIL, "example@epic.org");
		
		Register register = new Register(jsonFromClient);
		String response = register.start();
		
		String expectedResponse = "{\"service\":\"register\",\"noErrors\":true}";
		System.out.println(expectedResponse + "\n" + response);
		
		assertEquals(expectedResponse, response);

	}

}
