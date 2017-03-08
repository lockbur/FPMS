package com.forms.prms.web.sysmanagement.montAprvBatch.apprv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain.ApprvBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;

@Component
public class ApprvThread implements Runnable {

	public static boolean bStop = false;
	
	@Autowired
	private ApprvService service;
	
	private ApprvBean bean;
	
	public void setBean(ApprvBean bean) {
		this.bean = bean;
	}
	
	
	public void run() {
		while (!bStop) {
			CommonLogger.info("开始进入数据导入导出审批线程");
			try {
				ApprvService.getInstance().execute(bean);
			} catch (Exception e) {
				CommonLogger.error("审批线程出错", e);
				//CommonLogger.error("导入导出审批异常，"+e.getMessage());
			}finally{
				ApprvThread thread = new ApprvThread();
				Thread ApThread =  new Thread(thread);
				thread.bStop = true;
				ApThread.interrupt();
			}
			CommonLogger.info("结束数据导入导出线程");
		}
	}
}
