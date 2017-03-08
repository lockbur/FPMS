package com.forms.prms.web.contract.check.service;

import java.util.HashMap;
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
import com.forms.prms.web.contract.check.dao.ContractCheckDAO;
import com.forms.prms.web.contract.check.domain.ContractCheckBean;
import com.forms.prms.web.contract.contractcommon.service.ContractCommonService;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

/**
 * author : lisj <br>
 * date : 2015-01-23<br>
 * 合同复核Service
 */
@Service
public class ContractCheckService {
	@Autowired
	private ContractCheckDAO contractCheckDAO;
	@Autowired
	private WaterBookService wService;
	@Autowired
	private ContractCommonService ccService;
	@Autowired 
	private SysWarnCountService swcService;
	/**
	 * 合同复核信息列表查询
	 * 
	 * @param constract
	 * @return
	 */
	public List<ContractCheckBean> constractList(ContractCheckBean contractCheckBean) 
	{	
		CommonLogger.info("查看待复核的合同，ContractCheckService，constractList");
//		contractCheckBean.setDataFlag("10");//合同的状态
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("con", contractCheckBean);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("instUser", WebHelp.getLoginUser().getUserId());
		ContractCheckDAO pageDao = PageUtils.getPageDao(contractCheckDAO);
		return pageDao.constractList(paramMap);
	}

	/**
	 * 合同复核信息明细查询
	 * 
	 * @param constract
	 * @return
	 */
	public ContractCheckBean contractDeail(ContractCheckBean contractCheckBean)
	{
		return contractCheckDAO.contractDeail(contractCheckBean);
	}

	/**
	 * 合同物料设备信息
	 * @param contractCheckBean
	 * @return
	 */
	public List<ContractCheckBean> deviceDeail(ContractCheckBean contractCheckBean) {
		CommonLogger.info("得到合同号为"+contractCheckBean.getCntNum()+",归口部门为"+contractCheckBean.getDutyCode()+"的所有物料信息，ContractCheckService，deviceDeail");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("con", contractCheckBean);
		map.put("dutyCode", dutyCode);
		return contractCheckDAO.deviceDeail(map);
	}

