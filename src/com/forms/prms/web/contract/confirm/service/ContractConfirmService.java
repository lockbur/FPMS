package com.forms.prms.web.contract.confirm.service;

import java.math.BigDecimal;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.confirm.dao.IContractConfirmDAO;
import com.forms.prms.web.contract.confirm.domain.BudgetBean;
import com.forms.prms.web.contract.confirm.domain.ConfirmContract;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.HistoryContract;
import com.forms.prms.web.contract.initiate.service.ContractInitiateService;
import com.forms.prms.web.contract.modify.dao.IContractModifyDAO;
import com.forms.prms.web.contract.query.dao.IContractQueryDAO;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;
import com.forms.prms.web.util.ForwardPageUtils;

@Service
public class ContractConfirmService {
	@Autowired
	private IContractQueryDAO queryDao;
	@Autowired
	private IContractConfirmDAO dao;

	@Autowired
	private IContractModifyDAO mdao;
	
	@Autowired
	private ContractQueryService queryService;

	@Autowired
	private WaterBookService wService;

	@Autowired
	private ProjectMgrService projService;

	@Autowired
	private ContractQueryService conQueryService;

	@Autowired
	private ContractInitiateService initService;
	
	@Autowired
	private ConcurrentService concurrentService;

	/**
	 * @methodName confirmList desc 根据查詢條件获取待确认合同列表
	 * 
	 * @param con
	 *            合同确认查询条件对象
	 */
	public List<ConfirmContract> confirmList(ConfirmContract con) {
		CommonLogger.info("查询待确认的合同数据，ContractConfirmService，confirmList");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("instUser", WebHelp.getLoginUser().getUserId());

		List<ConfirmContract> cntList = null;
		IContractConfirmDAO pageDao = PageUtils.getPageDao(dao);
		cntList = pageDao.confirmList(paramMap);

		return cntList;
	}

	/**
	 * @methodName getDetail desc 根据合同编号获取合同详情
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public QueryContract getDetail(String cntNum) {
		return queryService.getDetailCheck(cntNum);
	}

	/**
	 * @methodName getCntProj desc 根据合同编号获取项目列表
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<ConfirmContract> getCntProj(String cntNum) {
		return dao.getCntProj(cntNum);
	}
	
	/**
	 * 合同确认前录入受益金额
	 * @param cnt
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmFeeAmt(ConfirmContract cnt){
		// 删除费用类型信息(费用修改的dao)
		CommonLogger.info("删除合同号为"+cnt.getCntNum()+"对应的费用类信息,ContractConfirmService,delCntFee");
		mdao.delCntFee(cnt.getCntNum());
		CommonLogger.info("删除合同号为"+cnt.getCntNum()+"对应的费用类日志信息,ContractConfirmService,delCntFeeLog");
		mdao.delCntFeeLog(cnt.getCntNum());
		BigDecimal versionNo = new BigDecimal(0);
		if (cnt.getFeeYyyymm() != null) {
			// 新增合同费用受益信息
			CommonLogger.info("得到合同号为"+cnt.getCntNum()+"在TD_CNT_LOG表中的最大版本号，ContractConfirmService，getHisMaxVersion");
			versionNo = initService.getHisMaxVersion(new HistoryContract("TD_CNT_LOG", cnt.getCntNum(), ""))
					.getVersionNo();
		}
		return initService.addCntFeeType(cnt, versionNo);
	}
	
	/**
	 * 检查当前系统日期是否小于等于受益结束日期
	 * @param end
	 * @return
	 */
	public boolean compareDate(String end){
		if("1".equals(dao.compareDate(end))){
			return true;
		}
		return false;
	}

	/**
	 * @methodName confirmPass desc 根据合同编号修改合同状态为‘合同确认完成’
	 * 
	 * @param cnt
	 *            合同确认对象
	 * @throws Exception 
	 * @throws UnknownHostException 
	 */
	//@Transactional(rollbackFor = Exception.class)
	public Map<String, String> confirmPass(ConfirmContract cnt) throws Exception {
		QueryContract contract = queryDao.getDetail(cnt.getCntNum());
		//合同类型为费用类，费用子类型为前两类的默认为非订单
		if("1".equals(contract.getCntType()) && ("0".equals(contract.getFeeType()) ||"1".equals(contract.getFeeType()))){
			cnt.setIsOrder("1");
		}				
		//处理预算（调过程）
		Map<String, String> param = new HashMap<String, String>();
		param.put("syyyymm", "");//开始日期
		param.put("eyyyymm", "");//结束日期
		param.put("cntNum", cnt.getCntNum());//合同号
		param.put("isOrder", cnt.getIsOrder());//是否订单
		param.put("instUser", WebHelp.getLoginUser().getUserId());//确认人
		param.put("instUserDutyCode", WebHelp.getLoginUser().getDutyCode());//确认人所在责任中心
		CommonLogger.info("合同确认通过，开始调用预算存储过程...");
		dao.bgtFrozen(param);
		String flag = param.get("flag");
		String returnStr = param.get("msgInfo");
		//0表示预算冻结失败
		if("0".equals(flag)){
			CommonLogger.info("合同确认（"+cnt.getCntNum()+"）失败："+returnStr+"。");
		}
		//1表示预算冻结成功
		else{
			CommonLogger.info("合同确认（"+cnt.getCntNum()+"）成功,增加操作流水...");
			wService.insert(cnt.getCntNum(), BusTypes.CONTRACT, OperateValues.CNTCONFIRM, cnt.getWaterMemo(), "12",
					"0".equals(cnt.getIsOrder())?"19":"20");
		}
		//在确认校验一下是否有因为监控指标拆分导致的合同必须先勾选 还没做完,或者物料行的监控指标和物料不是最新的
		Map<String, Object> map = this.isHaveMontSplit(cnt);
		if ("N".equals(map.get("flag"))) {
			param.put("flag", "0");
			param.put("msgInfo", map.get("msg").toString());
			throw new Exception(map.get("msg").toString());
		}
		return param;
	}

