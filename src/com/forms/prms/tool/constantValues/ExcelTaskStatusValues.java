package com.forms.prms.tool.constantValues;

public enum ExcelTaskStatusValues {
	/**
	 * 00----待处理
	 */
	WAITDEAL("00"),
	/**
	 * 01----正在处理
	 */
	ONDEAL("01"),
	/**
	 * 02----处理失败
	 */
	DEALFAIL("02"),
	/**
	 * 03----处理完成
	 */
	DEALCOMP("03");
	private ExcelTaskStatusValues(String status){
		this.status = status;
	}
	private String status = "";
	
	@Override
	public String toString(){
		return this.status;
	}

}
