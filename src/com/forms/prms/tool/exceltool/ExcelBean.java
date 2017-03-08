package com.forms.prms.tool.exceltool;

import java.util.ArrayList;
import java.util.List;

/**
 * author : liy_nby <br>
 * date : 2014-4-22<br>
 * 导入导出Excel的数据整合Bean<br>
 */
public class ExcelBean<U,L>{
	
	private Character handlerType;//类型
	private List<String> cellName;//表格样式存放表头列名
	private List<String> cellProperty;//表格样式存放属性 
	private List<String> sheetName;//页名集合
	private List<Integer> templateIndex;//模版页索引，为了解决Excel模版存在多个Sheet，有的是模版页，有的不是模版页

	/** !{%}表示Excel文件中所有分页都一样的数据  */
	private Object handObject;//对象

	/** ?{%}表示Excel文件中一页就一个对象的数据  */
	private List<U> uniqueObject;//对象集合
	
	/** ^{%}表示Excel文件中一页有多个对象的数据  */
	private List<L> loopObject;//对象集合
	
	private List<ExcelDesigner> designers = new ArrayList<ExcelDesigner>();//Excel样式设计器集合，由单一设计器变成设计器过滤链
	
	public List<ExcelDesigner> getDesigners() {
		return designers;
	}
	public void setDesigners(List<ExcelDesigner> designers) {
		this.designers = designers;
	}
	public Character getHandlerType() {
		return handlerType;
	}
	public void setHandlerType(Character handlerType) {
		this.handlerType = handlerType;
	}
	public List<String> getSheetName() {
		return sheetName;
	}
	public void setSheetName(List<String> sheetName) {
		this.sheetName = sheetName;
	}
	public List<String> getCellName() {
		return cellName;
	}
	public void setCellName(List<String> cellName) {
		this.cellName = cellName;
	}
	public List<String> getCellProperty() {
		return cellProperty;
	}
	public void setCellProperty(List<String> cellProperty) {
		this.cellProperty = cellProperty;
	}
	public Object getHandObject() {
		return handObject;
	}
	public void setHandObject(Object handObject) {
		this.handObject = handObject;
	}
	public List<U> getUniqueObject() {
		return uniqueObject;
	}
	public void setUniqueObject(List<U> uniqueObject) {
		this.uniqueObject = uniqueObject;
	}
	public List<L> getLoopObject() {
		return loopObject;
	}
	public void setLoopObject(List<L> loopObject) {
		this.loopObject = loopObject;
	}
	public List<Integer> getTemplateIndex() {
		return templateIndex;
	}
	public void setTemplateIndex(List<Integer> templateIndex) {
		this.templateIndex = templateIndex;
	}
}
