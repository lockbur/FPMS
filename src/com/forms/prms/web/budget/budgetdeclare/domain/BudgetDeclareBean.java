package com.forms.prms.web.budget.budgetdeclare.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BudgetDeclareBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//TABLE TB_BUDGET_TMPLT
	private String tmpltId;			//模板ID 模板的定义是以二级行为单位的
	private String tmpltName;		//模板名称TMPLT_NAME
	private String org21Code;		//模板所属的二级行
	private String org2Name;		//模板所属二级行名称
	private String dataType;		//预算类型 0-年初预算 1-追加预算(对一个二级行而言，年初预算只能有一个，追加预算可以有多个)
	private BigDecimal dataYear;	//有效年份 yyyy
	private String dataAttr;		//预算指标 0-资产类 1-费用类
	private String dataFlag;		//数据状态 数据导入状态：A0-待提交 00-待处理(已提交待处理) 01-处理中 02-处理失败 03-处理完成（可开始预算申报）
	private String sourceFilename;	//原始文件名 文件存储在服务器上的全路径，为避免中文乱码及文件名，其中的文件名为转义后的文件名。
	private String serverFile;		//模板文件全路径
	private String instOper;
	//TABLE TB_BUDGET_TMPLT_DUTY
	private String dutyCode;		//责任中心
	private String dutyName;		//责任中心名称
	
	private String memo;
	
	private MultipartFile impFile;
	private boolean queryFlag;
	private String configId;
	
	private List<AssetBudgetDetailBean> assetList = new ArrayList<AssetBudgetDetailBean>();
	private List<FeeBudgetDetailBean> feeList = new ArrayList<FeeBudgetDetailBean>();
	
	

	public String getTmpltName() {
		return tmpltName;
	}

	public void setTmpltName(String tmpltName) {
		this.tmpltName = tmpltName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getTmpltId() {
		return tmpltId;
	}

	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getDataYear() {
		return dataYear;
	}

	public void setDataYear(BigDecimal dataYear) {
		this.dataYear = dataYear;
	}

	public String getDataAttr() {
		return dataAttr;
	}

	public void setDataAttr(String dataAttr) {
		this.dataAttr = dataAttr;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public String getServerFile() {
		return serverFile;
	}

	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}

	public MultipartFile getImpFile() {
		return impFile;
	}

	public void setImpFile(MultipartFile impFile) {
		this.impFile = impFile;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public boolean isQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(boolean queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public List<AssetBudgetDetailBean> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<AssetBudgetDetailBean> assetList) {
		this.assetList = assetList;
	}
	
	public void addAsset(AssetBudgetDetailBean bean) {
		this.assetList.add(bean);
	}

	public List<FeeBudgetDetailBean> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<FeeBudgetDetailBean> feeList) {
		this.feeList = feeList;
	}

	public void addFee(FeeBudgetDetailBean bean) {
		this.feeList.add(bean);
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
