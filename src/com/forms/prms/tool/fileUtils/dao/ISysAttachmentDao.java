package com.forms.prms.tool.fileUtils.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.tool.fileUtils.domain.CommonSysAttachmentsBean;

@Repository
public interface ISysAttachmentDao {
	//根据业务主键查询附件集合
	public List<CommonSysAttachmentsBean> queryAll(CommonSysAttachmentsBean bean);
	
	//根据条件获得附件ID字符串
	public List<String> queryAttachmentIds(CommonSysAttachmentsBean bean);
	
	//批量插入数据
	public void insert(Map<String,Object> map);
	
	//批量更新数据
	public void batchUpdate(Map<String,Object> map);
	
	//查询出没有转换为SWF格式文件的列表供定时器转换
	public List<CommonSysAttachmentsBean> queryListToJob(CommonSysAttachmentsBean bean);
	
	//修改转换文件的列表的IS_CHECKED_SWF字段
	public void batchUpdateIsCheckedSWF(Map<String,Object> map);
	
	//统计未转为SWF文件的总数
	public Integer countFileNum();
}
