package com.forms.prms.tool.exceltool.loadthread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.dealexceldata.importdata.ImportExcelThread;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.tool.thread.ImportThreadPool;
import com.forms.prms.tool.thread.ThreadUtil;

public class ExcelImportDealThread implements Runnable {
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
				ThreadUtil.waitExcelImportDealThread();
			}
		}
	}
	
	/**
	 * 处理过程：每次从数据库取出10笔数据(可能各个节点取出相同数据)
	 * 			--->按列表分别处理，检查每笔数据是否更改
	 * 			--->如果数据未更改则修改状态，将数据放入处理线程,否则就读取下一笔数据
	 * @return
	 */
	private synchronized boolean process(){
		CommonLogger.info(">>>>>>>>>>处理线程被唤醒<<<<<<<<<<<");
		List<CommonExcelDealBean> list = CommonExcelDealService.getInstance().getLoadList();
		String[] configIds;
		if(list.size()>0){
			for(CommonExcelDealBean loadBean: list){
				
				if(CommonExcelDealService.getInstance().checkedStatusAndUpdateLoadStatus(loadBean.getTaskId())){
					continue;
				}
				
				configIds = loadBean.getConfigId().split(",");
				for(int i=0; i<configIds.length; i++){
					CommonLogger.info(">>>>>>>>>>任务号【"+loadBean.getTaskId()+"】,configId:【"+configIds[i]+"】正在加入线程池<<<<<<<<<<<");
				}
//				ImportThreadPool.getInstance().put(new ExcelImportInter(loadBean));
				//重新写线程(excel改为txt导入(处理大数据))
				ImportThreadPool.getInstance().put(new ImportExcelThread(loadBean));
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
		CommonLogger.info("正在导入销毁线程............");
		ThreadUtil.notifyExcelImportDealThread();
	}
}
