package exceptions;

import messages.fields_names.CommonFields;

import org.json.JSONException;
import org.json.JSONObject;

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
