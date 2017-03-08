package com.forms.prms.web.contract.deliver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.contractcommon.service.ContractCommonService;
import com.forms.prms.web.contract.deliver.dao.DeliverDAO;
import com.forms.prms.web.contract.deliver.domain.DeliverForm;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class DeliverService {

	@Autowired
	private DeliverDAO dDao;
	@Autowired
	private WaterBookService wService;
	@Autowired
	private ContractCommonService ccService;
	@Autowired
	private SysWarnCountService  sysWarnCountService;
	

	/**
	 * 列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> list(DeliverForm form) {
		CommonLogger.info("查询可移交的合同数据，DeliverService，list");
		form.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		DeliverDAO pageDAO = PageUtils.getPageDao(dDao);
		return pageDAO.list(form);
	}

	/**
	 * 发起移交
	 * 
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int deliver(DeliverForm form) {
		CommonLogger.info("发起移交（合同号："+form.getCntNum()+"），DeliverService，deliver");
		int i = dDao.deliver(form);
		if (i > 0) {
			for(String cntNum : form.getCntNums()){
				wService.insert(cntNum, BusTypes.CONTRACT, OperateValues.STARTDELIVER, form.getWaterMemo(),"20","25");
			}
		
		//移交之前先删除这些合同归属哪个部门的信息
		ccService.delWarnCntDeliver(form);
		//重新统计移交给别的部门后这个部门合同统计数据
		sysWarnCountService.DealSysWarnCount(WebHelp.getLoginUser().getDutyCode(), "C");
		//如果合同移交成功则将这些合同号对应的新的部门插入到合同统计表中
		ccService.addWarnCntDeliver(form);
		//统计待接收合同对应部门的合同数据
		sysWarnCountService.DealSysWarnCount(form.getDeliverDutyCode(), "C");
		}
		return i;
	}

	/**
	 * 移交列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> deliverList(DeliverForm form) {
		CommonLogger.info("查询移交待接收的合同数据，DeliverService，deliverList");
		form.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		DeliverDAO pageDAO = PageUtils.getPageDao(dDao);
		return pageDAO.deliverList(form);
	}

	/**
	 * 接受移交
	 * 
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int accept(DeliverForm form) {
		if (Tool.CHECK.isBlank(form.getDeliverDutyCode())) {
			return 0;
		}
		CommonLogger.info("接受移交（合同号："+form.getCntNum()+"），DeliverService，accept");
		int i = dDao.accept(form);
		//删除接收了移交的合同在合同统计表中的数据
		ccService.delWarnCntDeliver(form);
		//重新统计该部门要处理的合同各状态的条数
		sysWarnCountService.DealSysWarnCount(WebHelp.getLoginUser().getDutyCode(), "C");
		if (i > 0) {
			// 增加流水
			for(String cntNum : form.getCntNums()){
				wService.insert(cntNum, BusTypes.CONTRACT, OperateValues.ACCEPTDELIVER, form.getWaterMemo(),"25","20");
			}
		}
		return i;
	}

	/**
	 * 拒绝移交
	 * 
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int reject(DeliverForm form) {
		CommonLogger.info("拒绝移交（合同号："+form.getCntNum()+"），DeliverService，reject");
		int i = dDao.reject(form);
		//删除接收了移交的合同在合同统计表中的数据
		ccService.delWarnCntDeliver(form);
		//重新统计该部门要处理的合同各状态的条数
		sysWarnCountService.DealSysWarnCount(WebHelp.getLoginUser().getDutyCode(), "C");
		if (i > 0) {
			// 增加流水
			for(String cntNum : form.getCntNums()){
				wService.insert(cntNum, BusTypes.CONTRACT, OperateValues.REJECTDELIVER, form.getWaterMemo(),"25","20");
			}
		}
		return i;
	}

	/**
	 * 取消列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> cancelList(DeliverForm form) {
		CommonLogger.info("查询移交可取消的合同信息，DeliverService，cancelList");
		form.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		DeliverDAO pageDAO = PageUtils.getPageDao(dDao);
		return pageDAO.cancelList(form);
	}

	/**
	 * 取消移交
	 * 
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int cancel(DeliverForm form) {
		
		//取消移交时删除警告表
		CommonLogger.info("取消移交删除首页警告信息（合同号："+form.getCntNum()+"），DeliverService，cancel");
		ccService.cancelDeliverCntInfo(form);
		List<DeliverForm> dutyS =dDao.getDutyS(form);
		if (null!= dutyS && dutyS.size()>0) {
			for (int j = 0; j < dutyS.size(); j++) {
				if (!Tool.CHECK.isEmpty(dutyS.get(j).getDeliverDutyCode())) {
					dDao.callWarnCount(dutyS.get(j).getDeliverDutyCode());
				}
			}
			
		}
		
		CommonLogger.info("取消移交（合同号："+form.getCntNum()+"），DeliverService，cancel");
		int i = dDao.cancel(form);
		if (i > 0) {
			// 增加流水
			for(String cntNum : form.getCntNums()){
				wService.insert(cntNum, BusTypes.CONTRACT, OperateValues.CANCELDELIVER, form.getWaterMemo(),"25","20");
			}
		}
		return i;
	}
}
