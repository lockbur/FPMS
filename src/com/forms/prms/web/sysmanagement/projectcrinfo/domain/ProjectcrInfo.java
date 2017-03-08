package com.forms.prms.web.sysmanagement.projectcrinfo.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : YiXiaoYan <br>
 * @since : 2015-01-25 <br>
 * 电子审批信息bean
 */
import org.springframework.web.multipart.MultipartFile;

public class ProjectcrInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private MultipartFile projectcrFile;//电子审批文件
	private String        abCde;        //缩位码
	private String        projCrId;     //审批编号
	private BigDecimal    projCrAmt;    //立项总金额
	private String        createDate;   //立项日期
	private BigDecimal    projCrNum;    //立项总数量
	private String        status;       //状态 0 未立项 1 已立项
	private BigDecimal    exeAmt;       //已立项金额
	private BigDecimal    exeNum;       //已立项数量
	private String        startDate;    //查询开始日期
	private String        endDate;      //查询结束日期
	
	public MultipartFile getProjectcrFile() {
		return projectcrFile;
	}
	public void setProjectcrFile(MultipartFile projectcrFile) {
		this.projectcrFile = projectcrFile;
	}
	public String getAbCde() {
		return abCde;
	}
	public void setAbCde(String abCde) {
		this.abCde = abCde;
	}
	public String getProjCrId() {
		return projCrId;
	}
	public void setProjCrId(String projCrId) {
		this.projCrId = projCrId;
	}
	public BigDecimal getProjCrAmt() {
		return projCrAmt;
	}
	public void setProjCrAmt(BigDecimal projCrAmt) {
		this.projCrAmt = projCrAmt;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getProjCrNum() {
		return projCrNum;
	}
	public void setProjCrNum(BigDecimal projCrNum) {
		this.projCrNum = projCrNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getExeAmt() {
		return exeAmt;
	}
	public void setExeAmt(BigDecimal exeAmt) {
		this.exeAmt = exeAmt;
	}
	public BigDecimal getExeNum() {
		return exeNum;
	}
	public void setExeNum(BigDecimal exeNum) {
		this.exeNum = exeNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
