package com.forms.prms.web.contract.initiate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.forms.prms.web.contract.initiate.dao.ContractInitiateDAO;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.CntFee;
import com.forms.prms.web.contract.initiate.domain.ContractBean;
import com.forms.prms.web.contract.initiate.domain.ContractInitate;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.HistoryContract;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.Tenancy;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class ContractInitiateService {
	@Autowired
	private ContractInitiateDAO dao;
	@Autowired
	private WaterBookService wService;
	@Autowired
	private ProjectMgrService proService;
	@Autowired
	private ContractCommonService ccService;
	@Autowired
	private SysWarnCountService sysWarnCountService;

	/**
	 * 获取新的合同Id
	 * 
	 * @return
	 */
	public String getNewContractId(ContractInitate bean) {
		return dao.getNewContractId(bean);
	}

	/**
	 * 新增合同
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean add(ContractInitate bean) {
		boolean addSuc = true;
		// 一级分行机构号
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		// 责任中心
		bean.setCreateDept(WebHelp.getLoginUser().getDutyCode());
		// 付款责任中心 录入时为发起人责任中心，接收移交后更新
		bean.setPayDutyCode(bean.getPayDutyCode());
		// 币别 默认为CNY
		bean.setCurrency("CNY");
		// 合同状态 合同待复核:50
		bean.setDataFlag("50");
		// 是否预提待摊 1-否
		bean.setIsPrepaidProvision("1");
		if ("1".equals(bean.getCntType()) && ("0".equals(bean.getFeeType()) || "1".equals(bean.getFeeType()))) {
			// 是预提待摊 0-是
			bean.setIsPrepaidProvision("0");
			// 合同类型为费用类，费用子类型为前两类的默认为非订单
			bean.setIsOrder("1");
		}

		BigDecimal maxVersion = getMaxVersionNo("TD_CNT_LOG", bean.getCntNum(), ""); // 新增的历史版本号为1

		// 电子审批
		if ("1".equals(bean.getLxlx())) {
			// 新增电子审批列表
			addSuc = addSuc && addCntDzsp(bean, maxVersion);
		}

		// 合同类型
		if ("0".equals(bean.getCntType())) {
			// 资产类
			bean.setFeeType("");
			bean.setFeeSubType("");
			bean.setFeeStartDate("");
			bean.setFeeEndDate("");
			bean.setFeeAmt(null);
			bean.setFeePenalty(null);
		} else if ("1".equals(bean.getCntType())) {
			// 费用类
			// 费用类型
			if ("0".equals(bean.getFeeType())) {
				// 金额固定、受益期固定
				bean.setFeeAmt(null);
				bean.setFeePenalty(null);

				// 费用子类型
				if ("0".equals(bean.getFeeSubType())) {
					// 普通费用类型

				} else if ("1".equals(bean.getFeeSubType())) {
					// 房屋租赁类型
					// 递增费用信息
					addSuc = addSuc && addTenancyDz(bean, maxVersion);
					addSuc = addSuc && addTenancy(bean, maxVersion);
				}
			} else if ("1".equals(bean.getFeeType())) {
				// 受益期固定、合同金额不固定
				bean.setFeeSubType("");
			} else if ("2".equals(bean.getFeeType())) {
				// 其他
				bean.setFeeSubType("");
				bean.setFeeStartDate("");
				bean.setFeeEndDate("");
				bean.setFeeAmt(null);
				bean.setFeePenalty(null);
			} /*
			 * else if (bean.getFeeType().equals("3")) { // 宣传费
			 * bean.setFeeSubType(""); bean.setFeeStartDate("");
			 * bean.setFeeEndDate(""); bean.setFeeAmt(new BigDecimal(0));
			 * bean.setFeePenalty(new BigDecimal(0)); }
			 */
		}

		if ("3".equals(bean.getPayTerm())) {
			// 分期付款
			addSuc = addSuc && addStageInfo(bean, maxVersion);
		} else {
			bean.setStageType("");
		}

		if ("0".equals(bean.getIsSpec())) {
			// 专项包
			bean.setIsProvinceBuy("");
		}

		// 新增物料信息
		addSuc = addSuc && addCntDevice(bean, maxVersion);

		bean.setVersionNo(maxVersion);// 历史版本号
		bean.setOperType("0");// 操作类型为新增
		bean.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		bean.setOperUser(WebHelp.getLoginUser().getUserId());
		bean.setOperMemo("");
		wService.insert(bean.getCntNum(), BusTypes.CONTRACT, OperateValues.ADD, "", "", "50"); // 新增流水信息
		CommonLogger.info("新增合同（合同编号： " + bean.getCntNum() + " ，合同金额：" + bean.getCntAmt()
				+ "），ContractInitiateService，add");
		// 合同新增完后向合同将要复核的的操作加入合体统计表中
		int addWarnCnt = 0;
		int addWarnCntNoSpec = 0;
		int addWarnCntSpecOrNoSpec = 0;
		// 如果为[专向包] 
		if ("0".equals(bean.getIsSpec())) {
			addWarnCnt = ccService.addWarnCnt(bean.getCntNum());
		}
		// 如果为[非专向包] 
		if ("1".equals(bean.getIsSpec())) {
			addWarnCntNoSpec = ccService.addWarnCntNoSpec(bean.getCntNum());
		}
		//[ 费用 + 其它]
		if ("1".equals(bean.getCntType()) && "2".equals(bean.getFeeType())) {
			addWarnCntSpecOrNoSpec = ccService.addWarnCntSpecOrNoSpec(bean.getCntNum());
		}
		// 得到之前添加到合同统计表待复核数据的归口部门集合
		List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
		// 循环执行存储过程
		for (String dutyCode : dutyCodeList) {
			sysWarnCountService.DealSysWarnCount(dutyCode, "C");
		}
		return addSuc && dao.add(bean) + dao.addCntHis(bean) == 2 ? true : false;
	}

	/**
	 * 新增电子审批
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addCntDzsp(ContractInitate bean, BigDecimal maxVersion) {
		List<DzspInfo> list = new ArrayList<DzspInfo>();
		// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_DZSP_LOG",
		// bean.getCntNum(), ""); // 新增的历史版本号为1
		for (int i = 0; i < bean.getAbcde().length; i++) {
			DzspInfo dzsp = new DzspInfo();
			dzsp.setCntNum(bean.getCntNum());
			dzsp.setAbcde(bean.getAbcde()[i]);
//			dzsp.setAbcdeAmt(bean.getAbcdeAmt()[i]);
			dzsp.setAbcdeNum(bean.getAbcdeNum()[i]);
			dzsp.setVersionNo(maxVersion);// 历史版本号
			dzsp.setOperType("0");// 操作类型为新增
			list.add(dzsp);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		// 插入正式表和历史表
		CommonLogger.info("新增合同电子审批（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addCntDzsp");
		return dao.addDZSP(map) + dao.addDZSPHis(map) == list.size() + list.size() ? true : false;
	}

	/**
	 * 测算递增后租金
	 * 
	 * @param rent
	 * @param dzlx
	 * @param dzed
	 * @return
	 */
	public BigDecimal getDzhje(BigDecimal rent, String dzlx, BigDecimal dzed, String dzdw) {
		if ("2".equals(dzdw))// 单位：百分比
		{
			if ("1".equals(dzlx))
				return dzed;
			else if ("2".equals(dzlx))
				return rent.add(rent.multiply(dzed.divide(new BigDecimal(100))));
			else if ("3".equals(dzlx))
				return rent.subtract(rent.multiply(dzed.divide(new BigDecimal(100))));
			else
				return null;
		} else if ("1".equals(dzdw))// 单位：元
		{
			if ("1".equals(dzlx))
				return dzed;
			else if ("2".equals(dzlx))
				return rent.add(dzed);
			else if ("3".equals(dzlx))
				return rent.subtract(dzed);
			else
				return null;
		}
		return null;
	}

	/**
	 * 新增费用类型
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addCntFeeType(ContractInitate bean, BigDecimal maxVersion) {
		List<CntFee> list = new ArrayList<CntFee>();
		// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_FEE_LOG",
		// bean.getCntNum(), ""); // 新增的历史版本号为1
		String frozenYyyymm = "";
		for (int i = 0; i < bean.getSubId().length; i++) {
			if (Tool.CHECK.isBlank(frozenYyyymm)) {
				if (bean.getCglFeeAmt()[i].compareTo(new BigDecimal(0)) != 0) {
					frozenYyyymm = bean.getFeeYyyymm()[i];
				}
			}
			CntFee cntFee = new CntFee();
			cntFee.setCntNum(bean.getCntNum());
			cntFee.setSubId(bean.getSubId()[i]);
			cntFee.setFeeYyyymm(bean.getFeeYyyymm()[i]);
			cntFee.setCglCalAmt(bean.getCglCalAmt()[i]);
			cntFee.setCglFeeAmt(bean.getCglFeeAmt()[i]);
			cntFee.setVersionNo(maxVersion);// 历史版本号
			cntFee.setOperType("0");// 操作类型为新增
			list.add(cntFee);
		}
		// 更新合同表的FROZEN_YYYYMM
		CommonLogger.info("更新合同的冻结年月（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addCntFeeType");
		int m = dao.updateCntFrozenYyyymm(bean.getCntNum(), frozenYyyymm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		CommonLogger.info("新增合同费用类型（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addCntFeeType");
		// 插入正式表和历史表
		return dao.addFeeType(map) + dao.addFeeTypeHis(map) == list.size() + list.size() ? true : false;
	}

	/**
	 * 房屋租赁信息
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addTenancy(ContractInitate bean, BigDecimal maxVersion) {
		Tenancy tenancy = new Tenancy();
		tenancy.setCntNum(bean.getCntNum());
		tenancy.setGlbm(bean.getGlbm());
//		tenancy.setBeginDate(bean.getBeginDate());
//		tenancy.setEndDate(bean.getEndDate());
		tenancy.setArea(bean.getArea());
		tenancy.setUnitPrice(bean.getUnitPrice());
//		tenancy.setJf(bean.getJf());
//		tenancy.setJfId(bean.getJfId());
//		tenancy.setYf(bean.getYf());
//		tenancy.setYfId(bean.getYfId());
		tenancy.setRemark(bean.getRemark());
		tenancy.setHouseKindId(bean.getHouseKindId());
		tenancy.setRent(bean.getRent());
		tenancy.setWdjg(bean.getWdjg());
		tenancy.setGlbmId(bean.getGlbmId());
		tenancy.setWdjgId(bean.getWdjgId());
		tenancy.setWydz(bean.getWydz());
		tenancy.setWyglf(bean.getWyglf());
		tenancy.setYj(bean.getYj());
		tenancy.setAutoBankName(bean.getAutoBankName());

		// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_TENANCY_LOG",
		// bean.getCntNum(), ""); // 新增的历史版本号为1
		tenancy.setVersionNo(maxVersion);// 历史版本号
		tenancy.setOperType("0");// 操作类型为新增
		CommonLogger.info("新增合同房屋租赁信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addTenancy");
		return dao.addTenancy(tenancy) + dao.addTenancyHis(tenancy) == 2 ? true : false;
	}

	/**
	 * 新增租赁递增信息
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addTenancyDz(ContractInitate bean, BigDecimal maxVersion) {
		List<TenancyDz> list = new ArrayList<TenancyDz>();
		// BigDecimal maxVersion = getMaxVersionNo("td_cnt_tenancy_detail_log",
		// bean.getCntNum(), ""); // 新增的历史版本号为1
		if (bean.getFromDate() != null) {
			for (int i = 0; i < bean.getFromDate().length; i++) {
				TenancyDz tenancyDz = new TenancyDz();
				tenancyDz.setCntNum(bean.getCntNum());
				tenancyDz.setFromDate(bean.getFromDate()[i]);
				tenancyDz.setToDate(bean.getToDate()[i]);
				tenancyDz.setMatrCodeFz(bean.getMatrCodeFz()[i]);
				tenancyDz.setExecAmtTr(bean.getExecAmtTr()[i]);
				tenancyDz.setTaxAmtTr(bean.getTaxAmtTr()[i]);
				tenancyDz.setCntAmtTr(bean.getCntAmtTr()[i]);
				tenancyDz.setVersionNo(maxVersion);// 历史版本号
				// 计算递增后月租：递增后阶段月租在上一阶段月租基础上调整(第一阶段已租赁合同信息月租为基础)
//				if (i == 0) {
//					tenancyDz
//							.setDzhje(getDzhje(bean.getRent(), bean.getDzlx()[i], bean.getDzed()[i], bean.getDzdw()[i]));
//					if (tenancyDz.getDzhje().compareTo(new BigDecimal("0")) != 0) {
//						lastRent = tenancyDz.getDzhje();
//					}
//				} else {
//					tenancyDz.setDzhje(getDzhje(lastRent, bean.getDzlx()[i], bean.getDzed()[i], bean.getDzdw()[i]));
//					if (tenancyDz.getDzhje().compareTo(new BigDecimal("0")) != 0) {
//						lastRent = tenancyDz.getDzhje();
//					}
//				}
				list.add(tenancyDz);
			}
		} else {
			return true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		CommonLogger.info("新增合同租赁递增信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addTenancyDz");
		// 插入正式表和历史表
		return dao.addTenancyDz(map) + dao.addTenancyDzHis(map) == list.size() + list.size() ? true : false;
	}

	/**
	 * 新增分期支付的信息
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addStageInfo(ContractInitate bean, BigDecimal maxVersion) {
		boolean addSuc = true;
		if ("0".equals(bean.getStageType())) {
			// 按进度支付
			List<StageInfo> list = new ArrayList<StageInfo>();
			// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_FKJD_LOG",
			// bean.getCntNum(), ""); // 新增的历史版本号为1
			for (int i = 0; i < bean.getJdzf().length; i++) {
				StageInfo stage = new StageInfo();
				stage.setCntNum(bean.getCntNum());
				stage.setSubId(i + "");
//				stage.setJdtj(bean.getJdtj()[i]);
				stage.setJdzf(bean.getJdzf()[i]);
				stage.setJdDate(bean.getJdDate()[i]);
				stage.setVersionNo(maxVersion);// 历史版本号
				stage.setOperType("0");// 操作类型为新增
				list.add(stage);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			CommonLogger.info("新增合同分期支付的信息_进度支付（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addStageInfo");
			addSuc = addSuc && dao.addOnSchedule(map) + dao.addOnScheduleHis(map) == list.size() + list.size() ? true
					: false;
		} else if ("1".equals(bean.getStageType())) {
			// 按日期支付
			List<StageInfo> list = new ArrayList<StageInfo>();
			// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_FKRQ_LOG",
			// bean.getCntNum(), ""); // 新增的历史版本号为1
			for (int i = 0; i < bean.getRqtj().length; i++) {
				StageInfo stage = new StageInfo();
				stage.setCntNum(bean.getCntNum());
				stage.setSubId(i + "");
				stage.setRqtj(bean.getRqtj()[i]);
				stage.setRqzf(bean.getRqzf()[i]);
				stage.setVersionNo(maxVersion);// 历史版本号
				stage.setOperType("0");// 操作类型为新增
				list.add(stage);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			CommonLogger.info("新增合同分期支付的信息_日期支付（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addStageInfo");
			addSuc = addSuc && dao.addOnDate(map) + dao.addOnDateHis(map) == list.size() + list.size() ? true : false;
		} else if ("2".equals(bean.getStageType())) {
			// 按条件支付
			StageInfo stageInfo = new StageInfo();
			// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_FKTJ_LOG",
			// bean.getCntNum(), ""); // 新增的历史版本号为1
			stageInfo.setCntNum(bean.getCntNum());
			stageInfo.setDhzf(bean.getDhzf());
			stageInfo.setJszf(bean.getJszf());
			stageInfo.setYszf(bean.getYszf());
			stageInfo.setVersionNo(maxVersion);// 历史版本号
			stageInfo.setOperType("0");// 操作类型为新增
			CommonLogger.info("新增合同分期支付的信息_条件支付（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addStageInfo");
			addSuc = addSuc && dao.addOnTerm(stageInfo) + dao.addOnTermHis(stageInfo) == 2 ? true : false;
		}
		return addSuc;
	}

	/**
	 * 新增物料信息
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addCntDevice(ContractInitate bean, BigDecimal maxVersion) {
		List<CntDevice> list = new ArrayList<CntDevice>();
		// BigDecimal maxVersion = getMaxVersionNo("TD_CNT_DEVICE_LOG",
		// bean.getCntNum(), ""); // 新增的历史版本号为1
		for (int i = 0; i < bean.getProjId().length; i++) {
			CntDevice cntDevice = new CntDevice();
			cntDevice.setCntNum(bean.getCntNum());
			cntDevice.setSubId((i + 1) + "");
			cntDevice.setProjId(bean.getProjId()[i]);
			cntDevice.setFeeDept(bean.getFeeDept()[i]);
			cntDevice.setMatrCode(bean.getMatrCode()[i]);
			cntDevice.setMontCode(bean.getMontCode()[i]);
//			cntDevice.setDeviceModelName(bean.getDeviceModelName()[i]);
			if(!Tool.CHECK.isEmpty(bean.getDeviceModelName())){
				cntDevice.setDeviceModelName(bean.getDeviceModelName()[i]);
			}else{
				cntDevice.setDeviceModelName("");
			}
			cntDevice.setReference(bean.getReference()[i]);
			cntDevice.setSpecial(bean.getSpecial()[i]);
			cntDevice.setExecAmt(bean.getExecAmt()[i]);
			cntDevice.setExecNum(bean.getExecNum()[i]);
			cntDevice.setExecPrice(bean.getExecPrice()[i]);
			if(!Tool.CHECK.isEmpty(bean.getWarranty())){
				if(!Tool.CHECK.isEmpty(bean.getWarranty()[i])){
					cntDevice.setWarranty(bean.getWarranty()[i]);
				}else{
					cntDevice.setWarranty(new BigDecimal(0));
				}
			}else{
				cntDevice.setWarranty(new BigDecimal(0));
			}
			if(!Tool.CHECK.isEmpty(bean.getProductor())){
				cntDevice.setProductor(bean.getProductor()[i]);
			}else{
				cntDevice.setProductor("");
			}
			cntDevice.setDataFlag("00");
			cntDevice.setVersionNo(maxVersion);// 历史版本号
			cntDevice.setOperType("0");// 操作类型为新增
			cntDevice.setAuditMemo("");// 初始时auditMemo为空
			cntDevice.setTaxCode(bean.getTaxCode()[i]);
			cntDevice.setTaxRate(bean.getTaxRate()[i]);
			cntDevice.setDeductFlag(bean.getDeductFlag()[i]);
			if ("Y".equals(bean.getDeductFlag()[i])) {
				cntDevice.setTaxYamt(bean.getCntTrAmt()[i]);
				cntDevice.setTaxNamt(new BigDecimal(0));
			}else if ("N".equals(bean.getDeductFlag()[i])) {
				cntDevice.setTaxNamt(bean.getCntTrAmt()[i]);
				cntDevice.setTaxYamt(new BigDecimal(0));
			}
			list.add(cntDevice);
			// 生成对应项目的冻结金额
			if (!proService.newCnt(bean.getProjId()[i], bean.getExecAmt()[i],cntDevice.getTaxNamt())) {
				return false;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		CommonLogger.info("新增合同物料信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addCntDevice");
		return dao.addCntDevice(map) + dao.addCntDeviceHis(map) == list.size() + list.size() ? true : false;
	}

	/**
	 * 获取历史记录最大版本号，如果参数operType不为空，则查询的是除operType类外最大的版本号
	 * 
	 * @param bean
	 * @return
	 */
	public HistoryContract getHisMaxVersion(HistoryContract bean) {
		return dao.getHisMaxVersion(bean);
	}

	/**
	 * 获取除删除类型外的最高历史版本号+1
	 * 
	 * @return
	 */
	public BigDecimal getMaxVersionNo(String tableName, String cntNum, String operType) {
		CommonLogger.info("得到除了删除类型外的合同号为"+cntNum+"的最高历史版本号加1，ContractInitiateService，getHisMaxVersion");
		HistoryContract historyContract = new HistoryContract(tableName, cntNum, operType);
		historyContract = getHisMaxVersion(historyContract);
		return historyContract.getVersionNo().add(new BigDecimal(1)); // 获取除删除类型外的最高历史版本号+1
	}

	/**
	 * 获取项目预算，并校验是否超出
	 * 
	 * @param projId
	 * @return
	 */
	public String checkProjAmt(String projId, BigDecimal execAmt) {
		if (!proService.inFreezeAmtIsValid(projId, execAmt)) {
			return proService.veiwProj(projId).getProjName();
		}
		return null;
	}

	/**
	 * @methodName hasSacned desc 查看合同是否被扫描过
	 * 
	 * @param cntNum
	 * @return
	 */
	public boolean hasSacned(String cntNum) {
		CommonLogger.info("查看合同是否被扫描过（合同编号： " + cntNum+"）,ContractInitiateService,hasSacned");
		return dao.hasSacned(cntNum) > 0 ? true : false;
	}

	/**
	 * @methodName relatedCntPage desc 根据创建机构获取合同列表
	 * 
	 * @param createDept
	 * @return
	 */
	public List<ContractBean> relatedCntPage(ContractBean cnt) {
		CommonLogger.info("根据创建机构获取合同列表,ContractInitiateService,relatedCntPage");
		ContractInitiateDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.relatedCntPage(cnt);
	}

	/**
	 * 合同新增保存
	 * 
	 * @param bean
	 * @return
	 */
	public boolean save(ContractInitate bean) {
		boolean addSuc = true;
		// 一级分行机构号
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		// 责任中心
		bean.setCreateDept(WebHelp.getLoginUser().getDutyCode());
		// 付款责任中心 录入时为发起人责任中心，接收移交后更新
		bean.setPayDutyCode(bean.getPayDutyCode());
		// 币别 默认为CNY
		bean.setCurrency("CNY");
		// 合同状态 合同录入:10
		bean.setDataFlag("10");
		// 是否预提待摊 1-否
		bean.setIsPrepaidProvision("1");
		if ("1".equals(bean.getCntType()) && ("0".equals(bean.getFeeType()) || "1".equals(bean.getFeeType()))) {
			// 是预提待摊 0-是
			bean.setIsPrepaidProvision("0");
			// 合同类型为费用类，费用子类型为前两类的默认为非订单
			bean.setIsOrder("1");
		}

		BigDecimal maxVersion = getMaxVersionNo("TD_CNT_LOG", bean.getCntNum(), ""); // 新增的历史版本号为1

		// 电子审批
		if ("1".equals(bean.getLxlx())) {
			// 新增电子审批列表
			addSuc = addSuc && addCntDzsp(bean, maxVersion);
		}

		// 合同类型
		if ("0".equals(bean.getCntType())) {
			// 资产类
			bean.setFeeType("");
			bean.setFeeSubType("");
			bean.setFeeStartDate("");
			bean.setFeeEndDate("");
			bean.setFeeAmt(null);
			bean.setFeePenalty(null);
		} else if ("1".equals(bean.getCntType())) {
			// 费用类
			// 费用类型
			if ("0".equals(bean.getFeeType())) {
				// 金额固定、受益期固定
				bean.setFeeAmt(null);
				bean.setFeePenalty(null);

				// 费用子类型
				if ("0".equals(bean.getFeeSubType())) {
					// 普通费用类型

				} else if ("1".equals(bean.getFeeSubType())) {
					// 房屋租赁类型
					// 递增费用信息
					addSuc = addSuc && addTenancyDz(bean, maxVersion);
					addSuc = addSuc && addTenancy(bean, maxVersion);
				}
			} else if ("1".equals(bean.getFeeType())) {
				// 受益期固定、合同金额不固定
				bean.setFeeSubType("");
			} else if ("2".equals(bean.getFeeType())) {
				// 其他
				bean.setFeeSubType("");
				bean.setFeeStartDate("");
				bean.setFeeEndDate("");
				bean.setFeeAmt(null);
				bean.setFeePenalty(null);
			} /*
			 * else if (bean.getFeeType().equals("3")) { // 宣传费
			 * bean.setFeeSubType(""); bean.setFeeStartDate("");
			 * bean.setFeeEndDate(""); bean.setFeeAmt(new BigDecimal(0));
			 * bean.setFeePenalty(new BigDecimal(0)); }
			 */
		}

		if ("3".equals(bean.getPayTerm())) {
			// 分期付款
			addSuc = addSuc && addStageInfo(bean, maxVersion);
		} else {
			bean.setStageType("");
		}

		if ("0".equals(bean.getIsSpec())) {
			// 专项包
			bean.setIsProvinceBuy("");
		}

		// 新增物料信息
		addSuc = addSuc && addCntDevice(bean, maxVersion);

		bean.setVersionNo(maxVersion);// 历史版本号
		bean.setOperType("0");// 操作类型为新增
		bean.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		bean.setOperUser(WebHelp.getLoginUser().getUserId());
		bean.setOperMemo("");
		wService.insert(bean.getCntNum(), BusTypes.CONTRACT, OperateValues.ADD, "", "", "10"); // 新增流水信息
		CommonLogger.info("保存合同（合同编号： " + bean.getCntNum() + " ，合同金额：" + bean.getCntAmt()
				+ "），ContractInitiateService，add");
		return addSuc && dao.add(bean) + dao.addCntHis(bean) == 2 ? true : false;
	}

	public List<CntDevice> taxCodeList(CntDevice cnt) {
		CommonLogger.info("税码弹出框,ContractInitiateService,taxCodeList");
		ContractInitiateDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.taxCodeList(cnt);
	}
}
