package com.forms.prms.web.report.domain;

import java.util.HashMap;

/**
 * 表中的一行数据
 * @author Ronald
 *
 */
public class ThisRow {

	private HashMap valueList = new HashMap();

	public void setFieldValue(String fieldName, String fieldValue) {
		if(fieldValue == null)
			valueList.put(fieldName, "");
		else
			valueList.put(fieldName, fieldValue);
	}

	public void setFieldValue(String fieldName, Integer fieldValue) {
		if(fieldValue == null)
			valueList.put(fieldName, "0");
		else
			valueList.put(fieldName, fieldValue.toString());
	}

	public void setFieldValue(String fieldName, int fieldValue) {
		valueList.put(fieldName, (new Integer(fieldValue)).toString());
	}
	
	public String getFieldValue(String fieldName) throws Exception {
		Object fieldValue = valueList.get(fieldName);
		if(fieldValue == null)
			throw new Exception("ThisRow:field " + fieldName + "'s value does not exists!");
		
		return (String) fieldValue;
	}
}
