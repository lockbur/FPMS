package com.forms.prms.web.contract.confirmchg.service;

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
import com.forms.prms.web.contract.confirmchg.dao.IContractConfirmChgDAO;
import com.forms.prms.web.contract.confirmchg.domain.ConfirmChgContract;
import com.forms.prms.web.contract.initiate.dao.ContractInitiateDAO;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.service.ContractInitiateService;
import com.forms.prms.web.contract.modify.dao.IContractModifyDAO;
import com.forms.prms.web.contract.modify.domain.ModifyContract;
import com.forms.prms.web.contract.modify.service.ContractModifyService;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class ContractConfirmChgService {

	@Autowired
	private IContractConfirmChgDAO dao;

	@Autowired
	private IContractModifyDAO modDao;

	@Autowired
	private ContractModifyService modService;

	@Autowired
	private ContractInitiateService initService;

	@Autowired
	private WaterBookService wService;

	@Autowired
	private ContractInitiateDAO initDao;

	@Autowired
	private ProjectMgrService projService;
	
	/**
	 * @methodName confirmChgList desc 根据查詢條件获取变更确认合同列表
	 * 
	 * @param con
	 *            查询列表条件
	 */
	public List<ConfirmChgContract> confirmChgList(ConfirmChgContract con) {
		CommonLogger.info("查询变更待确认的合同，ContractConfirmChgService，confirmChgList");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		String dutyCode = WebHelp.getLoginUser().getDutyCode();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("org1Code", org1Code);
		paramMap.put("org2Code", org2Code);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("instUser", WebHelp.getLoginUser().getUserId());
		
		List<ConfirmChgContract> cntList = null;
		IContractConfirmChgDAO pageDao = PageUtils.getPageDao(dao);
		cntList = pageDao.confirmChgList(paramMap);

		return cntList;
	}

	/**
	 * @methodName updCnt desc 修改合同
	 * 
	 * @param bean
	 *            合同修改信息
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updCnt(ModifyContract bean) {

		boolean updSuc = true;
		BigDecimal maxVersion = initService.getMaxVersionNo("TD_CNT_LOG", bean.getCntNum(), ""); // 最大版本号+1
		// 电子审批
		if ("1".equals(bean.getLxlx())) {
			// 修改电子审批列表
			updSuc = updSuc && modService.modifyDZSPProj(bean, maxVersion);
		} else {
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的电子审批信息，ContractConfirmChgService，delDZSPProj");
			modDao.delDZSPProj(bean.getCntNum());
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
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的原租赁费用递增信息，ContractConfirmChgService，delTcyDz");
			modDao.delTcyDz(bean.getCntNum());
			// 删除原租赁费用信息
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的原租赁费用信息，ContractConfirmChgService，delTenancy");
			modDao.delTenancy(bean.getCntNum());
			// 删除费用信息
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的费用信息，ContractConfirmChgService，delCntFee");
			modDao.delCntFee(bean.getCntNum());
		} else if ("1".equals(bean.getCntType())) {
			// 费用类
			bean = modService.modifyFeeType(bean, maxVersion);
			updSuc = updSuc && bean.isSuc();
		}

		if ("3".equals(bean.getPayTerm())) {
			updSuc = updSuc && modService.updStageInfo(bean, maxVersion);
		} else {
			// 删除原分期付款记录
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的按进度分期付款信息，ContractConfirmChgService，delOnSchedule");
			modDao.delOnSchedule(bean.getCntNum());
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的按日期分期付款信息，ContractConfirmChgService，delOnDate");
			modDao.delOnDate(bean.getCntNum());
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的按条件分期付款信息，ContractConfirmChgService，delOnTerm");
			modDao.delOnTerm(bean.getCntNum());
		}

		String oldDataFlag = bean.getDataFlag();
		bean.setDataFlag("20");

		bean.setVersionNo(maxVersion);// 历史版本号
		bean.setOperType("3");// 操作类型为新增
		bean.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		bean.setOperUser(WebHelp.getLoginUser().getUserId());
		bean.setOperMemo("");
		if ("0".equals(bean.getIsSpec())) {
			bean.setIsProvinceBuy("");
		}
		// 更新物料信息
		updSuc = updSuc && updDevice(bean,maxVersion);
		updSuc = updSuc && dao.updCnt(bean) + initDao.addCntHis(bean) == 2 ? true : false;
		
		//物料费用承担部门变更 调整相关数据
		updSuc = updSuc && updDeviceFeeDept(bean);

		CommonLogger.info("合同变更确认（合同号：" + bean.getCntNum() + "），ContractConfirmChgService，updCnt");
		if (updSuc) {
			wService.insert(bean.getCntNum(), BusTypes.CONTRACT, OperateValues.CONFIRMCHG, "", oldDataFlag,
					bean.getDataFlag());
		}
		return updSuc;
	}

	/** 
	 * @methodName updDevice
	 * desc   根据合同信息更新物料信息
	 * 
	 * @param bean 合同信息， maxVersion 本次更新版本号
	 */
	public boolean updDevice(ModifyContract bean, BigDecimal maxVersion) {
		if ("Y".equals(bean.getDeviceChg())) {
			// 修改前的项目及执行金额
			List<CntDevice> oldCntProjAmtList = modService.getSumExecAmt(bean.getCntNum());
			for (CntDevice dev : oldCntProjAmtList) {
				CommonLogger.info("更新项目编号为"+dev.getProjId()+"的执行金额，ContractConfirmChgService，updateProjCntAmt");
				modDao.updateProjCntAmt(dev.getExecAmt(), dev.getProjId(),dev.getTaxNamt());
			}
			// 删除原物料信息
			CommonLogger.info("删除合同号为"+bean.getCntNum()+"对应的物料信息，ContractConfirmChgService，delCntDevice");
			modDao.delCntDevice(bean.getCntNum());
		}
		
		//更新项目累计付款金额 add by sunxing 20160909 begin
		String[] projchanges = bean.getProjchange();
		String[] projIdOld = bean.getProjIdOld();
		String[] projId = bean.getProjId();
		if(null != projchanges && projchanges.length > 0)
		{
			for(int i=0;i<projchanges.length;i++)
			{
				if("Y".equals(projchanges[i]))
				{
					//查询该物料以前累计占用的金额
					ConfirmChgContract payAmtBean = dao.getTotalPayAmt(bean.getCntNum(),bean.getSubId()[i]);
					BigDecimal payAmt = payAmtBean.getPayAmt();
					//旧项目ID累计付款金额减少
					dao.ruduceProjPayAmt(projIdOld[i],payAmt);
					//新项目ID累计付款 金额增加
					dao.addProjPayAmt(projId[i],payAmt);
				}
			}
		}
		//更新项目累计付款金额 add by sunxing 20160909 end

		// 新增物料信息
		return addCntDevice(bean, maxVersion);
	}
	
	/**
	 * 合同变更-调整费用承担部门相关数据
	 * @param bean
	 * @return
	 */
	public boolean updDeviceFeeDept(ModifyContract bean){
		if("Y".equals(bean.getDeviceChg())){
		int length = bean.getFeeDeptOld().length;
		List<String> list = new ArrayList<String>();
		for(int i=0;i<length;i++){
			if(bean.getFeeDept()[i].equals(bean.getFeeDeptOld()[i])||list.contains(bean.getFeeDeptOld()[i])){
				list.add(bean.getFeeDeptOld()[i]);
				continue;
			}else{
				list.add(bean.getFeeDeptOld()[i]);
				Map<String, String> param = new HashMap<String, String>();
				param.put("cntNum", bean.getCntNum());
				param.put("cntType", bean.getBgtType());
				param.put("feeDeptOld", bean.getFeeDeptOld()[i]);
				param.put("feeDeptNew", bean.getFeeDept()[i]);
				dao.calCntChange(param);
				if("1".equals(param.get("ret"))){
					CommonLogger.info("合同变更-调整费用承担部门（旧："+bean.getFeeDeptOld()[i]+"；新:"+bean.getFeeDept()[i]+"）相关信息成功！"+param.get("retInfo")+"，ContractConfirmChgService，updDeviceFeeDept");
				}else if("0".equals(param.get("ret"))){
					CommonLogger.error("合同变更-调整费用承担部门（旧："+bean.getFeeDeptOld()[i]+"；新:"+bean.getFeeDept()[i]+"）相关信息失败！"+param.get("retInfo")+"，ContractConfirmChgService，updDeviceFeeDept");
					return false;
				}
			}
		}}
		return true;
	}
	
	/** 
	 * @methodName addCntDevice
	 * desc   新增物料信息
	 * 
	 * @param bean 合同信息， maxVersion 本次更新版本号
	 */
	public boolean addCntDevice(ModifyContract bean, BigDecimal maxVersion) {
		List<CntDevice> list = new ArrayList<CntDevice>();
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
//			cntDevice.setWarranty(bean.getWarranty()[i]);
//			cntDevice.setProductor(bean.getProductor()[i]);
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
			
			cntDevice.setDataFlag(bean.getDevDataFlag()[i]);
			cntDevice.setVersionNo(maxVersion);// 历史版本号
			cntDevice.setOperType("0");// 操作类型为新增
			cntDevice.setAuditMemo(Tool.CHECK.isEmpty(bean.getAuditMemo()) ? "":bean.getAuditMemo()[i] );// 审批备注信息
			
			list.add(cntDevice);
			if ("Y".equals(bean.getDeviceChg())) {
				// 修改项目的执行预算金额
				if (projService.updateCntAmt(bean.getProjId()[i],bean.getExecAmt()[i],cntDevice.getTaxNamt()) != 1) {
					return false;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		if ("Y".equals(bean.getDeviceChg())) {
			return initDao.addCntDevice(map) + initDao.addCntDeviceHis(map) == list.size() + list.size() ? true : false;
		} else {
			return initDao.addCntDeviceHis(map) == list.size() ? true : false;
		}
	}
	
	public ConfirmChgContract getInfo(String cntNum){
		CommonLogger.info("得到合同号为"+cntNum+"的变更确认详细信息，ContractConfirmChgService，getInfo");
		return dao.getInfo(cntNum);
	}
}

