package com.forms.prms.web.sysmanagement.montindex.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MontIndexBean implements Serializable {
	private static final long serialVersionUID = 6543855914719474304L;
	private String montType;// 指标类型 资产 或费用
	private String montTypeName;// 指标类型参数值
	private String montCode;// 指标编码
	private String montName;// 指标名称
	private String matrCode;// 物料编码
	private String matrName;// 物料名称
	private String org21Code;// 指标二级行/一级行
	private String org21Name;
	private String cglCode;// 物料核算码
	private String matrs[];
	private List<String> matrLst;
	private String selectMatrs;
	private String oldMontCode;// 修改前监控指标
	private String type;// 查询物料页面返回的类型
	private String isSuperAdmin;// 是否是超级管理员
	private String org1Code;// 一级行
	private String org2Code;// 二级行
	private String userOrg2Code;//用户二级行 用于未填写二级行的情况
	private String org2Name;
	private String[] noUpMatrs;// 未维护
	private List<String> moUpMatrList;
	private String selectNoUpMatrs;
	private String isChecked;// 是否选中 1选中
	private String decomposeOrg;// 预算分解部门
	private String operType;
	private String operId;
	private String projType;// 项目类型
	private String projTypeName;// 项目类型参数值
	private String org1Name;
	private String dataYear;// 年份
	private String instOper;// 操作者ID
	private String categoryId;// 操作者ID
	private String paramName;// 操作者ID
	private String paramValue;// 操作者ID
	private String sortFlag;// 操作者ID
	private String lastMontCode;// 旧监控指标代码

	private String montCodeHis;// 历史监控指标代码

	private String[] fristMatrs;// 修改时最初的物料

	private List<String> fristMatrsList;// 修改时最初的物料集合

	private String isSelectHis;//是否选择查询历史
	private String[] cglCodeS;//页面的核算吗数组
	private List<String> cglCodeList;
	private List<String> inValidList;
	private String orgType;//1:省行 2：分行
	private String isValid;
	private String[] isValids;//失效
	
	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	
	
	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	private String seq;
	
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public List<String> getInValidList() {
		return inValidList;
	}

	public void setInValidList(List<String> inValidList) {
		this.inValidList = inValidList;
	}

 

	public String[] getIsValids() {
		return isValids;
	}

	public void setIsValids(String[] isValids) {
		this.isValids = isValids;
	}

	public List<String> getCglCodeList() {
		return cglCodeList;
	}

	public void setCglCodeList(List<String> cglCodeList) {
		this.cglCodeList = cglCodeList;
	}

	public String[] getCglCodeS() {
		return cglCodeS;
	}

	public void setCglCodeS(String[] cglCodeS) {
		this.cglCodeS = cglCodeS;
	}

	public String getIsSelectHis() {
		return isSelectHis;
	}

	public void setIsSelectHis(String isSelectHis) {
		this.isSelectHis = isSelectHis;
	}

	public String getUserOrg2Code() {
		return userOrg2Code;
	}

	public void setUserOrg2Code(String userOrg2Code) {
		this.userOrg2Code = userOrg2Code;
	}

	public String[] getFristMatrs() {
		return fristMatrs;
	}

	public void setFristMatrs(String[] fristMatrs) {
		this.fristMatrs = fristMatrs;
	}

	public List<String> getFristMatrsList() {
		return fristMatrsList;
	}

	public void setFristMatrsList(List<String> fristMatrsList) {
		this.fristMatrsList = fristMatrsList;
	}

	public String getMontCodeHis() {
		return montCodeHis;
	}

	public void setMontCodeHis(String montCodeHis) {
		this.montCodeHis = montCodeHis;
	}

	public String getLastMontCode() {
		return lastMontCode;
	}

	public void setLastMontCode(String lastMontCode) {
		this.lastMontCode = lastMontCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(String sortFlag) {
		this.sortFlag = sortFlag;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getDataYear() {
		return dataYear;
	}

	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getProjTypeName() {
		return projTypeName;
	}

	public void setProjTypeName(String projTypeName) {
		this.projTypeName = projTypeName;
	}

	public String getMontTypeName() {
		return montTypeName;
	}

	public void setMontTypeName(String montTypeName) {
		this.montTypeName = montTypeName;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getProjType() {
		return projType;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public String getMontType() {
		return montType;
	}

	public void setMontType(String montType) {
		this.montType = montType;
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

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	public String getOrg21Name() {
		return org21Name;
	}

	public void setOrg21Name(String org21Name) {
		this.org21Name = org21Name;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String[] getMatrs() {
		return matrs;
	}

	public void setMatrs(String[] matrs) {
		this.matrs = matrs;
	}

	public List<String> getMatrLst() {
		return matrLst;
	}

	public void setMatrLst(List<String> matrLst) {
		this.matrLst = matrLst;
	}

	public String getSelectMatrs() {
		return selectMatrs;
	}

	public void setSelectMatrs(String selectMatrs) {
		this.selectMatrs = selectMatrs;
	}

	public String getOldMontCode() {
		return oldMontCode;
	}

	public void setOldMontCode(String oldMontCode) {
		this.oldMontCode = oldMontCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String[] getNoUpMatrs() {
		return noUpMatrs;
	}

	public void setNoUpMatrs(String[] noUpMatrs) {
		this.noUpMatrs = noUpMatrs;
	}

	public List<String> getMoUpMatrList() {
		return moUpMatrList;
	}

	public void setMoUpMatrList(List<String> moUpMatrList) {
		this.moUpMatrList = moUpMatrList;
	}

	public String getSelectNoUpMatrs() {
		return selectNoUpMatrs;
	}

	public void setSelectNoUpMatrs(String selectNoUpMatrs) {
		this.selectNoUpMatrs = selectNoUpMatrs;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getDecomposeOrg() {
		return decomposeOrg;
	}

	public void setDecomposeOrg(String decomposeOrg) {
		this.decomposeOrg = decomposeOrg;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

}
