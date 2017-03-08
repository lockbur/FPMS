
package com.forms.prms.tool.fms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.montAprv.service.MontAprvService;
import com.forms.prms.web.cluster.lock.service.ClusterLockService;

@Component
public class FmsThread implements Runnable {

	public static boolean bStop = false;
	
	@Autowired
	private FmsGeneral fmsGeneral;
	@Autowired
	private MontAprvService service;
	
	@Autowired
	private ClusterLockService clusterLockService;
	
	public void run() 
	{
		boolean wakeFlag = true;
		boolean isStart = true;//是否系统启动
		
		if(isStart){//第一次调用即系统启动时
			clusterLockService.clearLock();
			//清空本机锁
			isStart = false;
		}
		while (!bStop) 
		{
			CommonLogger.info("开始fms文件扫描");
			try {
//				fmsGeneral.dealDownload("SYSTEM");
			} catch (Exception e) {
				CommonLogger.error("FmsThread run() Exception:", e);
			}
			//监控指标和审批链数据转移
			try {
				String curTime = Tool.DATE.getTime().substring(0, 5);
				String taskStarTime = WebHelp.getSysPara("FILE_DOWLOAD_TIME_START");
				String taskEndTime = WebHelp.getSysPara("FILE_DOWLOAD_TIME_END");
				//下面是在年初将本年的监控指标和预算 由FUT表 导入到正式表
				String date = Tool.DATE.getDateStrNO();
				if ("01".equals(date.substring(4,6)) && curTime.compareTo(taskStarTime) >= 0 && curTime.compareTo(taskEndTime)<0) {
					//一月份的时候判断 FUT表有没有数据 如果有就转移数据
//					service.executeMontAndAprv();
				}
				 	
			} catch (Exception e) {
				CommonLogger.error("FmsThread run() Exception:", e);
				//e.printStackTrace();
			}
			CommonLogger.info("结束fms文件扫描");
			//线程睡眠，等待唤醒线程唤醒
	        if (wakeFlag)
	        {
	           wakeFlag = true;
	           FmsScanLock.newInstance().executeWait();
	        }
		}
		// 进入等待状态
		FmsScanLock.newInstance().executeWait();
	}
	
	public static void stopRun()
    {
       bStop = true;
       FmsScanLock.newInstance().executeWake();
       CommonLogger.info("fms scan thread stoped!");
    }
}
