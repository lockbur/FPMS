package com.forms.prms.web.budget.budgetplan.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： Excel导入bean的父类，保存公共信息(tmpltId等)，泛型T对应实际导入的Excel类型(资产类/费用类)
 * @author HQQ
 * @param <T>
 */
public class ExcelBean<T> {

	private String tmpltId;
	private String rowSeq;
	private String title;
	private List<T> templateInfo = new ArrayList<T>();		//对应导入Excel的处理Bean
	private String headInfos ;		//Sheet对应的表头信息
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getRowSeq() {
		return rowSeq;
	}
	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<T> getTemplateInfo() {
		return templateInfo;
	}
	public void setTemplateInfo(List<T> templateInfo) {
		this.templateInfo = templateInfo;
	}
	public String getHeadInfos() {
		return headInfos;
	}
	public void setHeadInfos(String headInfos) {
		this.headInfos = headInfos;
	}
}
