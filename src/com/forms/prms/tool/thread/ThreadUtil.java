package com.forms.prms.tool.thread;

import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportDealThread;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportDealThread;

/**
 * 线程常用类
 * @author zuzeep
 *
 */
public class ThreadUtil {
	/**
	 * 线程休眠
	 * 
	 * @param synObj
	 * @param threadStatus
	 */
	public static void threadWait(Map<String, Integer> synMap) {
		try {
			synchronized (synMap) {
				if (2 != synMap.get("runStatus")) {
					synMap.put("runStatus", 0);
					synMap.wait();
				}
			}
		} catch (Exception e) {
			CommonLogger.error("ThreadUtil threadWait() Exception : " + e.getMessage(), e);
		}
	}

	/**
	 * 唤醒线程
	 * 
	 * @param synObj
	 * @param threadStatus
	 */
	public static void threadNotify(Map<String, Integer> synMap) {
		try {
			synchronized (synMap) {
				// 当线程为等待时，则唤醒
				if (0 == synMap.get("runStatus")) {
					synMap.notify();
				}
				// 将状态改为已唤醒
				synMap.put("runStatus", 2);
			}
		} catch (Exception e) {
			CommonLogger.error("ThreadUtil threadNotify() Exception : " + e.getMessage(), e);
		}
	}

	/**
	 * 休眠 导入Excel处理线程
	 */
	public static void waitExcelImportDealThread() {
		CommonLogger.info(">>>>>>>>>>>>>>>>>>>>休眠Excel导入线程<<<<<<<<<<<<<<<<<<<<");
		threadWait(ExcelImportDealThread.synMap);
	}

	/**
	 * 唤醒  导入Excel处理线程
	 */
	public static void notifyExcelImportDealThread() {
		CommonLogger.info(">>>>>>>>>>>>>>>>>>>>唤醒Excel导入线程<<<<<<<<<<<<<<<<<<<<");
		threadNotify(ExcelImportDealThread.synMap);
	}
	
	/**
	 * 休眠 导出Excel处理线程
	 */
	public static void waitExcelExportDealThread() {
		CommonLogger.info(">>>>>>>>>>>>>>>>>>>>休眠Excel导出线程<<<<<<<<<<<<<<<<<<<<");
		threadWait(ExcelExportDealThread.synMap);
	}
	
	/**
	 * 唤醒  导出Excel处理线程
	 */
	public static void notifyExcelExportDealThread() {
		CommonLogger.info(">>>>>>>>>>>>>>>>>>>>唤醒Excel导出线程<<<<<<<<<<<<<<<<<<<<");
		threadNotify(ExcelExportDealThread.synMap);
	}

}
