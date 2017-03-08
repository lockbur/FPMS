package com.forms.dealdata.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.fms.FmsScanLock;
import com.forms.prms.web.cluster.lock.service.ClusterLockService;

@Component
public class DownloadThread implements Runnable {

	public static boolean bStop = false;

	@Autowired
	private DownloadService service;

	@Autowired
	private ClusterLockService clusterLockService;

	@Override
	public void run() {
		// 第一次调用即系统启动时清空本机锁
		clusterLockService.clearLock();
		while (!bStop) {
			CommonLogger.info("FMS文件下载及年初预算冻结线程开始运行.....");
			try {
				service.dealDownload("SYSTEM", null);
			} catch (Exception e) {
				// e.printStackTrace();
				CommonLogger.error("FMS文件下载出现异常:", e);
			}
			CommonLogger.info("FMS文件下载及年初预算冻结线程结束运行。");
			// 线程睡眠，等待唤醒线程唤醒
			FmsScanLock.newInstance().executeWait();
		}
	}

}
