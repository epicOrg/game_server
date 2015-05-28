package exceptions;

import org.json.JSONException;
import org.json.JSONObject;

import fields_names.CommonFields;

/**
 * @author Noris
 * @date 2015/04/24
 */

@SuppressWarnings("serial")
public class MissingFieldException extends Exception {

	public JSONObject getMissingFieldError() {

		JSONObject error = new JSONObject();

		try {
			error.put(CommonFields.MISSING_FIELD.toString(), CommonFields.INVALID.toString());
		} catch (JSONException e) {
		}

		return error;
	}

	public JSONObject getMissingFieldName(String fieldName) {

		JSONObject error = new JSONObject();

		try {
			error.put(CommonFields.MISSING_FIELD.toString(), fieldName);
		} catch (JSONException e) {
		}

		return error;
	}

}
