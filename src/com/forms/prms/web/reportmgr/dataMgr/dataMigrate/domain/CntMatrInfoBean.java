package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

/**
 * Title:			CntMatrInfoBean
 * Description:		【数据迁移】-合同数据Excel中的合同物料信息Bean
 * Copyright: 		formssi
 * @author：			HQQ
 * @project：		ERP
 * @date：			2015-06-25
 * @version：		1.0
 */
public class CntMatrInfoBean {
	
	private String cntNum;				//合同号
	private String feeDeptId;			//费用承担部门编号
	private String feeDeptName;			//费用承担部门名称
	private String matrCode;			//物料编码		 	根据合同类型、费用类型、审批链(费用承担部门、物料编码和是否省行统购)选择	
	private String matrName;			//物料名称
	private String special;				//专项
	private String reference;			//参考
	private BigDecimal execNum;			//数量
	private BigDecimal execPrice;		//执行金额(单价：元)
	private String deviceModelName;		//设备型号
	private BigDecimal warranty;		//保修期(年)
	private String productor;			//制造商
	
	//6-30新增公共字段
	private String batchNo;			//批次号
	private String dataType;		//数据类型(对应具体模板具体Sheet，如0101)
	private String uploadType;		//上传类型(对应Excel模板编号)
	private String rowNo;			//Sheet中数据的行号
	private String orgId;			//导入操作用户的一级行机构编号
	
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getFeeDeptId() {
		return feeDeptId;
	}
	public void setFeeDeptId(String feeDeptId) {
		this.feeDeptId = feeDeptId;
	}
	public String getFeeDeptName() {
		return feeDeptName;
	}
	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
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
	
	//	集体福利支出
	//	S6475
	//	教育经费
	//	工会经费
	//	劳务用工基本养老保险金
	//	劳务用工基本医疗保险金
	//	劳务用工失业保险金
	public String getReference() {
		if("集体福利支出".equals(reference)){
			return "1";
		}else if("S6475".equals(reference)){
			return "2";
		}else if("教育经费".equals(reference)){
			return "3";
		}else if("工会经费".equals(reference)){
			return "4";
		}else if("劳务用工基本养老保险金".equals(reference)){
			return "5";
		}else if("劳务用工基本医疗保险金".equals(reference)){
			return "6";
		}else if("劳务用工失业保险金".equals(reference)){
			return "7";
		}else{
			return reference;
		} 
	}
	public void setReference(String reference) {
		if("1".equals(reference)){
			this.reference = "集体福利支出";
		}else if("2".equals(reference)){
			this.reference = "S6475";
		}else if("3".equals(reference)){
			this.reference = "教育经费";
		}else if("4".equals(reference)){
			this.reference = "工会经费";
		}else if("5".equals(reference)){
			this.reference = "劳务用工基本养老保险金";
		}else if("6".equals(reference)){
			this.reference = "劳务用工基本医疗保险金";
		}else if("7".equals(reference)){
			this.reference = "劳务用工失业保险金";
		}else{
			this.reference = reference;
		}
	}
	
	
	//	个人金融总部“开门红”营销费用
	//	个人金融总部第二次“开门红”营销费用
	//	夏季竞赛专项营销费用
	//	公司金融总部“开门红”营销费用
	//	燎原行动网点专项营销费用
	//	重点城市行专项营销费用
	//	代发薪专项营销费用
	public String getSpecial() {
		if("个人金融总部“开门红”营销费用".equals(special)){
			return "1";
		}else if("个人金融总部第二次“开门红”营销费用".equals(special)){
			return "2";
		}else if("夏季竞赛专项营销费用".equals(special)){
			return "3";
		}else if("公司金融总部“开门红”营销费用".equals(special)){
			return "4";
		}else if("燎原行动网点专项营销费用".equals(special)){
			return "5";
		}else if("重点城市行专项营销费用".equals(special)){
			return "6";
		}else if("代发薪专项营销费用".equals(special)){
			return "7";
		}else{
			return special;
		}
		
	}
	public void setSpecial(String special) {
		if("1".equals(special)){
			this.special = "个人金融总部“开门红”营销费用";
		}else if("2".equals(special)){
			this.special = "个人金融总部第二次“开门红”营销费用";
		}else if("3".equals(special)){
			this.special = "夏季竞赛专项营销费用";
		}else if("4".equals(special)){
			this.special = "公司金融总部“开门红”营销费用";
		}else if("5".equals(special)){
			this.special = "燎原行动网点专项营销费用";
		}else if("6".equals(special)){
			this.special = "重点城市行专项营销费用";
		}else if("7".equals(special)){
			this.special = "代发薪专项营销费用";
		}else{
			this.special = special;
		}
		
	}
	
	public BigDecimal getExecNum() {
		return execNum;
	}
	public void setExecNum(BigDecimal execNum) {
		this.execNum = execNum;
	}
	public BigDecimal getWarranty() {
		return warranty;
	}
	public void setWarranty(BigDecimal warranty) {
		this.warranty = warranty;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public BigDecimal getExecPrice() {
		return execPrice;
	}
	public void setExecPrice(BigDecimal execPrice) {
		this.execPrice = execPrice;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	
	
}
