package com.forms.prms.web.projmanagement.projectMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectDept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String projId;// 项目编号
	private String dutyCode;// 责任中心编号

	private BigDecimal versionNo;// 版本号

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}