	/**
	 * 合同复核通过
	 * @param contractCheckBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean checkPass(ContractCheckBean contractCheckBean) {
		contractCheckBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		contractCheckBean.setAuditDept(WebHelp.getLoginUser().getDutyCode()+WebHelp.getLoginUser().getDutyName());
		contractCheckBean.setAuditOper(WebHelp.getLoginUser().getUserId());
		contractCheckBean.setAuditDate(Tool.DATE.getDate());
		contractCheckBean.setAuditTime(Tool.DATE.getTime());
		
		//改变物料的状态
		if(contractCheckBean.getDataFlag().equals("40")){
			contractCheckBean.setDataFlagDevice("90");
		}else{
			contractCheckBean.setDataFlagDevice("99");
		}
		//如果没有填审批意见则表示是通过，默认填写同意
		if(contractCheckBean.getAuditMemo()==null||"".equals(contractCheckBean.getAuditMemo().trim())){
			contractCheckBean.setAuditMemo("同意");
		}
		CommonLogger.info("修改（合同号：" + contractCheckBean.getCntAmt()+"）物料的状态为复核通过(99)，ContractCheckService，updateDeviceFlag");
		int n = contractCheckDAO.updateDeviceFlag(contractCheckBean);
		//删除复核物料所属归口部门在合同统计表中的数据
		contractCheckBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		ccService.delWarnCntCheckOnce(contractCheckBean);
		//重新统计该部门待复核的合同
		swcService.DealSysWarnCount(WebHelp.getLoginUser().getDutyCode(), "C");
		//复核通过时判断该合同如果有退回的物料且没有待复核的物料则改为合同录入状态
		CommonLogger.info("查看（合同号：" + contractCheckBean.getCntNum()+"）下是否有退回物料而且没有待复核的物料，ContractCheckService，have01NoHave00");
		int count=contractCheckDAO.have01NoHave00(contractCheckBean);
		if(count>0){
			contractCheckBean.setDataFlag("10");
			CommonLogger.info("修改合同编号（"+contractCheckBean.getCntNum()+"）的状态为录入(10),ContractCheckService,updateContractFlag");
			contractCheckDAO.updateContractFlag(contractCheckBean);
			wService.insert(contractCheckBean.getCntNum(), BusTypes.CONTRACT, OperateValues.CHECKBACK, contractCheckBean.getAuditMemo(), "50", "10"); // 新增流水信息
		}
		//判断该合同下的物料状态是否已全部成功则改变合同的状态
		List<ContractCheckBean> lists = contractCheckDAO.findDeviceById(contractCheckBean);
		if(Tool.CHECK.isEmpty(lists))
		{
			//为空则改变合同的状态
			contractCheckBean.setDataFlag("12");
			CommonLogger.info("修改合同编号（"+contractCheckBean.getCntNum()+"）的状态为待确认(12),ContractCheckService,updateContractFlag");
			contractCheckDAO.updateContractFlag(contractCheckBean);
			wService.insert(contractCheckBean.getCntNum(), BusTypes.CONTRACT, OperateValues.CONTRACTPASS, contractCheckBean.getAuditMemo(), "50", "12"); // 所有物料审核通过后改为合同待确认
			//合同物料复核全部通过则在合同统计表中加入数据
			ccService.addWarnCntCheck(contractCheckBean.getCntNum());
			//先得到这个待确认合同的部门
			String sureDutyCode=ccService.getSureDutyCode(contractCheckBean.getCntNum());
			//重新统计要待确认合同的部门的数据
			swcService.DealSysWarnCount(sureDutyCode, "C");
		}
		else{
			wService.insert(contractCheckBean.getCntNum(), BusTypes.CONTRACT, OperateValues.CONTRACTPASS, contractCheckBean.getAuditMemo(), "50", "50"); // 所有物料审核通过后改为合同待确认
		}
		return n>0?true:false;
	}

	/**
	 * 合同复核退回
	 * @param contractCheckBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean checkGoBack(ContractCheckBean contractCheckBean) {
		//改变物料的状态
		contractCheckBean.setDataFlagDevice("01");
		CommonLogger.info("改变合同（合同号：" + contractCheckBean.getCntAmt()+"）对应物料归口部门相同的在设备表中状态为退回(01)，ContractCheckService，updateDeviceFlag");
		int n = contractCheckDAO.updateDeviceFlag(contractCheckBean);
		//判断复核后该合同的所有物料是否都进行了复核如果都进行了复核则改变合同的状态为合同录入状态10
		CommonLogger.info("查看合同（合同号：" + contractCheckBean.getCntAmt()+"）在设备表中是否还有未复核的物料，ContractCheckService，noCheckDeviceList");
		List<ContractCheckBean> noCheckDeviceList=contractCheckDAO.noCheckDeviceList(contractCheckBean);
		if(noCheckDeviceList.size()==0){
			contractCheckBean.setDataFlag("10");
			CommonLogger.info("修改合同编号（"+contractCheckBean.getCntNum()+"）的状态为录入(10),ContractCheckService,updateContractFlag");
			contractCheckDAO.updateContractFlag(contractCheckBean);
			wService.insert(contractCheckBean.getCntNum(), BusTypes.CONTRACT, OperateValues.CHECKBACK, contractCheckBean.getAuditMemo(), "50", "10"); // 新增流水信息
		}
		else{
			wService.insert(contractCheckBean.getCntNum(), BusTypes.CONTRACT, OperateValues.CHECKBACK, contractCheckBean.getAuditMemo(), "50", "50"); // 新增流水信息
		}
		//删除复核物料所属归口部门在合同统计表中的数据
		contractCheckBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		ccService.delWarnCntCheckOnce(contractCheckBean);
		//重新统计该部门待复核的合同
		swcService.DealSysWarnCount(WebHelp.getLoginUser().getDutyCode(), "C");
		return n>0?true:false;
	}
	
	/**
	 * 去重合同物料设备信息
	 * @param contractCheckBean
	 * @return
	 */
	public List<ContractCheckBean> distinctDeviceDeail(ContractCheckBean contractCheckBean) {
		CommonLogger.info("得到合同号为"+contractCheckBean.getCntNum()+",归口部门为"+contractCheckBean.getDutyCode()+"的所有物料信息，ContractCheckService，deviceDeail");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("con", contractCheckBean);
		map.put("dutyCode", dutyCode);
		return contractCheckDAO.distinctDeviceDeail(map);
	}
}
