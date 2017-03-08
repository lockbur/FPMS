package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain;

import java.io.Serializable;

public class MontBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7347589306983374596L;
	private String batchNo;
	private String seq;
	private String montType;
	private String matrCode;
	private String matrName;
	private String lastMontName;
	private String lastMontProjType;
	private String montCode;
	private String montName;
	private String montProjType;
	private String memo;
	private String flag;
	private String lastMontCode;
	private String org21Code;
	private String excelNo;
	private String dataYear;
	private String montCodeHis;
	
	
	public String getMontCodeHis() {
		return montCodeHis;
	}
	public void setMontCodeHis(String montCodeHis) {
		this.montCodeHis = montCodeHis;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}
	public String getExcelNo() {
		return excelNo;
	}
	public void setExcelNo(String excelNo) {
		this.excelNo = excelNo;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getMontType() {
		return montType;
	}
	public void setMontType(String montType) {
		this.montType = montType;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
	public String getLastMontName() {
		return lastMontName;
	}
	public void setLastMontName(String lastMontName) {
		this.lastMontName = lastMontName;
	}
	public String getLastMontProjType() {
		return lastMontProjType;
	}
	public void setLastMontProjType(String lastMontProjType) {
		this.lastMontProjType = lastMontProjType;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getMontName() {
		return montName;
	}
	public void setMontName(String montName) {
		this.montName = montName;
	}
	public String getMontProjType() {
		return montProjType;
	}
	public void setMontProjType(String montProjType) {
		this.montProjType = montProjType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLastMontCode() {
		return lastMontCode;
	}
	public void setLastMontCode(String lastMontCode) {
		this.lastMontCode = lastMontCode;
	}
	
}
