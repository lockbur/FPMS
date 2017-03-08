package com.forms.prms.web.contract.history.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.history.dao.ContractHistoryDAO;
import com.forms.prms.web.contract.history.domain.ContractHistory;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.initiate.service.ContractInitiateService;

@Service
public class ContractHistoryService {
	@Autowired
	private ContractHistoryDAO dao;
	@Autowired
	private ContractInitiateService initService;

	/**
	 * 根据查询条件获取合同历史列表
	 * 
	 * @param QueryContract
	 * @return List<QueryContract>
	 */
	public List<ContractHistory> queryList(ContractHistory con) {
		CommonLogger.info("查看合同历史列表信息，ContractHistoryService，queryList");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("dutyCode", dutyCode);

		List<ContractHistory> cntList = null;
		ContractHistoryDAO pageDao = PageUtils.getPageDao(dao);
		cntList = pageDao.queryList(paramMap);

		return cntList;
	}

	/**
	 * 根据合同编号获取合同详情
	 * 
	 * @param String
	 * @return QueryContract
	 */
	public ContractHistory getDetail(String cntNum, BigDecimal versionNo) {
		ContractHistory bean = new ContractHistory();
		bean.setCntNum(cntNum);
		bean.setVersionNo(versionNo);
		CommonLogger.info("合同历史列表中查看合同号为"+cntNum+"的详细信息，ContractHistoryService，getDetail");
		ContractHistory cnt = dao.getDetail(bean);
		// 物料信息
		cnt.setDevices(getCntProj(bean));
		// 审批类别 1：电子审批;
		if ("1".equals(cnt.getLxlx())) {
			// 电子审批信息
			cnt.setDzspInfos(getDZSPProj(bean));
		}
		// 合同类型：1-费用类 && 费用类型： 0-金额固定、受益期固定 && 费用子类型 1-房屋租赁类
		if ("1".equals(cnt.getCntType()) && "0".equals(cnt.getFeeType()) && "1".equals(cnt.getFeeSubType())) {
			// 租金信息
			cnt.setTenancies(getTcyDz(bean));
		}
		// 付款条件 ： 3-分期付款
		if ("3".equals(cnt.getPayTerm())) {
			// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			if ("0".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnSchedule(bean));
			} else if ("1".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnDate(bean));
			} else if ("2".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnTerm(bean));
			}
		}
		return cnt;
	}

	/**
	 * 根据合同编号获取项目列表
	 * 
	 * @param String
	 * @return List<CntDevice>
	 */
	public List<CntDevice> getCntProj(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的项目列表，ContractHistoryService，getCntProj");
		return dao.getCntProj(bean);
	}

	/**
	 * 根据合同编号获取电子审批信息列表
	 * 
	 * @param String
	 * @return List<DzspInfo>
	 */
	public List<DzspInfo> getDZSPProj(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的电子审批信息列表，ContractHistoryService，getDZSPProj");
		return dao.getDZSPProj(bean);
	}

	/**
	 * 根据合同编号获取租金递增条件列表
	 * 
	 * @param String
	 * @return List<TenancyDz>
	 */
	public List<TenancyDz> getTcyDz(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的租金递增条件列表，ContractHistoryService，getTcyDz");
		
		
		List<List<TenancyDz>> tenancies = new ArrayList<List<TenancyDz>>();
		
		List<String> list = dao.getMatrCode(bean.getCntNum());
		
		for(int i = 0 ;i<list.size();i++){
			bean.setMatrCodeString(list.get(i));
			List<TenancyDz> beanList = dao.getTcyDzByMatrCode(bean);
			tenancies.add(beanList);
		}
		
		
		return dao.getTcyDz(bean);
	}

	/**
	 * 根据合同编号获取按 进度分期付款列表
	 * 
	 * @param String
	 * @return List<StageInfo>
	 */
	public List<StageInfo> getOnSchedule(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的按进度分期付款列表，ContractHistoryService，getOnSchedule");
		return dao.getOnSchedule(bean);
	}

	/**
	 * 根据合同编号获取 按日期分期付款列表
	 * 
	 * @param String
	 * @return List<StageInfo>
	 */
	public List<StageInfo> getOnDate(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的按日期分期付款列表，ContractHistoryService，getOnDate");
		return dao.getOnDate(bean);
	}

	/**
	 * 根据合同编号获取按 条件分期付款列表
	 * 
	 * @param String
	 * @return List<StageInfo>
	 */
	public List<StageInfo> getOnTerm(ContractHistory bean) {
		CommonLogger.info("合同历史查看时通过合同编号为"+bean.getCntNum()+"下的按条件分期付款列表，ContractHistoryService，getOnTerm");
		return dao.getOnTerm(bean);
	}

	/**
	 * @methodName queryFeeType
	 * desc 查询受益信息页面  
	 * 
	 * @param con
	 */
	public List<CntDevice> queryFeeType(String cntNum,String versionNo) {
		CommonLogger.info("合同历史列表查看详情时得到合同号为"+cntNum+"的收益信息，ContractHistoryService，queryCntDevice");
		return dao.queryCntDevice(cntNum,versionNo);
	}

}
