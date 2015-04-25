package exceptions;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/24
 */

@SuppressWarnings("serial")
public class MissingFieldException extends Exception {

	public JSONObject getMissingFieldError() {

		JSONObject error = new JSONObject();

		try {
			error.put(FieldsNames.MISSING_FIELD, FieldsNames.INVALID);
		} catch (JSONException e) {
		}

		return error;
	}

	public JSONObject getMissingFieldName(String fieldName) {

		JSONObject error = new JSONObject();

		try {
			error.put(FieldsNames.MISSING_FIELD, fieldName);
		} catch (JSONException e) {
		}

		return error;
	}

}
