package com.forms.prms.web.pay.paycancel.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.paycancel.dao.PayCancelDAO;
import com.forms.prms.web.pay.paycancel.domain.PayCancelBean;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;

@Service
public class PayCancelService {
	@Autowired
	private PayCancelDAO payCancelDAO;

	/**
	 * 付款历史信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	public List<PayCancelBean> list(PayCancelBean payCancelBean) {
		CommonLogger.info("付款历史数据列表信息查询,PayCancelService,list");
		PayCancelDAO pageDao = PageUtils.getPageDao(payCancelDAO);
		payCancelBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());// 得到登录人所在的一级行
		return pageDao.list(payCancelBean);
	}

	/**
	 * 查询合同付款信息
	 * 
	 * @param payId
	 * @return
	 */
	public PayCancelBean getPayByPayId(PayCancelBean payCancelBean) {
		CommonLogger.info("查询合同付款信息（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,getPayByPayId");
		return payCancelDAO.getPayByPayId(payCancelBean);
	}

	/**
	 * 查询付款设备信息
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPayDeviceListByPayId(
			PayCancelBean payCancelBean) {
		CommonLogger.info("查询付款设备信息（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,getPayDeviceListByPayId");
		return payCancelDAO.getPayDeviceListByPayId(payCancelBean);
	}

	/**
	 * 查询预付款核销列表
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<PayQueryBean> getPrePayCancleListByCntNum(
			PayCancelBean payCancelBean) {
		CommonLogger.info("查询付款设备信息（合同号："+payCancelBean.getCntNum()+"）,PayCancelService,getPrePayCancleListByCntNum");
		return payCancelDAO.getPrePayCancleListByCntNum(payCancelBean);
	}

	/**
	 * 付款取消（都是导入的数据不用分订单类与非订单类的数据）
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean cancelPay(PayCancelBean payCancelBean) {
		boolean flag = false;
		if ("N".equalsIgnoreCase(payCancelBean.getIsPrePay())) {// 正常付款
			payCancelBean.setTable("TD_PAY");
			//更新合同信息（预付款核销、正常付款、暂收）
			CommonLogger.info("付款取消:正常付款更新合同信息（合同号："+payCancelBean.getCntNum()+"）,PayCancelService,cancelPay");
			int n = payCancelDAO.updateCntInfoToPay(payCancelBean);
			//更新付款表的金额
			CommonLogger.info("付款取消:正常付款更新金额（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,cancelPay");
			int m = payCancelDAO.updatePayInfo(payCancelBean);
			//预付款信息不为空
			if(!Tool.CHECK.isEmpty(payCancelBean.getAdvancePayIds())){
				//预付款核销信息更新
				this.updateAdvCancelInfo(payCancelBean);
				//预付款核销设备信息更新
				this.updateAdvCancelDevInfo(payCancelBean);
			}
			//正常付款信息不为空
			if(!Tool.CHECK.isEmpty(payCancelBean.getPayCancelAmts())){
				//正常付款设备更新
				this.updateCancelDevInfo(payCancelBean);
			}
			
			if(n>0&&m>0){
				flag = true;
			}
		} else {// 预付款
			payCancelBean.setTable("TD_PAY_ADVANCE");
			//更新合同信息（预付款）
			CommonLogger.info("付款取消:预付款更新合同信息（合同号："+payCancelBean.getCntNum()+"）,PayCancelService,cancelPay");
			int n = payCancelDAO.updateCntInfoToPrePay(payCancelBean);
			//更新预付款信息
			CommonLogger.info("付款取消:预付款更新金额（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,cancelPay");
			int m = payCancelDAO.updateAdvInfo(payCancelBean);
			if(n>0&&m>0){
				flag = true;
			}
		}
		//先 查询出发票金额是否为0来给状态
		CommonLogger.info("付款取消:查询付款信息（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,cancelPay");
		PayCancelBean pbBean = payCancelDAO.queryPayInfoById(payCancelBean);
		if(pbBean.getInvoiceAmt().compareTo(new BigDecimal(0))==0){
			payCancelBean.setDataFlag("E1");
		}else{
			payCancelBean.setDataFlag("E0");
		}
		//更新付款表或预付表的状态
		CommonLogger.info("付款取消:更新付款表或预付表的状态（付款单号："+payCancelBean.getPayId()+"）,PayCancelService,cancelPay");
		int k = payCancelDAO.updateDataFlag(payCancelBean);
		if(k<=0){
			flag = false;
		}
		return flag;
	}

	//正常付款设备更新
	private void updateCancelDevInfo(PayCancelBean payCancelBean) {
		String cntNum = payCancelBean.getCntNum();//合同号
		BigDecimal[] subIds = payCancelBean.getSubIds();//子序号
		BigDecimal[] payCancelAmts = payCancelBean.getPayCancelAmts();//取消的金额
		String payId = payCancelBean.getPayId();//付款单号
		String payType = "1";//付款类型
		CommonLogger.info("付款取消:正常付款更新合同设备信息和更新设备付款信息（合同号："+payCancelBean.getCntNum()+",付款单号："+payCancelBean.getPayId()+"）,PayCancelService,updateCancelDevInfo");
		for(int i=0;i<payCancelAmts.length;i++){
			if(payCancelAmts[i].compareTo(BigDecimal.ZERO)!=0){
				PayCancelBean pcBean = new PayCancelBean();
				pcBean.setPayId(payId);
				pcBean.setCntNum(cntNum);
				pcBean.setPayType(payType);
				pcBean.setSubId(subIds[i]);
				pcBean.setDevCancelAmt(payCancelAmts[i]);
				payCancelDAO.updateCntDevInfo(pcBean);//更新合同设备信息
				payCancelDAO.updatePayDevInfo(pcBean);//更新设备付款信息
			}
		}
	}

	//预付款核销设备信息更新
	private void updateAdvCancelDevInfo(PayCancelBean payCancelBean) {
		String cntNum = payCancelBean.getCntNum();//合同号
		BigDecimal[] advSubIds = payCancelBean.getAdvSubIds();//子序号
		BigDecimal[] advancePayCancelAmts = payCancelBean.getAdvancePayCancelAmts();//取消的金额
		String payId = payCancelBean.getPayId();//付款单号
		String payType = "0";//付款类型
		CommonLogger.info("付款取消:预付款核销更新合同设备信息和更新设备付款信息（合同号："+payCancelBean.getCntNum()+",付款单号："+payCancelBean.getPayId()+"）,PayCancelService,updateCancelDevInfo");
		for(int i=0;i<advancePayCancelAmts.length;i++){
			if(advancePayCancelAmts[i].compareTo(new BigDecimal(0))!=0){
				PayCancelBean pcBean = new PayCancelBean();
				pcBean.setPayId(payId);
				pcBean.setCntNum(cntNum);
				pcBean.setPayType(payType);
				pcBean.setSubId(advSubIds[i]);
				pcBean.setDevCancelAmt(advancePayCancelAmts[i]);
				payCancelDAO.updateCntDevInfo(pcBean);//更新合同设备信息
				payCancelDAO.updatePayDevInfo(pcBean);//更新设备付款信息
			}
		}
		
	}

	//预付款核销信息更新
	private void updateAdvCancelInfo(PayCancelBean payCancelBean) {
		String payId = payCancelBean.getPayId();//正常付款单号
		String[] advancePayIds = payCancelBean.getAdvancePayIds();//预付款批次号（预付款单）
		BigDecimal[] preCancelAmts = payCancelBean.getPreCancelAmts();//预付款取消金额
		CommonLogger.info("付款取消:预付款核销更新（正常付款单号："+payCancelBean.getPayId()+",预付款单号："+payCancelBean.getAdvancePayId()+"）,PayCancelService,updateAdvCancelInfo");
		for(int i=0;i<preCancelAmts.length;i++){
			if(preCancelAmts[i].compareTo(new BigDecimal(0))!=0){
				PayCancelBean pcBean = new PayCancelBean();
				pcBean.setPayId(payId);
				pcBean.setAdvancePayId(advancePayIds[i]);
				pcBean.setCancelAmt(preCancelAmts[i]);
				payCancelDAO.updateAdvCancelInfo(pcBean);
			}
		}
	}
}
