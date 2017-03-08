package com.forms.prms.web.pay.scan.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.icms.dao.ScanDAO;
import com.forms.prms.web.icms.domain.ScanBean;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.pay.payexamine.dao.PayExamineDao;
import com.forms.prms.web.pay.payexamine.domain.PayExamineBean;
import com.forms.prms.web.pay.payexamine.service.PayExamineService;
import com.forms.prms.web.pay.scan.dao.PayScanDAO;
import com.forms.prms.web.pay.scan.domain.PayScanBean;

@Service
public class PayScanService {
	@Autowired
	private PayScanDAO dao;
	@Autowired
	private ScanDAO scanDAO;
	
	@Autowired
	private PayCommonService payCommonService;
	
	@Autowired
	private PayExamineService payExamineService;
	
	@Autowired
	private PayExamineDao payExamineDao;
	
	@Transactional(rollbackFor=Exception.class)
	public void insertBatchDetail(PayScanBean payScanBean){
		int cnt = 0;
		String[] payIds = payScanBean.getPayIds().split("&&"); 
		String[] attachCnts = payScanBean.getAttachCnts().split("&&"); 
		String[] icmsPkuuids = payScanBean.getIcmsPkuuids().split("&&"); 
		payScanBean.setDataFlag("01");
		//payScanBean.setMainCnt(""+payIds.length);
		CommonLogger.info("扫描清空TI_ICMS_PKUUID中批次号"+payScanBean.getBatchNo()+"的数据,PayScanService,insertBatchDetail");
		//再次上传前先清空明细
		dao.clearBatchDetail(payScanBean);
		CommonLogger.info("添加扫描TI_ICMS_PKUUID中批次号"+payScanBean.getBatchNo()+"的数据,PayScanService,insertBatchDetail");
		for(int i=0;i<payIds.length;i++){
			payScanBean.setInnerId(""+(i+1));
			payScanBean.setPayId(payIds[i]);
			payScanBean.setIcmsPkuuid(icmsPkuuids[i]);
			payScanBean.setAttachCnt(attachCnts[i]);
			cnt += Integer.valueOf(attachCnts[i]) + 1;
			dao.insertBatchDetail(payScanBean);
		}
		payScanBean.setMainCntOk(String.valueOf(payIds.length));
		payScanBean.setAttachCntOk(String.valueOf(cnt));
		CommonLogger.info("扫描更新TD_BATCH_ICMS_HEADER中批次号"+payScanBean.getBatchNo()+"的数据,PayScanService,insertBatchDetail");
		dao.updateBatchGeneral(payScanBean);
		CommonLogger.info("扫描调用过程PRC_PAY_SCAN_VALIDATE校验数据,PayScanService,insertBatchDetail");
		dao.validate(payScanBean);
	}
	public void updateBatchGeneral(PayScanBean payScanBean){
		CommonLogger.info("扫描更新TD_BATCH_ICMS_HEADER中批次号"+payScanBean.getBatchNo()+"的数据,PayScanService,updateBatchGeneral");
		dao.updateBatchGeneral(payScanBean);
	}
	@Transactional(rollbackFor=Exception.class)
	public void updateBatchDetail(PayScanBean payScanBean){
		String[] payIds = payScanBean.getPayIds().split("&&"); 
		String[] flags = payScanBean.getFlags().split("&&"); 
		String[] errorMsgs = payScanBean.getErrorMsgs().split("&&"); 
		String[] icmsPkuuids = payScanBean.getIcmsPkuuids().split("&&"); 
		ScanBean scanBean = new ScanBean();
		scanBean.setInstOper(WebHelp.getLoginUser().getUserId());
		//payScanBean.setDataFlag("01");
		payScanBean.setMainCnt(""+payIds.length);
		for(int i=0;i<payIds.length;i++){
			payScanBean.setInnerId(""+(i+1));
			payScanBean.setPayId(payIds[i]);
			//00-校验错误(付款单状态不变) 01-校验正确(付款单状态不变) 02-上传成功（待扫描复核） 03-上传失败（付款单状态不变）  04-扫描通过（付款单状态变为D0） 05-扫描退回（付款单状态变为AC）
			payScanBean.setDataFlag("0000".equals(flags[i])?"02":"03");
			//上传成功的mergePKUUID表 -- edit by drf: 应该待扫描复核通过后，再更新至ti_icms_pkuuid表中。
			if("0000".equals(flags[i])){
				scanBean.setId(payIds[i]);
				scanBean.setIcmsPkuuid(icmsPkuuids[i]);
			    //scanDAO.mergeUUID(scanBean);
			}
			payScanBean.setMemo(errorMsgs[i]);
			CommonLogger.info("扫描更新TD_BATCH_ICMS_DETAIL中批次号"+payScanBean.getBatchNo()+"和付款单号"+payScanBean.getPayId()+"的数据,PayScanService,updateBatchDetail");
			dao.updateBatchDetail(payScanBean);
			
			//将本批次下的付款单状态全部更新为 D0-待确认
			PayExamineBean bean = new PayExamineBean();
			bean.setInstOper(WebHelp.getLoginUser().getUserId());
			bean.setBatchNo(payScanBean.getBatchNo());
			bean.setPayId(payIds[i]);
			//根据倒数第7位判断是正常付款（1）还是预付款（0）
			String payId = bean.getPayId();
			char idx  = StringUtils.reverse(payId).charAt(6);
			bean.setIsPrePay(idx=='0'?"Y":"N");
			bean.setIcmsPkuuid(icmsPkuuids[i]);
			payExamineService.scanAgree(bean);
			
			/* edit by drf 20151209: 无需统计，待页面复核后再统计
			//查询付款单的责任中心及一级行
			PaySureBean pBean = dao.queryPay(payIds[i]);
			//如果上传成功则进行统计
			if("02".equals(payScanBean.getDataFlag())){
				//查付款单所在的责任中心的一级行
				String org1Code = pBean.getOrg1Code();
				//添加到待确认
				CommonLogger.info("扫描上传成功添加付款单号"+payScanBean.getPayId()+"的数据到统计表中,PayScanService,updateBatchDetail");
				payCommonService.addSysWarnPayInfo(payIds[i],org1Code,"P3");
			}
			//如果上传失败进行统计
			if("03".equals(payScanBean.getDataFlag())){
				//查付款单所在的责任中心
				String dutyCode = pBean.getInstDutyCode();
				//添加到待修改
				CommonLogger.info("扫描上传失败添加付款单号"+payScanBean.getPayId()+"的数据到统计表中,PayScanService,updateBatchDetail");
				payCommonService.addSysWarnPayInfo(payIds[i],dutyCode,"P0");
			}*/
		}
		//00-待扫描 01-扫描处理中 02-待复核 03-处理完毕
		//payScanBean.setDataFlag("02");
		CommonLogger.info("扫描更新TD_BATCH_ICMS_HEADER中批次号"+payScanBean.getBatchNo()+"的数据,PayScanService,updateBatchDetail");
	    //dao.updateBatchGeneral(payScanBean);
		payExamineDao.updateScanBatchDataFlag(payScanBean.getBatchNo());
		/*
		//更新上传成功的付款单
		CommonLogger.info("扫描上传成功更新付款单号"+payScanBean.getPayId()+"的数据的状态,PayScanService,updateBatchDetail");
		dao.updatePay(payScanBean);
		dao.updatePayAdvance(payScanBean);
		//更新上传失败的付款单
		CommonLogger.info("扫描上传失败更新付款单号"+payScanBean.getPayId()+"的数据的状态,PayScanService,updateBatchDetail");
		dao.updatePayFail(payScanBean);
		dao.updatePayAdvanceFail(payScanBean);
		*/
	}
	