	/**
	 * @methodName confirmReturn desc 根据合同编号修改合同状态为‘合同退回’
	 * 
	 * @param cnt
	 *            合同确认对象
	 */
	@Transactional(rollbackFor = Exception.class)
	public int confirmReturn(ConfirmContract cnt) {
		CommonLogger.info("合同退回（合同号：" + cnt.getCntNum() + "），ContractConfirmService，confirmReturn");
		int rs = dao.confirmReturn(cnt.getCntNum());
		// 增加流水
		if (rs > 0) {
			wService.insert(cnt.getCntNum(), BusTypes.CONTRACT, OperateValues.CNTRETURN, cnt.getWaterMemo(), "12", "11");
		}

		return rs;
	}

	/**
	 * @methodName checkProjAmt desc 校验采购项目金额是否超出项目预算
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public String checkProjAmt(String cntNum) {
		// 获取采购项目金额，减去项目对应的冻结金额，加上执行金额
		List<CntDevice> list = conQueryService.getCntProj(cntNum);
		Map<String, BigDecimal> projAmtMap = new HashMap<String, BigDecimal>();
		for (CntDevice cntDevice : list) {
			if (projAmtMap.containsKey(cntDevice.getProjId())) {
				projAmtMap
						.put(cntDevice.getProjId(), projAmtMap.get(cntDevice.getProjId()).add(cntDevice.getExecAmt()));
			} else {
				projAmtMap.put(cntDevice.getProjId(), cntDevice.getExecAmt());
			}
		}
		Iterator iter = projAmtMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String projId = (String) entry.getKey();
			BigDecimal execAmt = (BigDecimal) entry.getValue();
			if (!projService.incntAmtIsValid(projId, execAmt)) {
				return projService.veiwProj(projId).getProjName();
			}
		}
		return null;
	}

	/**
	 * @methodName getFeeStartFlag desc 获取当前合同的费用受益金额不为0的起始日期与当前月的对比
	 * 
	 * @param cntNum
	 * @return
	 */
	public boolean getFeeStartFlag(String cntNum) {
		String feeStartDate = dao.getFeeStartDate(cntNum);
		String nowDate = Tool.DATE.getDateStrNO().substring(0,6);
		if (feeStartDate.compareTo(nowDate) < 0) {
			// 受益金额不为0的最小起始日期大于当前月，表示受益已开始
			return true;
		}
		return false;
	}

	/**
	 * 预提待摊类合同在合同确立时冻结预算
	 * @param cntNum
	 * @return
	 */
	public void bgtFrozen(Map<String, String> param) {
		CommonLogger.info("合同确认时冻结预算，ContractConfirmService，bgtFrozen");
		dao.bgtFrozen(param);
	}

	/**
	 * 
	 * 预提待摊类合同在合同确立时冻结预算
	 *	查询冻结明细
	 * @param cntNum
	 * @return
	 */
	public List<BudgetBean> queryBgtFrozenDetail(String cntNum) {
		CommonLogger.info("查询合同号为"+cntNum+"的冻结明细，ContractConfirmService，queryBgtFrozenDetail");
		return dao.queryBgtFrozenDetail(cntNum);
	}

	/**
	 * 删除预算临时数据
	 * @param cntNum
	 * @return
	 */
	public boolean deleteBgtFrozenTemp(String cntNum) {
		CommonLogger.info("删除合同号为"+cntNum+"的预算临时数据，ContractConfirmService，deleteBgtFrozenTemp");
		return dao.deleteBgtFrozenTemp(cntNum)>0?true:false;
	}
	
	public Map<String, Object> isHaveMontSplit(ConfirmContract cnt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", "Y");
		int count  = dao.isHaveMontSplit(cnt);
		if (count>0) {
			//
			map.put("flag", "N");
			map.put("msg", "因为这个合同里面有"+count+"组监控指标和物料涉及到拆分，所以不能确立合同。");
		}
		String cntNums = dao.isInTableMont(cnt);
		if (!Tool.CHECK.isEmpty(cntNums)) {
			map.put("flag", "N");
			map.put("msg", "合同的这些行！"+cntNums+"的监控指标和物料在监控指标表中不存在");
		}
		return map; 
	}

	public List<String> orderList(String cntNum) {
		return dao.orderList(cntNum);
	}

	public List<String> notOrderMatrList(String cntNum) {
		return dao.notOrderMatrList(cntNum);
	}
}
