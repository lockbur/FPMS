package com.forms.prms.web.contract.modify.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.check.domain.ContractCheckBean;
import com.forms.prms.web.contract.contractcommon.service.ContractCommonService;
import com.forms.prms.web.contract.initiate.dao.ContractInitiateDAO;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.Tenancy;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.initiate.service.ContractInitiateService;
import com.forms.prms.web.contract.modify.dao.IContractModifyDAO;
import com.forms.prms.web.contract.modify.domain.ModifyContract;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class ContractModifyService {

	@Autowired
	private IContractModifyDAO dao;

	@Autowired
	private ContractInitiateService initService;

	@Autowired
	private ContractInitiateDAO initDao;

	@Autowired
	private WaterBookService wService;

	@Autowired
	private ProjectMgrService projService;

	@Autowired
	private ContractCommonService ccService;

	@Autowired
	private SysWarnCountService sysWarnCountService;

	/**
	 * @methodName modifyList desc 获取可修改合同列表信息
	 * 
	 * @param con
	 *            查询条件
	 */
	public List<ModifyContract> modifyList(ModifyContract con) {
		String dutyCode = WebHelp.getLoginUser().getDutyCode();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("dutyCode", dutyCode);
		CommonLogger.info("获取可修改合同列表信息,ContractModifyService,modifyList");
		List<ModifyContract> cntList = null;
		IContractModifyDAO pageDao = PageUtils.getPageDao(dao);
		cntList = pageDao.modifyList(paramMap);

		return cntList;
	}

	/**
	 * @methodName getDetail desc 获取可修改合同信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	@Transactional(rollbackFor = Exception.class)
	public ModifyContract getDetail(String cntNum) {
		CommonLogger.info("获取合同编号:（ " + cntNum+"）的详细信息,ContractModifyService,getDetail");
		ModifyContract cnt = dao.getDetail(cntNum);
		cnt.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		// 物料信息
		cnt.setDevices(getCntProj(cntNum));
		// 审批类别 1：电子审批;
		if ("1".equals(cnt.getLxlx())) {
			// 电子审批信息
			cnt.setDzspInfos(getDZSPProj(cntNum));
		}
		// 合同类型：1-费用类 && 费用类型： 0-金额固定、受益期固定 && 费用子类型 1-房屋租赁类
		if ("1".equals(cnt.getCntType())) {
			if ("0".equals(cnt.getFeeType()) && "1".equals(cnt.getFeeSubType())) {
				// 租金信息
				cnt.setTenancies(getTcyDz(cntNum));
			}
		}
		// 付款条件 ： 3-分期付款
		if ("3".equals(cnt.getPayTerm())) {
			// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			if ("0".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnSchedule(cntNum));
			} else if ("1".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnDate(cntNum));
			} else if ("2".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnTerm(cntNum));
			}
		}
		return cnt;
	}

	/**
	 * @methodName updCnt desc 修改合同信息
	 * 
	 * @param bean
	 *            合同信息
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updCnt(ModifyContract bean) throws Exception {
		CommonLogger.info("修改合同信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
		BigDecimal maxVersion = initService.getMaxVersionNo("TD_CNT_LOG", bean.getCntNum(), ""); // 最大版本号+1
		boolean updSuc = true;
		bean.setIsPrepaidProvision("1");
		// 更新预提待摊类
		if ("1".equals(bean.getCntType()) && ("0".equals(bean.getFeeType()) || "1".equals(bean.getFeeType()))) {
			// 是预提待摊 0-是
			bean.setIsPrepaidProvision("0");
			// 合同类型为费用类，费用子类型为前两类的默认为非订单
			bean.setIsOrder("1");
		}
		// 电子审批
		if ("1".equals(bean.getLxlx())) {
			// 修改电子审批列表
			updSuc = updSuc && modifyDZSPProj(bean, maxVersion);
		} else {
			CommonLogger.info("根据合同编号删除电子审批信息列表（合同编号： " + bean.getCntNum()+"）,ContractModifyService,delDZSPProj");
			dao.delDZSPProj(bean.getCntNum());
		}

		// 合同类型
		if ("0".equals(bean.getCntType())) {
			// 资产类
			bean.setFeeType("");
			bean.setFeeSubType("");
			bean.setFeeStartDate("");
			bean.setFeeEndDate("");
			bean.setFeeAmt(new BigDecimal(0));
			bean.setFeePenalty(new BigDecimal(0));

			// 删除原租赁费用递增信息
			CommonLogger.info("修改合同信息_删除原租赁费用递增信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
			dao.delTcyDz(bean.getCntNum());
			// 删除原租赁费用信息
			CommonLogger.info("修改合同信息_删除原租赁费用信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
			dao.delTenancy(bean.getCntNum());
			// 删除费用信息
			CommonLogger.info("修改合同信息_删除费用信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
			dao.delCntFee(bean.getCntNum());

		} else if ("1".equals(bean.getCntType())) {
			// 费用类
			bean = modifyFeeType(bean, maxVersion);
			updSuc = updSuc && bean.isSuc();
		}

		if ("3".equals(bean.getPayTerm())) {
			updSuc = updSuc && updStageInfo(bean, maxVersion);
		} else {
			// 删除原分期付款记录
			CommonLogger.info("修改合同信息_删除原分期付款记录（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
			dao.delOnSchedule(bean.getCntNum());
			dao.delOnDate(bean.getCntNum());
			dao.delOnTerm(bean.getCntNum());
		}

		String oldDataFlag = bean.getDataFlag();
		// 如果为录入状态的合同
		if ("10".equals(bean.getDataFlag())) {
			// 改为待复核状态
			bean.setDataFlag("50");
		}
		// 为订单退回或者确认退回的合同
		else {
			// 物料发生了改变
			if ("Y".equals(bean.getDeviceChg())) {
				// 改为待复核状态
				bean.setDataFlag("50");
			} else {
				// 直接待确认
				bean.setDataFlag("12");
			}
		}
		// 更新物料信息及对应的订单信息
		if ("40".equals(oldDataFlag)||"0".equals(bean.getIsOrder())) {
			// 订单退回
			updSuc = updSuc && updOrderBackDevice(bean, maxVersion,oldDataFlag);
			CommonLogger.info("修改合同信息_更新对应的订单信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updCnt");
			dao.updateOrderInfo(bean);
		} else {
			// 合同录入
			updSuc = updSuc && updDevice(bean, maxVersion,oldDataFlag);
		}

		bean.setVersionNo(maxVersion);// 历史版本号
		bean.setOperType("3");// 操作类型为新增
		bean.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		bean.setOperUser(WebHelp.getLoginUser().getUserId());
		bean.setOperMemo("");
		if (bean.getIsSpec().equals("0")) {
			// 专项包
			bean.setIsProvinceBuy("");
		}
		updSuc = updSuc && dao.updCnt(bean) + initDao.addCntHis(bean) == 2 ? true : false;
		CommonLogger.info("修改合同（合同编号： " + bean.getCntNum() + " ， 版本号:" + maxVersion + "），ContractModifyService，updCnt");
		if (updSuc) {
			wService.insert(bean.getCntNum(), BusTypes.CONTRACT, OperateValues.UPDATE, "修改提交", oldDataFlag,
					bean.getDataFlag());
		}

		if (!updSuc) {
			throw new Exception("修改失败！");
		}
		return updSuc;
	}

	/**
	 * @methodName getCntProj desc 获取物料信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getCntProj(String cntNum) {
		CommonLogger.info("获取物料信息（合同编号： " + cntNum+"）,ContractModifyService,getCntProj");
		return dao.getCntProj(cntNum);
	}

	/**
	 * @methodName getDZSPProj desc 根据合同编号获取电子审批信息列表
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<DzspInfo> getDZSPProj(String cntNum) {
		CommonLogger.info("根据合同编号获取电子审批信息列表（合同编号： " + cntNum+"）,ContractModifyService,getDZSPProj");
		return dao.getDZSPProj(cntNum);
	}

	/**
	 * @methodName modifyDZSPProj desc 根据合同编号更新电子审批信息列表
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新历史版本
	 */
	public boolean modifyDZSPProj(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("根据合同编号更新电子审批信息列表（合同编号： " + bean.getCntNum()+"）,ContractModifyService,modifyDZSPProj");
		dao.delDZSPProj(bean.getCntNum());
		return initService.addCntDzsp(bean, maxVersion);
	}

	/**
	 * @methodName getTcyDz desc 获取租金递增信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<TenancyDz> getTcyDz(String cntNum) {
		CommonLogger.info("获取租金递增信息（合同编号： " + cntNum+"）,ContractModifyService,getTcyDz");
		
		return dao.getTcyDz(cntNum);
	}
	
	/**
	 * @methodName getTcyDzList desc 获取租金递增信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<Map<String, Object>> getTcyDzList(String cntNum) {
		CommonLogger.info("获取租金递增信息（合同编号： " + cntNum+"）,ContractModifyService,getTcyDzList");
		
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>(); 
		
		
		
		List<TenancyDz> list = dao.getMatrCode(cntNum);
		
		for(int i = 0 ;i<list.size();i++){
			List<TenancyDz> beanList = dao.getTcyDzByMatrCode(list.get(i).getMatrCodeFz(),cntNum);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", beanList);
			map.put("matrName", list.get(i).getMatrNameFz());
			tempList.add(map);
		}
		
		return tempList;
	}
	public List<Map<String, Object>> getTcyDzCheckList(List<ContractCheckBean> deviceInfoList,String cntNum) {
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		CommonLogger.info("获取租金递增信息（合同编号： " + cntNum+"）,归口部门为"+dutyCode+"的所有物料信息,ContractModifyService,getTcyDzList");
		
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>(); 
		
		for(int i = 0 ;i<deviceInfoList.size();i++){
			List<TenancyDz> beanList = dao.getTcyDzByMatrCode(deviceInfoList.get(i).getMatrCode(),cntNum);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", beanList);
			map.put("matrName", deviceInfoList.get(i).getMatrName());
			tempList.add(map);
		}
		
		return tempList;
	}

	/**
	 * @methodName getOnSchedule desc 获取按进度付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnSchedule(String cntNum) {
		CommonLogger.info("获取按进度付款信息（合同编号： " + cntNum+"）,ContractInitiateService,getOnSchedule");
		return dao.getOnSchedule(cntNum);
	}

	/**
	 * @methodName getOnDate desc 获取按日期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnDate(String cntNum) {
		CommonLogger.info("获取按日期付款信息（合同编号： " + cntNum+"）,ContractInitiateService,getOnDate");
		return dao.getOnDate(cntNum);
	}

	/**
	 * @methodName getOnTerm desc 获取按条件分期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnTerm(String cntNum) {
		CommonLogger.info("获取按条件分期付款信息（合同编号： " + cntNum+"）,ContractInitiateService,getOnTerm");
		return dao.getOnTerm(cntNum);
	}

	/**
	 * @methodName updStageInfo desc 根据合同信息更新租金递增信息
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updStageInfo(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("根据合同信息更新租金递增信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updStageInfo");
		boolean isSus = true;
		boolean exist = getOnTerm(bean.getCntNum()).size() > 0 ? true : false;
		// 删除原分期付款记录
		CommonLogger.info("根据合同删除原分期付款记录（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updStageInfo");
		dao.delOnSchedule(bean.getCntNum());
		dao.delOnDate(bean.getCntNum());

		if (bean.getStageType().equals("2")) {
			// 按条件支付
			StageInfo stageInfo = new StageInfo();
			// BigDecimal maxVersion =
			// initService.getMaxVersionNo("TD_CNT_FKTJ_LOG", bean.getCntNum(),
			// ""); // 最大版本号+1
			stageInfo.setCntNum(bean.getCntNum());
			stageInfo.setDhzf(bean.getDhzf());
			stageInfo.setJszf(bean.getJszf());
			stageInfo.setYszf(bean.getYszf());
			stageInfo.setVersionNo(maxVersion);// 历史版本号
			stageInfo.setOperType("2");// 操作类型为修改
			if (exist) {
				isSus = isSus && dao.updOnTerm(stageInfo) + initDao.addOnTermHis(stageInfo) == 2 ? true : false;
			}
		} else {
			// 删除原分期付款记录
			dao.delOnTerm(bean.getCntNum());
		}
		// 分期付款
		if (!bean.getStageType().equals("2") || !exist) {
			isSus = isSus && initService.addStageInfo(bean, maxVersion);
		}
		return isSus;
	}

	/**
	 * @methodName updDevice desc 根据合同信息更新物料信息
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updDevice(ModifyContract bean, BigDecimal maxVersion,String oldDataFlag) {
		CommonLogger.info("根据合同信息更新物料信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updDevice");
		if ("Y".equals(bean.getDeviceChg())) {
			// 修改前的项目及执行金额
			List<CntDevice> oldCntProjAmtList = getSumExecAmt(bean.getCntNum());
			CommonLogger.info("更新项目冻结金额,ContractInitiateService,updDevice");
			for (CntDevice dev : oldCntProjAmtList) {
				dao.updateFreezeAmt(dev.getExecAmt(), dev.getProjId(),dev.getTaxNamt());
			}
		}
		// 删除原物料信息
		CommonLogger.info("根据合同删除原物料信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updDevice");
		dao.delCntDevice(bean.getCntNum());
		// 删除之前待复核合同统计表的数据，先得到这个合同号对应的归口部门集合
		List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
		ccService.delWarnCntCheck(bean.getCntNum());
		// 循环执行存储过程
		CommonLogger.info("添加合同数据到统计中,ContractInitiateService,updDevice");
		for (String dutyCode : dutyCodeList) {
			sysWarnCountService.DealSysWarnCount(dutyCode, "C");
		}
		// 新增物料信息
		return addCntDevice(bean, maxVersion,oldDataFlag);
	}

	/**
	 * @methodName updDevice desc 根据合同信息更新物料信息（订单退回）
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updOrderBackDevice(ModifyContract bean, BigDecimal maxVersion,String oldDataFlag) {
		CommonLogger.info("根据合同信息更新物料信息（订单退回）（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updOrderBackDevice");
		if ("Y".equals(bean.getDeviceChg())) {
			// 修改前的项目及执行金额
			List<CntDevice> list = getCntProj(bean.getCntNum());
			CommonLogger.info("更新项目冻结金额,ContractInitiateService,updOrderBackDevice");
			for (CntDevice device : list) {
				if (device.getDataFlag().equals("01")) {
					dao.updateFreezeAmt(device.getExecAmt(), device.getProjId(),device.getTaxNamt());
				}
			}
			// 删除之前待复核合同统计表的数据，先得到这个合同号对应的归口部门集合
			CommonLogger.info("删除之前待复核合同统计表的数据（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updOrderBackDevice");
			List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
			ccService.delWarnCntCheck(bean.getCntNum());
			// 循环执行存储过程
			CommonLogger.info("添加合同数据到统计中,ContractInitiateService,updDevice");
			for (String dutyCode : dutyCodeList) {
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}

		}
		// 删除原物料信息
		CommonLogger.info("删除原物料信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,updOrderBackDevice");
		dao.delOrderBackCntDevice(bean.getCntNum());
		// 新增物料信息
		return addOrderBackCntDevice(bean, maxVersion,oldDataFlag);
	}

	/**
	 * @methodName addCntDevice desc 新增物料信息
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addCntDevice(ModifyContract bean, BigDecimal maxVersion,String oldDataFlag) {
		CommonLogger.info("新增物料信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addCntDevice");
		List<CntDevice> list = new ArrayList<CntDevice>();

		for (int i = 0; i < bean.getProjId().length; i++) {
			CntDevice cntDevice = new CntDevice();
			cntDevice.setCntNum(bean.getCntNum());
			cntDevice.setSubId((i + 1) + "");
			cntDevice.setProjId(bean.getProjId()[i]);
			cntDevice.setFeeDept(bean.getFeeDept()[i]);
			cntDevice.setMatrCode(bean.getMatrCode()[i]);
			cntDevice.setMontCode(bean.getMontCode()[i]);
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
			if (("99".equals(bean.getDevDataFlag()[i]) || "90".equals(bean.getDevDataFlag()[i]))
					&& !bean.getProjDevChg()[i]) {
				if(!Tool.CHECK.isEmpty(bean.getAuditMemo())){
					cntDevice.setAuditMemo(bean.getAuditMemo()[i]);
				}else{
					cntDevice.setAuditMemo("");
				}
				cntDevice.setDataFlag(bean.getDevDataFlag()[i]);
			} else {
				cntDevice.setDataFlag("00");
			}
			cntDevice.setVersionNo(maxVersion);// 历史版本号
			cntDevice.setOperType("0");// 操作类型为新增
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
			if ("Y".equals(bean.getDeviceChg())) {
				// 生成对应项目的冻结金额
				if (!projService.newCnt(bean.getProjId()[i], bean.getExecAmt()[i],cntDevice.getTaxNamt())) {
					return false;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		int addCntDevice = initDao.addCntDevice(map);
		// 修改时物料发生了改变
		if ("Y".equals(bean.getDeviceChg())) {
			int addCntDeviceHis = initDao.addCntDeviceHis(map);
			// 向新的物料所属归口部门加入到合同统计表中为待复核数据
			// 如果为专向包
			if (bean.getIsSpec().equals("0")) {
				ccService.addWarnCnt(bean.getCntNum());
			}
			// 非专向包
			if (bean.getIsSpec().equals("1")) {
				ccService.addWarnCntNoSpec(bean.getCntNum());
			}
			//[ 费用 + 其它]
			if ("1".equals(bean.getCntType()) && "2".equals(bean.getFeeType())) {
				 ccService.addWarnCntSpecOrNoSpec(bean.getCntNum());
			}
			// 得到之前添加到合同统计表待复核数据的归口部门集合
			List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
			// 循环执行存储过程
			for (String dutyCode : dutyCodeList) {
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}
			return addCntDevice + addCntDeviceHis == list.size() + list.size() ? true : false;
		} else {
			int addCntDeviceHis = initDao.addCntDeviceHis(map);
			// 如果为确认退回或者订单退回的的合同
			if ("11".equals(oldDataFlag) || "40".equals(oldDataFlag)) {
				ccService.addWarnCntCheck(bean.getCntNum());
				// 重新统计确认机构的合同数据
				String dutyCode = WebHelp.getLoginUser().getDutyCode();
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}
			// 如果为保存的合同
			else {
				// 向新的物料所属归口部门加入到合同统计表中为待复核数据
				// 如果为专向包 或者  费用 +　其他 
				if (bean.getIsSpec().equals("0") ) {
					ccService.addWarnCnt(bean.getCntNum());
				}
				// 非专向包　或者  费用 +　其他 
				if (bean.getIsSpec().equals("1") ) {
					ccService.addWarnCntNoSpec(bean.getCntNum());
				}
				//[ 费用 + 其它]
				if ("1".equals(bean.getCntType()) && "2".equals(bean.getFeeType())) {
					 ccService.addWarnCntSpecOrNoSpec(bean.getCntNum());
				}
				// 得到之前添加到合同统计表待复核数据的归口部门集合
				List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
				// 循环执行存储过程
				for (String dutyCode : dutyCodeList) {
					sysWarnCountService.DealSysWarnCount(dutyCode, "C");
				}
			}
			/*
			 * // 物料没有发生改变且都是99状态的直接待确认 boolean appvDone = true; for (int i = 0;
			 * i < bean.getProjId().length; i++) { if
			 * (!"99".equals(bean.getDevDataFlag()[i])) { appvDone = false; } }
			 * if (appvDone) {
			 * 
			 * } // 物料没有发生改变但是不全是99状态的物料 else {
			 * 
			 * }
			 */
			return addCntDeviceHis == list.size() ? true : false;
		}
	}

	/**
	 * @methodName addCntDevice desc 新增物料信息（订单退回）
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addOrderBackCntDevice(ModifyContract bean, BigDecimal maxVersion,String oldDataFlag) {
		CommonLogger.info("新增物料信息（订单退回）（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,addOrderBackCntDevice");
		List<CntDevice> list = new ArrayList<CntDevice>();
		ArrayList<CntDevice> orderSucDevices = (ArrayList<CntDevice>) getOrderSucDevices(bean.getCntNum());
		for (CntDevice cDevice : orderSucDevices) {
			cDevice.setVersionNo(maxVersion);
		}
		int maxSubId = dao.getMaxSubIdByCntNum(bean.getCntNum());
		for (int i = 0; i < bean.getProjId().length; i++) {
			if (bean.getIsOrderSucDev()[i].equals("0")) {
				continue;
			}
			CntDevice cntDevice = new CntDevice();
			cntDevice.setCntNum(bean.getCntNum());
			cntDevice.setSubId((i + 1 + maxSubId) + "");
			cntDevice.setProjId(bean.getProjId()[i]);
			cntDevice.setFeeDept(bean.getFeeDept()[i]);
			cntDevice.setMatrCode(bean.getMatrCode()[i]);
			cntDevice.setMontCode(bean.getMontCode()[i]);
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
			if (!bean.getProjDevChg()[i]) {
				cntDevice.setDataFlag("90");
			} else {
				cntDevice.setDataFlag("00");
			}
			cntDevice.setVersionNo(maxVersion);// 历史版本号
			cntDevice.setOperType("0");// 操作类型为新增
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
			if ("Y".equals(bean.getDeviceChg())) {
				// 项目预算冻结
				if (!projService.newCnt(bean.getProjId()[i], bean.getExecAmt()[i],new BigDecimal(0))) {
					return false;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> historyMap = new HashMap<String, Object>();
		map.put("list", list);
		if (!orderSucDevices.addAll(list)) {
			return false;
		}
		historyMap.put("list", orderSucDevices);
		if ("Y".equals(bean.getDeviceChg())) {
			int addCntDevice = initDao.addCntDevice(map);
			int addCntDeviceHis = initDao.addCntDeviceHis(historyMap);
			// 向新的物料所属归口部门加入到合同统计表中为待复核数据
			// 如果为专向包
			if (bean.getIsSpec().equals("0")) {
				ccService.addWarnCnt(bean.getCntNum());
			}
			// 非专向包
			else {
				ccService.addWarnCntNoSpec(bean.getCntNum());
			}
			//[ 费用 + 其它]
			if ("1".equals(bean.getCntType()) && "2".equals(bean.getFeeType())) {
				 ccService.addWarnCntSpecOrNoSpec(bean.getCntNum());
			}
			// 得到之前添加到合同统计表待复核数据的归口部门集合
			List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
			// 循环执行存储过程
			for (String dutyCode : dutyCodeList) {
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}
			return addCntDevice + addCntDeviceHis == list.size() + orderSucDevices.size() ? true : false;
		} else {
			int addCntDevice = initDao.addCntDevice(map);
			int addCntDeviceHis = initDao.addCntDeviceHis(historyMap);
			
			// 如果为确认退回或者订单退回的的合同
			if ("11".equals(oldDataFlag) || "40".equals(oldDataFlag)) {
				ccService.addWarnCntCheck(bean.getCntNum());
				// 重新统计确认机构的合同数据
				String dutyCode = WebHelp.getLoginUser().getDutyCode();
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}
			// 如果为保存的合同
			else {
				// 向新的物料所属归口部门加入到合同统计表中为待复核数据
				// 如果为专向包　或者  费用 +　其他 
				if (bean.getIsSpec().equals("0")  ) {
					ccService.addWarnCnt(bean.getCntNum());
				}
				// 非专向包　或者  费用 +　其他 
				if (bean.getIsSpec().equals("1")  ) {
					ccService.addWarnCntNoSpec(bean.getCntNum());
				}
				//[ 费用 + 其它]
				if ("1".equals(bean.getCntType()) && "2".equals(bean.getFeeType())) {
					 ccService.addWarnCntSpecOrNoSpec(bean.getCntNum());
				}
				// 得到之前添加到合同统计表待复核数据的归口部门集合
				List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
				// 循环执行存储过程
				for (String dutyCode : dutyCodeList) {
					sysWarnCountService.DealSysWarnCount(dutyCode, "C");
				}
			}
			/*// 向新的物料所属归口部门加入到合同统计表中为待复核数据
			// 如果为专向包
			if (bean.getIsSpec().equals("0")) {
				ccService.addWarnCnt(bean.getCntNum());
			}
			// 非专向包
			else {
				ccService.addWarnCntNoSpec(bean.getCntNum());
			}
			// 得到之前添加到合同统计表待复核数据的归口部门集合
			List<String> dutyCodeList = ccService.getDutyCodeList(bean.getCntNum());
			// 循环执行存储过程
			for (String dutyCode : dutyCodeList) {
				sysWarnCountService.DealSysWarnCount(dutyCode, "C");
			}*/
			return addCntDevice + addCntDeviceHis == list.size() + orderSucDevices.size() ? true : false;
		}
	}

	/**
	 * @methodName modifyFeeType desc 根据合同编号更新费用信息列表
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	@Transactional(rollbackFor = Exception.class)
	public ModifyContract modifyFeeType(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("根据合同编号更新费用信息列表（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
		boolean isSuc = true;
		if (!"0".equals(bean.getFeeType())) {
			// 删除原租赁费用递增信息
			CommonLogger.info("删除原租赁费用递增信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
			dao.delTcyDz(bean.getCntNum());
			// 删除原租赁费用信息
			CommonLogger.info("删除原租赁费用信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
			dao.delTenancy(bean.getCntNum());
		}
		// 费用类型
		if ("0".equals(bean.getFeeType())) {
			// 金额固定、受益期固定
			bean.setFeeAmt(new BigDecimal(0));
			bean.setFeePenalty(new BigDecimal(0));
			if ("1".equals(bean.getIsOrder())) {
				bean.setIsPrepaidProvision("0");
			}
			// isSuc = isSuc && modifyCntFeeType(bean, maxVersion);
			// 费用子类型
			if ("0".equals(bean.getFeeSubType())) {
				// 普通费用类型

				// 删除原租赁费用递增信息
				CommonLogger.info("费用子类型_删除原租赁费用递增信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
				dao.delTcyDz(bean.getCntNum());
				// 删除原租赁费用信息
				CommonLogger.info("费用子类型_删除原租赁费用信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
				dao.delTenancy(bean.getCntNum());

			} else if ("1".equals(bean.getFeeSubType())) {
				// 房屋租赁类型
				// 递增费用信息
				CommonLogger.info("修改租赁费用递增信息（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyFeeType");
				isSuc = isSuc && modifyTcyDz(bean, maxVersion);
				if (dao.getTenancy(bean.getCntNum()) > 0) {
					isSuc = isSuc && updTenancy(bean, maxVersion);
				} else {
					isSuc = isSuc && initService.addTenancy(bean, maxVersion);
				}
			}
		} else if ("1".equals(bean.getFeeType())) {
			// 受益期固定、合同金额不固定
			bean.setFeeSubType("");
			if ("1".equals(bean.getIsOrder())) {
				bean.setIsPrepaidProvision("0");
			}
			// isSuc = isSuc && modifyCntFeeType(bean, maxVersion);
		} else if ("2".equals(bean.getFeeType())) {
			// 其他
			bean.setFeeSubType("");
			bean.setFeeStartDate("");
			bean.setFeeEndDate("");
			bean.setFeeAmt(new BigDecimal(0));
			bean.setFeePenalty(new BigDecimal(0));
			// 删除原预算信息
			// dao.delCntFee(bean.getCntNum());
		} /*
		 * else if (bean.getFeeType().equals("3")) { // 宣传费
		 * bean.setFeeSubType(""); bean.setFeeStartDate("");
		 * bean.setFeeEndDate(""); bean.setFeeAmt(new BigDecimal(0));
		 * bean.setFeePenalty(new BigDecimal(0)); //删除原预算信息
		 * //dao.delCntFee(bean.getCntNum()); }
		 */
		bean.setSuc(isSuc);
		return bean;
	}

	/**
	 * @methodName modifyCntFeeType desc 修改费用类型
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	public boolean modifyCntFeeType(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("修改费用类型（合同编号： " + bean.getCntNum()+"）,ContractInitiateService,modifyCntFeeType");
		dao.delCntFee(bean.getCntNum());
		if ("1".equals(bean.getIsOrder())) {
			return initService.addCntFeeType(bean, maxVersion);
		}
		return true;
	}

	/**
	 * @methodName modifyTcyDz desc 更新租赁费用递增信息列表
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	public boolean modifyTcyDz(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("更新租赁费用递增信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,modifyTcyDz");
		// 删除原租赁费用递增信息
		dao.delTcyDz(bean.getCntNum());
		return initService.addTenancyDz(bean, maxVersion);
	}

	/**
	 * @methodName updTenancy desc 更新房屋租赁信息
	 * 
	 * @param bean
	 *            合同信息， maxVersion 本次更新版本号
	 */
	public boolean updTenancy(ModifyContract bean, BigDecimal maxVersion) {
		CommonLogger.info("更新房屋租赁信息（合同编号： " + bean.getCntNum()+"）,ContractModifyService,updTenancy");
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

		// BigDecimal maxVersion =
		// initService.getMaxVersionNo("TD_CNT_TENANCY_LOG", bean.getCntNum(),
		// ""); // 最大版本号+1
		tenancy.setVersionNo(maxVersion);// 历史版本号
		tenancy.setOperType("2");// 操作类型为修改
		return dao.updTenancy(tenancy) + initDao.addTenancyHis(tenancy) == 2 ? true : false;
	}

	/**
	 * @methodName getBenefit desc 获取费用类型受益信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getBenefit(String cntNum) {
		CommonLogger.info("获取费用类型受益信息（合同编号： " + cntNum+"）,ContractModifyService,getBenefit");
		return dao.getBenefit(cntNum);
	}

	/**
	 * @methodName getSumExecAmt desc 查询同一项目总的执行金额
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getSumExecAmt(String cntNum) {
		CommonLogger.info("查询同一项目总的执行金额（合同编号： " + cntNum+"）,ContractModifyService,getSumExecAmt");
		return dao.getSumExecAmt(cntNum);
	}

	/**
	 * @methodName checkFreezeAmt desc 检查新物料信息冻结金额添加后是否超出同一项目总的执行金额
	 * 
	 * @param bean
	 *            合同信息
	 */
	public String checkFreezeAmt(ModifyContract bean) {
		CommonLogger.info("检查新物料信息冻结金额添加后是否超出同一项目总的执行金额（合同编号： " + bean.getCntNum()+"）,ContractModifyService,checkFreezeAmt");
		StringBuffer projName = new StringBuffer(); // 超出预算的项目

		Map<String, BigDecimal> projAmtMap = new HashMap<String, BigDecimal>();
		int projLen = null == bean.getProjId() ? 0 : bean.getProjId().length;
		// 修改后的项目及执行金额
		for (int i = 0; i < projLen; i++) {
			BigDecimal taxNamt = new BigDecimal(0);
			if ("N".equals(bean.getDeductFlag()[i])) {
				if (!Tool.CHECK.isEmpty(bean.getCntTrAmt()[i])) {
					taxNamt = bean.getCntTrAmt()[i];
				}
			}
			if (projAmtMap.containsKey(bean.getProjId()[i])) {
				projAmtMap.put(bean.getProjId()[i], projAmtMap.get(bean.getProjId()[i]).add(bean.getExecAmt()[i].add(taxNamt)));
			} else {
				projAmtMap.put(bean.getProjId()[i], bean.getExecAmt()[i].add(taxNamt));
			}
		}
		// 修改前的项目及执行金额
		List<CntDevice> oldCntProjAmtList = getSumExecAmt(bean.getCntNum());

		for (CntDevice dev : oldCntProjAmtList) {
			if (projAmtMap.containsKey(dev.getProjId())) {
				projAmtMap.put(dev.getProjId(), projAmtMap.get(dev.getProjId()).subtract(dev.getExecAmt().add(dev.getTaxNamt())));// 减去修改前的金额
			}
		}

		for (Iterator it = projAmtMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, BigDecimal> entry = (Entry<String, BigDecimal>) it.next();
			String curProjId = (String) entry.getKey();
			BigDecimal curExecAmt = (BigDecimal) entry.getValue();
			if (!projService.inFreezeAmtIsValid(curProjId, curExecAmt)) {
				projName.append(",");
				projName.append(projService.veiwProj(curProjId).getProjName());
			}
		}
		if ("".equals(projName.toString())) {
			return projName.toString();
		} else {
			return projName.toString().substring(1);
		}
	}

	/**
	 * @methodName delCnt desc 删除合同时删除相关信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	@Transactional(rollbackFor = Exception.class)
	public int delCnt(String cntNum) {
		//删除合同释放所有物料占用的冻结金额
		CommonLogger.info("释放合同（合同号： " + cntNum + " ）对应物料项目占用的冻结金额，ContractModifyService ，updateFreezeTotal");
		dao.updateFreezeTotal(cntNum);
		// 删除物料信息
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）对应的所有物料，ContractModifyService ，delCntDevice");
		dao.delCntDevice(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的电子审批信息，ContractModifyService ，delDZSPProj");
		// 删除电子审批信息
		dao.delDZSPProj(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的房屋租赁信息，ContractModifyService ，delTenancy");
		// 删除房屋租赁信息
		dao.delTenancy(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的租赁递增信息，ContractModifyService ，delTcyDz");
		// 删除租赁递增信息
		dao.delTcyDz(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的按进度付款信息，ContractModifyService ，delOnSchedule");
		// 删除按进度分期付款信息
		dao.delOnSchedule(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的按日期分期付款信息，ContractModifyService ，delOnDate");
		// 删除按日期分期付款信息
		dao.delOnDate(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的按条件分期付款信息，ContractModifyService ，delOnTerm");
		// 删除按条件分期付款
		dao.delOnTerm(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）下的费用类型信息，ContractModifyService ，delCntFee");
		// 删除费用类型信息
		dao.delCntFee(cntNum);
		//删除合同在ti_icms_pkuuid表中的记录
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）在扫描信息表中的记录，ContractModifyService ，delTiIcms");
		dao.delTiIcms(cntNum);
		//删除该合同号在合同审批历史表中的记录
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）在合同审批历史记录表中的数据，ContractModifyService ，delCntLog");
		dao.delCntLog(cntNum);
		//删除该合同号在合同设备表中的记录数据
		CommonLogger.info("删除合同（合同号： " + cntNum + " ）在合同设备历史表中的数据，ContractModifyService ，delCntDevLog");
		dao.delCntDevLog(cntNum);
		CommonLogger.info("删除合同（合同号： " + cntNum + " ），ContractModifyService ，delCnt");
		// 删除合同
		return dao.delCnt(cntNum);
	}

	/**
	 * @methodName getOrderSucDevices desc 根据合同号获取状态为订单退回的合同中发送成功的订单对应的物料列表
	 * 
	 * @return
	 */
	public List<CntDevice> getOrderSucDevices(String cntNum) {
		CommonLogger.info("根据合同号获取状态为订单退回的合同中发送成功的订单对应的物料列表（合同号： " + cntNum + " ），ContractModifyService ，getOrderSucDevices");
		return dao.getOrderSucDevices(cntNum);
	}

	/**
	 * @methodName getOrderSucDevices desc 根据合同号获取状态为订单退回的合同中的退回订单对应的物料列表
	 * 
	 * @return
	 */
	public List<CntDevice> orderBackNewDevices(String cntNum) {
		CommonLogger.info("根据合同号获取状态为订单退回的合同中的退回订单对应的物料列表（合同号： " + cntNum + " ），ContractModifyService ，orderBackNewDevices");
		return dao.orderBackNewDevices(cntNum);
	}

	/**
	 * 查询出不能修改的物料
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<CntDevice> checkPassDev(String cntNum) {
		// TODO Auto-generated method stub
		CommonLogger.info("查询出不能修改的物料（合同号： " + cntNum + " ），ContractModifyService ，checkPassDev");
		return dao.checkPassDev(cntNum);
	}

	public List<CntDevice> canMotidyDev(String cntNum) {
		CommonLogger.info("查询出能修改的物料（合同号： " + cntNum + " ），ContractModifyService ，canMotidyDev");
		// TODO Auto-generated method stub
		return dao.canMotidyDev(cntNum);
	}
	
	public List<TenancyDz> getTcyDzByMatrCode( String matrCodeFz , String cntNum ){
		CommonLogger.info("ajax查询该物料的房租递增信息（合同号： " + cntNum + ",物料编码："+matrCodeFz +" ），ContractModifyService ，getTcyDzByMatrCode");
		return dao.getTcyDzByMatrCode(matrCodeFz, cntNum);
	}
}
