package com.forms.prms.tool.fileUtils.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.dao.ISysAttachmentDao;
import com.forms.prms.tool.fileUtils.domain.CommonSysAttachmentsBean;

@Service
public class SysAttachmentService {
	@Autowired
	private ISysAttachmentDao iSysAttachmentDao;
	
	/**
	 * 根据业务表主键查询它的所有附件集合
	 * @param busiId 业务表主键
	 * @param isDeleted 是否保留已删除的数据
	 * @return
	 */
	public List<CommonSysAttachmentsBean> queryAll(String busiId,boolean isDeleted){
		if(StringUtils.isBlank(busiId)){
			return null;
		}
		CommonSysAttachmentsBean bean = new CommonSysAttachmentsBean();
		bean.setBusiId(busiId);
		if(!isDeleted){
			bean.setIsDelete("0");
		}
		return iSysAttachmentDao.queryAll(bean);
	}
	
	/**
	 * 根据业务表主键与附件类型查询它的附件集合
	 * @param busiId  业务表主键
	 * @param fileType 附件类型
	 * @param isDeleted 是否保留已删除的数据
	 * @return
	 */
	public List<CommonSysAttachmentsBean> queryListByType(String busiId,String fileType,boolean isDeleted){
		if(StringUtils.isBlank(busiId)){
			return null;
		}
		CommonSysAttachmentsBean bean = new CommonSysAttachmentsBean();
		bean.setBusiId(busiId);
		bean.setAttachmentType(fileType);
		if(!isDeleted){
			bean.setIsDelete("0");
		}
		return iSysAttachmentDao.queryAll(bean);
	}
	
	/**
	 * 根据条件获得附件ID字符串
	 * @param busiId
	 * @param fileType
	 * @return
	 */
	public List<String> queryAttachmentIds(String busiId,String fileType){
		CommonSysAttachmentsBean bean = new CommonSysAttachmentsBean();
		bean.setBusiId(busiId);
		bean.setAttachmentType(fileType);
		bean.setIsDelete("0");
		return iSysAttachmentDao.queryAttachmentIds(bean);
	}
	
	/**
	 * 保存附件
	 * @param busiId  业务表主键
	 * @param fileType 附件类型
	 * @param filePath 附件路径
	 * @param fileName 附件真实名称
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveAttachments(String busiId,String fileType,String filePath,String fileName){
		if(!Tool.CHECK.isEmpty(filePath)){
			Map<String,Object> map = new HashMap<String,Object>();
			//待插入的附件集合
			List<CommonSysAttachmentsBean> dataList = new ArrayList<CommonSysAttachmentsBean>();
			//已经存在表中的附件集合
			List<String> passIdStr = this.queryAttachmentIds(busiId, fileType);
			if(Tool.CHECK.isEmpty(passIdStr)){
				String[] filePathArr = filePath.split(",");
				String[] fileNameArr = fileName.split(",");
				for(int i=0;i<filePathArr.length;i++){
					CommonSysAttachmentsBean bean = new CommonSysAttachmentsBean();
					bean.setFilePath(filePathArr[i].trim());
					bean.setAttachmentType(fileType);
					bean.setOrigalFilename(i<fileNameArr.length?fileNameArr[i].trim():"");
					bean.setUploadUserId(WebHelp.getLoginUser().getUserId());
					bean.setUploadDate(Tool.DATE.getDate());
					bean.setUploadTime(Tool.DATE.getTime());
					bean.setIsDelete("0");
					bean.setBusiId(busiId);
					dataList.add(bean);
				}
			}else{
				//先将已删除的附件改为删除
				Map<String,Object> tempMap = new HashMap<String,Object>();
				List<String> noPassIdStr = new ArrayList<String>();
				String[] filePathArr = filePath.split(",");
				String[] fileNameArr = fileName.split(",");
				for(int i=0;i<filePathArr.length;i++){
					String tempStr = filePathArr[i].trim();
					if(tempStr.contains("&&")){
						noPassIdStr.add(tempStr.split("&&")[1]);
					}else{
						CommonSysAttachmentsBean bean = new CommonSysAttachmentsBean();
						bean.setFilePath(filePathArr[i].trim());
						bean.setAttachmentType(fileType);
						bean.setOrigalFilename(i<fileNameArr.length?fileNameArr[i].trim():"");
						bean.setUploadUserId(WebHelp.getLoginUser().getUserId());
						bean.setUploadDate(Tool.DATE.getDate());
						bean.setUploadTime(Tool.DATE.getTime());
						bean.setBusiId(busiId);
						bean.setIsDelete("0");
						dataList.add(bean);
					}
				}
				if(!Tool.CHECK.isEmpty(noPassIdStr)){
					tempMap.put("busiId", busiId);
					tempMap.put("attachmentType",fileType);
					tempMap.put("passAttachIdStr", passIdStr);
					tempMap.put("noPassIdStr",noPassIdStr);
					iSysAttachmentDao.batchUpdate(tempMap);
				}else{
					//如果把所有的全部删除完了
					tempMap.put("busiId", busiId);
					tempMap.put("attachmentType",fileType);
					iSysAttachmentDao.batchUpdate(tempMap);
				}
			}
			if(!Tool.CHECK.isEmpty(dataList)){
				map.put("dataList", dataList);
				iSysAttachmentDao.insert(map);
			}
		}else{
			//如果把所有的全部删除完了
			Map<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("busiId", busiId);
			tempMap.put("attachmentType",fileType);
			iSysAttachmentDao.batchUpdate(tempMap);
		}
	}
	
	/**
	 * 查询出没有转换为SWF格式文件的附件列表
	 * @param bean
	 * @return
	 */
	public List<CommonSysAttachmentsBean> queryListToJob(CommonSysAttachmentsBean bean){
		return iSysAttachmentDao.queryListToJob(bean);
	}
	
