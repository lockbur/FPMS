package com.forms.prms.web.contract.end.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.end.dao.EndDAO;
import com.forms.prms.web.contract.end.domain.EndForm;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.pay.orderstart.dao.OrderStartDao;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class EndService {
	@Autowired
	private EndDAO eDao;
	
	@Autowired
	private OrderStartDao ordDao;

	@Autowired
	private WaterBookService wService;

	@Autowired
	private ContractQueryService queryService;
	
	@Autowired
	private SysWarnCountService sysWarnCountService;
	
	
	/**
	 * 合同终止列表
	 * 
	 * @param form
	 * @return
	 */
	public List<EndForm> list(EndForm form) {
		CommonLogger.info("查询合同确认完成可以终止的合同数据，EndService，list");
		form.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		EndDAO PageDAO = PageUtils.getPageDao(eDao);
		return PageDAO.endList(form);
	}
	
	public List<EndForm> cancelList(EndForm con) {
		CommonLogger.info("查询合同确认完成可以取消的合同数据，EndService，cancelList");
		
		con.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		con.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		con.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		
		EndDAO PageDAO = PageUtils.getPageDao(eDao);
		return PageDAO.list(con);
	}
	
	/**
	 * 合同完成详情
	 * @param cntNum
	 * @return
	 */
	public QueryContract view(String cntNum){
		QueryContract cnt = queryService.getDetailCheck(cntNum);
		if("0".equals(cnt.getIsPrepaidProvision())){
		//是预提待摊类合同
			CommonLogger.info("预提待摊类合同查看合同号为"+cntNum+"剩余的待摊余额");
			cnt.setPrepaidRemaindAmt(eDao.getPrepaidRemainAmt(cntNum));
		}
		return cnt;
		
	}

	/**
	 * 合同取消
	 * 
	 * @param cntNum
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public int end(EndForm e) throws Exception {
		CommonLogger.info("合同取消（合同号："+e.getCntNum()+"），EndService，end");
		int i = eDao.end(e.getCntNum());
		CommonLogger.info("取消合同后释放合同号为"+e.getCntNum()+"对应的项目预算，EndService，updateCancelProjAmt");
		eDao.updateCancelProjAmt(e.getCntNum());
		
		//合同取消，项目累计已付款金额释放（begin）
		CommonLogger.info("取消合同后释放合同号为:"+e.getCntNum()+",对应的项目累计已付款金额，EndService，updateCancelProjAmt");
		eDao.releaseProjPayAmt(e.getCntNum());
		//合同取消，项目累计已付款金额释放（end）
		
		//未发送FMS的订单更新为14：订单取消（合同取消） 
		eDao.updateOrdStatus(e.getCntNum());
		//记录订单更新处理日志
		List<OrderStartBean> ordList = eDao.getOrdList(e.getCntNum());
		if(null != ordList && ordList.size() > 0)
		{
			for(OrderStartBean bean : ordList)
			{
				// 向订单历史表中加入一条取消的记录
				bean.setDataFlag("14");
				bean.setInstUser(WebHelp.getLoginUser().getUserId());
				bean.setOperMemo("合同取消时，取消未上送订单。");
				ordDao.addLogOrder(bean);
				CommonLogger.info("合同取消，订单号：" + bean.getOrderId() + "添加一条订单取消（合同取消）的记录，EndService，end");
			}
		}
		
		//sx:合同取消未发送fms的付款单状态全部置为“失效（AX）”状态）
		CommonLogger.info("合同取消后合同号为"+e.getCntNum()+"下的未发送FMS的正常付款单都置为无效付款单，EndService，updatePayStatus");
		int upPay=eDao.updatePayStatus(e.getCntNum());//正常付款更新
		CommonLogger.info("合同取消后合同号为"+e.getCntNum()+"下的未发送FMS的预付款单都置为无效付款单，EndService，updatePrePayStatus");
		int upPrePay=eDao.updatePrePayStatus(e.getCntNum());//预付款更新
		//只要有置为无效的正常付款或者预付款
		if(upPay>0||upPrePay>0){
			//得到更新为无效的正常付款和预付款集合
			String userId=WebHelp.getLoginUser().getUserId();
			/*List<PayAddBean> axList=eDao.getAxList(e.getCntNum(),userId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("axList", axList);*/
			//插入到付款记录表中
			eDao.batchInsert(e.getCntNum(),userId);
			//删除付款单前线记录涉及这些付款单涉及到哪些责任中心
			List<String> dutyList=eDao.dutyList(e.getCntNum());
			//删除付款单
			eDao.delBatchPay(e.getCntNum());
			//循环重新统计条数
			for (String dutyCode : dutyList) {
				sysWarnCountService.DealSysWarnCount(dutyCode,"P");
			}
		}
		//预算释放
		Map<String, String> param = new HashMap<String, String>();
		param.put("cntNo", e.getCntNum());
		param.put("retMsg", "");
		eDao.cntCancelFreeBgt(param);
		//预算处理失败，抛出异常
		if(null == param.get("retMsg") || "".contentEquals(param.get("retMsg")) || "0".contentEquals(param.get("retMsg")))
		{
			throw new Exception("合同："+e.getCntNum()+"取消 ，释放预算失败");
		}
		
		if (i > 0) {
			// 增加流水
			wService.insert(e.getCntNum(), BusTypes.CONTRACT, OperateValues.CNTEND, e.getWaterMemo(),"20","30");
		}
		return i;
	}

	private void getAxList(String cntNum) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 合同完成
	 * 
	 * @param cntNum
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int finish(EndForm e) {
		CommonLogger.info("对合同号"+e.getCntNum()+"进行合同终止操作，EndService，finish");
		int i = eDao.finish(e.getCntNum());
		CommonLogger.info("合同号"+e.getCntNum()+"完成释放该合同对应的项目预算，EndService，updateProjAmt");
		eDao.updateProjAmt(e.getCntNum());
		if(e.getPrepaidStatus() != null){
			if("Y".equals(e.getPrepaidStatus())){
				CommonLogger.info("一次完成待摊（合同号："+e.getCntNum()+"），EndService，finish");
				eDao.updatePrepaidStatus(e.getCntNum());
			}
		}
		CommonLogger.info("合同终止（合同号："+e.getCntNum()+"），EndService，finish");
		if (i > 0) {
			// 增加流水
			wService.insert(e.getCntNum(), BusTypes.CONTRACT, OperateValues.CNTFINISH, e.getWaterMemo(),"20","32");
		}
		return i;
	}
	
	/**
	 * @methodName getEndAmt
	 * desc  校验合同退款后剩余的总额是否为0  
	 * 
	 * @param cntNum
	 * @return
	 */
	public boolean getEndAmt(String cntNum){
		CommonLogger.info("查看合同号为"+cntNum+"退款后剩余的总额是否为0，EndService，getEndAmt");
		return eDao.getEndAmt(cntNum).getBackAmt().compareTo(new BigDecimal(0)) == 0 ?true:false;
	}
}
