package com.forms.prms.tool.fms.montAprv.bean;

import java.io.Serializable;

public class MontAprvBean implements Serializable {
	private String type;//对于审批链 00-是所有类型 11-专项包 ，12-省行统购，21-非专项包非省行统购
	private String org21Code;
	private String position;//查询维度 11 是物料归口部门维度
	private String isReturn;//当未维护的时候是继续下去还是不让继续了 1-继续 0-中断
	private String org1Code;
	private String org2Code;
	
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getOrg2Code() {
		return org2Code;
	}
	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

}