	/**
	 * 将已转换的附件的IS_CHECKED_SWF字段修改为1
	 * @param map
	 */
	public void batchUpdateIsCheckedSWF(Map<String,Object> map){
		iSysAttachmentDao.batchUpdateIsCheckedSWF(map);
	}
	
	/**
	 * 定时器的执行转换文件方法
	 */
	@Transactional(rollbackFor=Exception.class)
	public void batchOfficeFtpFileConvertSWF(){
		List<CommonSysAttachmentsBean> dataList = this.queryListToJob(new CommonSysAttachmentsBean());
		List<String> updateList = new ArrayList<String>();
		for(CommonSysAttachmentsBean bean : dataList){
			if(!Tool.CHECK.isBlank(bean.getFilePath())){
				updateList.add(bean.getAttachmentId());
				String fileType = bean.getFilePath().substring(bean.getFilePath().lastIndexOf(".")+1);
				if("zip".equalsIgnoreCase(fileType)||"rar".equalsIgnoreCase(fileType)
						||"jar".equalsIgnoreCase(fileType)){
					continue;
				}
				FileConvertSWFUtil d=new FileConvertSWFUtil(bean.getFilePath());
			    d.start();
			}
		}
		if(!Tool.CHECK.isEmpty(updateList)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("attachIdStr", updateList);
			this.batchUpdateIsCheckedSWF(map);
		}
	}
	
	/**
	 * 定时器的执行转换文件方法
	 */
	@Transactional(rollbackFor=Exception.class)
	public void batchOfficeConvertSWF(){
		List<CommonSysAttachmentsBean> dataList = this.queryListToJob(new CommonSysAttachmentsBean());
		List<String> updateList = new ArrayList<String>();
		for(CommonSysAttachmentsBean bean : dataList){
			if(!Tool.CHECK.isBlank(bean.getFilePath())){
				updateList.add(bean.getAttachmentId());
				String fileType = bean.getFilePath().substring(bean.getFilePath().lastIndexOf(".")+1);
				if("zip".equalsIgnoreCase(fileType)||"rar".equalsIgnoreCase(fileType)
						||"jar".equalsIgnoreCase(fileType)){
					continue;
				}
				FileConvertSWFUtil d=new FileConvertSWFUtil(bean.getFilePath());
			    d.start();
			}
		}
		if(!Tool.CHECK.isEmpty(updateList)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("attachIdStr", updateList);
			this.batchUpdateIsCheckedSWF(map);
		}
	}
	
	/**
	 * 统计未转换为SWF文件的数量
	 * @return
	 */
	public int countFileNum(){
		return iSysAttachmentDao.countFileNum();
	}
	
}
