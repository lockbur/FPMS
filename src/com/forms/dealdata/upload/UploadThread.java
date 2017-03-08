package com.forms.dealdata.upload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.FmsUploadLock;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.tool.fms.parse.domain.FileBean;
import com.forms.prms.tool.fms.parse.service.FMSService;
import com.forms.prms.tool.fms.parse.service.SendFileException;
import com.forms.prms.web.init.SystemParamManage;

@Component
public class UploadThread implements Runnable {
	
	public static boolean bStop = false;
	
	@Autowired
	private FMSService service;

	@Override
	public void run() {
		boolean wakeFlag = true;
		
		while (!bStop) 
		{
			List<UpLoadBean> uploadList = service.getUploadTask();
			if(null!=uploadList && uploadList.size()>0)
			{
				for(UpLoadBean bean : uploadList)
				{
					try 
					{
						uploadFile(bean);
					}
					catch (SendFileException e) 
					{
						e.printStackTrace();
						try {
							Thread.sleep(5*60*1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						if (wakeFlag) {
							wakeFlag = true;
							FmsUploadLock.newInstance().executeWait();
						}
					}
				}
				
			}
			else 
			{
				// 线程睡眠，等待唤醒线程唤醒
				if (wakeFlag) {
					wakeFlag = true;
					FmsUploadLock.newInstance().executeWait();
				}
			}
		}
		
	}
	
	//上传文件
	private void uploadFile(UpLoadBean bean) throws Exception 
	{
		try
		{
			CommonLogger.info("开始上传fms文件:"+bean.getUploadPath());
			FMSBean fms = FMSConfig.getFMS(bean.getTradeType()+"up");
			FileBean filebean = fms.getFile();
			String uploadPath = bean.getUploadPath();
			String path = "";
			String fileName = "";
				
			if(uploadPath.indexOf("/")>-1)
			{
				path = uploadPath.substring(0,uploadPath.lastIndexOf("/"));
				fileName = uploadPath.substring(uploadPath.lastIndexOf("/")+1);
			}
			else
			{
				path = uploadPath.substring(0,uploadPath.lastIndexOf("\\"));
				fileName = uploadPath.substring(uploadPath.lastIndexOf("\\")+1);
			}
			
			// 获取本地上传文件路径
//			String path = SystemParamManage.getInstance().getParaValue("FMS_UPLOAD_LOCAL_FOLDER");
//			// 替换上传文件名中的参数
//			String fileName = filebean.getFileName().replace("{oucode}", bean.getOuCode())
//					.replace("{date8}", bean.getTradeDate()).replace("{seq}", bean.getSeqNo())+".gz";
			FTPBean ftp = new FTPBean();
			boolean res = Tool.FTP.sendFile(ftp.getUploadHostAddr(), ftp.getUploadPort(), ftp.getUploadUserName(), ftp.getUploadPassword(), 
								ftp.getUploadFolder(bean.getTradeType()), path, fileName);
			//上传成功则更新汇总表状态为“上传成功”
			if(res){
				bean.setDataFlag(FmsValues.FMS_DOWN_FORDEAL);
				service.updateStatus(bean);
				CommonLogger.info("上传fms文件成功:"+bean.getUploadPath());
			}
			//上传文件失败，抛出异常
			else
			{
				bean.setDataFlag(FmsValues.FMS_UP_FAIL);
				service.updateStatus(bean);
				CommonLogger.error("连接ftp上传文件:"+bean.getUploadPath()+"失败！");
				throw new SendFileException("连接ftp上传文件失败！");
			}
		}
		catch(Exception e)
		{
			bean.setDataFlag(FmsValues.FMS_UP_FAIL);
			service.updateStatus(bean);
			CommonLogger.error("连接ftp上传文件:"+bean.getUploadPath()+"失败！");
			throw new SendFileException("连接ftp上传文件失败！");
		}
		
	}
}
