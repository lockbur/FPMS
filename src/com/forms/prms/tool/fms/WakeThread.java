package com.forms.prms.tool.fms;

import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;

@Component
public class WakeThread implements Runnable
{
	public static boolean bStop = false;
	
	public void run()
	{
		int fmsTimes = Integer.parseInt(WebHelp.getSysPara("FMS_SLEEP_TIMES"));
		long SLEEP_TIME = fmsTimes * 60 * 1000;
		while (!bStop) {
			fmsTimes = Integer.parseInt(WebHelp.getSysPara("FMS_SLEEP_TIMES"));
			SLEEP_TIME = fmsTimes * 60 * 1000;
			CommonLogger.info("定时唤醒fms扫描线程和上传文件线程....");
			try {
				//唤醒线程扫描 + 上传文件线程
			    FmsScanLock.newInstance().executeWake();
			    FmsUploadLock.newInstance().executeWake();
				Thread.sleep(SLEEP_TIME);
			} catch (Exception e) {
				CommonLogger.error("定时唤醒fms扫描线程和上传文件线程出错:", e);
			}
		}
	}
}
