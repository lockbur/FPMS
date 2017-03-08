package com.forms.prms.tool.exceltool.exportthread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.tool.thread.ExportThreadPool;
import com.forms.prms.tool.thread.ThreadUtil;

public class ExcelExportDealThread implements Runnable {
	// 阻塞时的锁对象,runStatus为1表示线程在运行，为2表示线程已唤醒，为0表示在休眠
	public final static Map<String, Integer> synMap = new HashMap<String, Integer>();
	
	/**
	 * 是否需要导入
	 */
	private boolean isRun = true;

	@Override
	public void run() {
		while (isRun) {
			synMap.put("runStatus", 1); // 将线程改为运行状态

			if (this.process()) {
				continue;
			} else {
				// 休眠线程
				ThreadUtil.waitExcelExportDealThread();
			}
		}
	}
	
	private synchronized boolean process(){
		List<CommonExcelDealBean> list = CommonExcelDealService.getInstance().getExportList();
		
		if(list.size()>0){
			for(CommonExcelDealBean exportBean: list){
				if(CommonExcelDealService.getInstance().checkedStatusAndupdateExportStatus(exportBean.getTaskId())){
					continue;
				}
				//判断不同形式的导出方式
				if(exportBean.getConfigId().equals("PERPAY_EXPORT")){
					exportBean.setExportType("bean");
					ExportThreadPool.getInstance().put(new ExcelExportInter(exportBean));
				}else{
				ExportThreadPool.getInstance().put(new ExcelExportInter(exportBean));
				}
			}
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 终止线程
	 */
	public void stopRun() {
		isRun = false;
		CommonLogger.info("正在导出销毁线程............");
		ThreadUtil.notifyExcelExportDealThread();
	}
}
