package com.forms.dealexceldata.exceldealtool;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ImportExcel")
public class ImportExcelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7065344096668300540L;

	@XStreamAsAttribute
	@XStreamAlias("id")
	private String id;

	@XStreamAsAttribute
	@XStreamAlias("tableName")
	private String tableName;

	@XStreamAsAttribute
	@XStreamAlias("type")
	private String type;//01监控指标 02审批链 03预算

	@XStreamAlias("TempletExcelFile")
	private String templetExcelFile;

	@XStreamAlias("DealClass")
	private String dealClass;

	@XStreamAlias("CallProc")
	private String callProc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTempletExcelFile() {
		return templetExcelFile;
	}

	public void setTempletExcelFile(String templetExcelFile) {
		this.templetExcelFile = templetExcelFile;
	}

	public String getDealClass() {
		return dealClass;
	}

	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}

	public String getCallProc() {
		return callProc;
	}

	public void setCallProc(String callProc) {
		this.callProc = callProc;
	}

}
