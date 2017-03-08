package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * Title:		UploadDataControlInfoBean
 * Description:	上传数据信息记录Bean
 * @version 1.0
 */
public class UploadDataControlInfoBean {

	//导入批次号
	private String batchNo;
	//模板类型  01 合同 02 付款
	private String uploadType;
	//数据状态   00 待确认 01 已推送   02 已失效
	private String dataFlag;
	//一级行号
	private String org1Code;
	//操作人
	private String instOper;
	//操作时间
	private String instTime;
	//导入批次的合同数据导入任务ID
	private String taskCntId;
	//导入批次的付款数据导入任务ID
	private String taskPayId;
	//导入批次状态更新操作者(02=确认；03=删除)
	private String updtOper;
	//导入批次状态更新时间
	private String updtTime;
	
	//上传Excel文件1：合同数据
	private MultipartFile impFile1;
	//上传Excel文件1：付款数据
	private MultipartFile impFile2;
	
	//【9-9使用平台上传控件添加】
	private String impFile1Path ;				//上传文件1的保存路径
	private String impFile1OriginalName;		//上传文件1的源文件名称
	private String impFile2Path;				//上传文件2的保存路径
	private String impFile2OriginalName;		//上传文件2的源文件名称
	
	
	//校验为错误的数据行数
	private int errCount;
	
	private int errHeadCount;		//校验为错误的表头数据行数
	private int errInfoCount;		//校验为错误的主体数据行数
	
	public UploadDataControlInfoBean() {
		super();
	}
	
	public UploadDataControlInfoBean(String batchId , String instUserId , String instOrg1Code ,String cntTaskId , String payTaskId){
		this.batchNo = batchId;
		this.instOper = instUserId;
		this.org1Code = instOrg1Code;
		this.taskCntId = cntTaskId;
		this.taskPayId = payTaskId;
	}

	
	public String getImpFile1Path() {
		return impFile1Path;
	}

	public void setImpFile1Path(String impFile1Path) {
		this.impFile1Path = impFile1Path;
	}

	public String getImpFile1OriginalName() {
		return impFile1OriginalName;
	}

	public void setImpFile1OriginalName(String impFile1OriginalName) {
		this.impFile1OriginalName = impFile1OriginalName;
	}

	public String getImpFile2Path() {
		return impFile2Path;
	}

	public void setImpFile2Path(String impFile2Path) {
		this.impFile2Path = impFile2Path;
	}

	public String getImpFile2OriginalName() {
		return impFile2OriginalName;
	}

	public void setImpFile2OriginalName(String impFile2OriginalName) {
		this.impFile2OriginalName = impFile2OriginalName;
	}

	public int getErrHeadCount() {
		return errHeadCount;
	}
	public void setErrHeadCount(int errHeadCount) {
		this.errHeadCount = errHeadCount;
	}
	public int getErrInfoCount() {
		return errInfoCount;
	}
	public void setErrInfoCount(int errInfoCount) {
		this.errInfoCount = errInfoCount;
	}
	public int getErrCount() {
		return errCount;
	}
	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}
	public String getUpdtTime() {
		return updtTime;
	}
	public void setUpdtTime(String updtTime) {
		this.updtTime = updtTime;
	}
	public String getUpdtOper() {
		return updtOper;
	}
	public void setUpdtOper(String updtOper) {
		this.updtOper = updtOper;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getInstOper() {
		return instOper;
	}
	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}
	public String getInstTime() {
		return instTime;
	}
	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}
	public MultipartFile getImpFile1() {
		return impFile1;
	}
	public void setImpFile1(MultipartFile impFile1) {
		this.impFile1 = impFile1;
	}
	public MultipartFile getImpFile2() {
		return impFile2;
	}
	public void setImpFile2(MultipartFile impFile2) {
		this.impFile2 = impFile2;
	}
	public String getTaskCntId() {
		return taskCntId;
	}
	public void setTaskCntId(String taskCntId) {
		this.taskCntId = taskCntId;
	}
	public String getTaskPayId() {
		return taskPayId;
	}
	public void setTaskPayId(String taskPayId) {
		this.taskPayId = taskPayId;
	}
}
