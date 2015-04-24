package exceptions;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/24
 */

@SuppressWarnings("serial")
public class MissingFieldException extends Exception {

	public String getMissingFieldError() {
		return "{\"" + FieldsNames.MISSING_FIELD + "\":\""
				+ FieldsNames.INVALID + "\"}";
	}

	public String getMissingFieldName(String fieldName) {
		return "{\"" + FieldsNames.MISSING_FIELD + "\":\"" + fieldName + "\"}";
	}

}
