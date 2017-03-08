package com.forms.prms.web.sysmanagement.uploadfilemanage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.uploadfilemanage.domain.UpFileBean;

/**
 * Title:		UpFileManagerDAO
 * Description:	上传文件管理的DAO层
 * Copyright: 	formssi
 * @author: 	HQQ
 * @project: 	ERP
 * @date: 		2015-03-18
 * @version: 	1.0
 */
@Repository
public interface UpFileManagerDAO {
	
	//[查询]查询文件上传信息的列表
	public List<UpFileBean> getUpFileList(UpFileBean upFile);
	
	//[查询]根据fileId查询指定的上传文件信息
	public UpFileBean getUpFileById(@Param("fileId") String fileId);

	//[新增]上传文件时调用操作方法：将上传文件的信息插入数据库表：TB_UPLOAD_FILE_MNGT
	public int saveUploadFileInfo(UpFileBean upFile);
	
	//[删除]删除表TB_UPLOAD_FILE_MNGT指定的上传文件数据
	public void deleteUpFile(String fileId);
	
	//[修改]修改更新上传文件信息(请同步更新此表TB_UPLOAD_FILE_MNGT中的上传文件信息，以及你关联的上传文件信息 )
	public int updateUpFile(UpFileBean upFile);
	
	//[查询]获取数据库时间戳
	public String getDBTimeStamp();
	
}
