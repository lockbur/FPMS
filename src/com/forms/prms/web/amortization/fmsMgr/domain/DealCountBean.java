package com.forms.prms.web.amortization.fmsMgr.domain;

import java.io.Serializable;

public class DealCountBean implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private int count11; //11文件待处理数量(员工信息)
	
	private int count12; //12文件待处理数量(机构信息)
	
	private int count13; //13文件待处理数量(供应商信息)
	
	private int count21; //21文件待处理数量(AP发票信息)
	
	private int count22; //22文件待处理数量(AP付款信息)
	
	private int count23; //23文件待处理数量(GL预提信息)
	
	private int count25; //25文件待处理数量(采购订单)

	private int count31; //31文件待处理数量(应付发票)
	
	private int count32; //32文件待处理数量(预付款核销)
	
	private int count33; //33文件待处理数量(总账凭证)
	
	private int count34; //34文件待处理数量(采购订单)
	
	private String sucFile;

	public int getCount11() {
		return count11;
	}

	public void setCount11(int count11) {
		this.count11 = count11;
	}

	public int getCount12() {
		return count12;
	}

	public void setCount12(int count12) {
		this.count12 = count12;
	}

	public int getCount13() {
		return count13;
	}

	public void setCount13(int count13) {
		this.count13 = count13;
	}

	public int getCount21() {
		return count21;
	}

	public void setCount21(int count21) {
		this.count21 = count21;
	}

	public int getCount22() {
		return count22;
	}

	public void setCount22(int count22) {
		this.count22 = count22;
	}

	public int getCount23() {
		return count23;
	}

	public void setCount23(int count23) {
		this.count23 = count23;
	}

	public int getCount25() {
		return count25;
	}

	public void setCount25(int count25) {
		this.count25 = count25;
	}

	public int getCount31() {
		return count31;
	}

	public void setCount31(int count31) {
		this.count31 = count31;
	}

	public int getCount32() {
		return count32;
	}

	public void setCount32(int count32) {
		this.count32 = count32;
	}

	public int getCount33() {
		return count33;
	}

	public void setCount33(int count33) {
		this.count33 = count33;
	}

	public int getCount34() {
		return count34;
	}

	public void setCount34(int count34) {
		this.count34 = count34;
	}

	public String getSucFile() {
		return sucFile;
	}

	public void setSucFile(String sucFile) {
		this.sucFile = sucFile;
	}
	
}
