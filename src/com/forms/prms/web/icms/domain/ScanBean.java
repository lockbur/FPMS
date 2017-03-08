package com.forms.prms.web.icms.domain;

public class ScanBean {
	private String id;//编号 合同号或付款单单号
	private String icmsPkuuid;//关联编号
	private String instOper;//
	private String instDate;//
	private String instTime;//
	private String serverIp;//		ICMS服务器IP
	private String serverPort;//	ICMS服务器端口
	private String bankCode;
	private String systemId;//		
	private String dataType;//		数据类型
	private String icms;
	private String org1Code;//一级机构号
	private String icmsCode;//ICMS省行号
	
	private String msProvince;//发起消息的机构对应的机构号
	private String dataProvince;//查询的业务数据对应的机构号
	
	private String icmsClassId;//ICMS的CLASSID
	
	
	public String getIcmsClassId() {
		return icmsClassId;
	}
	public void setIcmsClassId(String icmsClassId) {
		this.icmsClassId = icmsClassId;
	}
	public String getIcmsCode() {
		return icmsCode;
	}
	public void setIcmsCode(String icmsCode) {
		this.icmsCode = icmsCode;
	}
	public String getMsProvince() {
		return msProvince;
	}
	public void setMsProvince(String msProvince) {
		this.msProvince = msProvince;
	}
	public String getDataProvince() {
		return dataProvince;
	}
	public void setDataProvince(String dataProvince) {
		this.dataProvince = dataProvince;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcmsPkuuid() {
		return icmsPkuuid;
	}
	public void setIcmsPkuuid(String icmsPkuuid) {
		this.icmsPkuuid = icmsPkuuid;
	}
	public String getInstOper() {
		return instOper;
	}
	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}
	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getInstTime() {
		return instTime;
	}
	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getIcms() {
		return icms;
	}
	public void setIcms(String icms) {
		this.icms = icms;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	
}
