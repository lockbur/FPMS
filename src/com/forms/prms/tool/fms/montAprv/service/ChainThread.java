package com.forms.prms.tool.fms.montAprv.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.prms.tool.fms.montAprv.dao.MontAprvDao;

public class ChainThread  implements Runnable{

	public static boolean chainStop = false;
	
	@Autowired
	private MontAprvService service;
	@Autowired
	private MontAprvDao dao;
	
	private String type;
	private String org21Code;
	
	
	public void setType(String type) {
		this.type = type;
	}


	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	
	public void run() {
		while (!chainStop) {
			CommonLogger.info("审批链跨年数据开始转移");
			try {
				MontAprvService.getInstance().chainTransferExecute();
			} catch (Exception e) {
				CommonLogger.error("审批链跨年数据转移出错", e);
				try {
					SpringHelp.getBean(MontAprvService.class).updateStatus(type, org21Code, "11","13");
				} catch (Exception e2) {
					e.printStackTrace();
				}
				
				CommonLogger.error("审批链跨年数据转移异常，"+e.getMessage());
			}finally{
				ChainThread thread = new ChainThread();
				Thread ApThread =  new Thread(thread);
				thread.chainStop = true;
				ApThread.stop();
			}
			CommonLogger.info("结束审批链跨年数据转移线程");
		}
	}


}