	public List<PayScanBean> selectBatchDetail(PayScanBean payScanBean){
		CommonLogger.info("查询TD_BATCH_ICMS_DETAIL中批次号"+payScanBean.getBatchNo()+"的明细数据,PayScanService,selectBatchDetail");
		return dao.selectBatchDetail(payScanBean);
	}
	
	public String findDutyCode(String payId){
		CommonLogger.info("查询付款单号"+payId+"对应的dutyCode,PayScanService,findDutyCode");
		return dao.findDutyCode(payId);
	}
	
	// 确认预付款
	@Transactional(rollbackFor=Exception.class)
	public int agreePreEdit(PayScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("影像编辑重新上传成功（付款单号：" + bean.getPayId()+"），ScanService，agreePreEdit");
		int cnt = dao.agreePreEdit(bean);
		CommonLogger.info("影像编辑重新上传成功（付款单号："+bean.getPayId()+"），的预付款log信息，ScanService，agreePreEdit");
		bean.setAuditMemo("影像编辑重新上传成功");
		dao.addLog(bean);
		return cnt;
	}
	
	// 确认付款
	@Transactional(rollbackFor=Exception.class)
	public int agreeEdit(PayScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("影像编辑重新上传成功（付款单号：" + bean.getPayId()+"），ScanService，agreeEdit");
		int cnt = dao.agreeEdit(bean);
		CommonLogger.info("影像编辑重新上传成功（付款单号："+bean.getPayId()+"），的付款log信息，ScanService，agreeEdit");
		bean.setAuditMemo("影像编辑重新上传成功");
		dao.addLog(bean);
		return cnt;
	}
	
}
