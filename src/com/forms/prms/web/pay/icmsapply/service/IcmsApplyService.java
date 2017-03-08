package com.forms.prms.web.pay.icmsapply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.icmsapply.dao.IcmsApplyDAO;
import com.forms.prms.web.pay.icmsapply.domain.IcmsApplyBean;
import com.forms.prms.web.pay.paysure.dao.PaySureDao;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;

@Service
public class IcmsApplyService {
	@Autowired
	private IcmsApplyDAO icmsApplyDAO;
	
	/**
	 * 影像编辑申请信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<IcmsApplyBean> icmsApplyList(IcmsApplyBean icmsApplyBean) {
		CommonLogger.info("影像编辑申请列表查询，IcmsApplyService，icmsApplyList");
		IcmsApplyDAO pageDao = PageUtils.getPageDao(icmsApplyDAO);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		//String ouCode = WebHelp.getLoginUser().getOuCode();// 得到登录人的所属财务中心CODE
		icmsApplyBean.setOrg1Code(org1Code);
		//icmsApplyBean.setOuCode(ouCode);
		return pageDao.icmsApplyList(icmsApplyBean);
	}
	
	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsApplyBean getApplyByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"的明细,IcmsApplyService,getApplyByPayId");
		return icmsApplyDAO.getApplyByPayId(payId);
	}

	/**
	 * 通过预付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsApplyBean getPreApplyByPayId(String payId) {
		CommonLogger.info("查询预付款单号"+payId+"的明细,IcmsApplyService,getPreApplyByPayId");
		return icmsApplyDAO.getPreApplyByPayId(payId);
	}

	/**
	 * 通过预付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<IcmsApplyBean> getPreApplyDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的预付款采购设备,IcmsApplyService,getPreApplyDeviceListByPayId");
//		IcmsApplyDAO pageDao = PageUtils.getPageDao(icmsApplyDAO);
//		return pageDao.getPreApplyDeviceListByPayId(payId);
		return icmsApplyDAO.getPreApplyDeviceListByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<IcmsApplyBean> getApplyDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的正常付款采购设备,IcmsApplyService,getApplyDeviceListByPayId");
//		IcmsApplyDAO pageDao = PageUtils.getPageDao(icmsApplyDAO);
//		return pageDao.getApplyDeviceListByPayId(payId);
		return icmsApplyDAO.getApplyDeviceListByPayId(payId);
	}

	/**
	 * 通过合同号查找对应的核销集合
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<IcmsApplyBean> getPreApplyCancleListByCntNum(IcmsApplyBean bean) {
		CommonLogger.info("查询合同号"+bean.getCntNum()+"的核销集合,IcmsApplyService,getPreApplyCancleListByCntNum");
		IcmsApplyDAO pageDao = PageUtils.getPageDao(icmsApplyDAO);
		return pageDao.getPreApplyCancleListByCntNum(bean);
	}
	
	/**
	 * 得到登录人的ouCode
	 * @param org1Code
	 * @return
	 */
	public List<IcmsApplyBean>  ouCodeList(String org1Code){
		CommonLogger.info("查询一级行"+org1Code+"下的ouCode集合,IcmsApplyService,ouCodeList");
		return icmsApplyDAO.ouCodeList(org1Code);
	}
	
	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsApplyBean getApply(String payId, String icmsEdit) {
		CommonLogger.info("查询正常付款单号"+payId+"的信息,IcmsApplyService,getApply");
		return icmsApplyDAO.getApply(payId, icmsEdit);
	}
	
	// 确认预付款
	@Transactional(rollbackFor=Exception.class)
	public int agreePreApply(IcmsApplyBean bean) {
		String memo = "1".equals(bean.getIcmsEdit())?"撤销申请":"申请编辑";
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setNewIcmsEdit("1".equals(bean.getIcmsEdit())?"0":"1");
		CommonLogger.info(memo + "：（付款单号：" + bean.getPayId()+"），IcmsApplyService，agreePreApply");
		int cnt = icmsApplyDAO.agreePreApply(bean);
		CommonLogger.info(memo + "：（付款单号："+bean.getPayId()+"），的预付款log信息，IcmsApplyService，agreePreApply");
		bean.setAuditMemo(memo + ":" + bean.getAuditMemo());
		icmsApplyDAO.addLog(bean);
		return cnt;
	}

	// 确认付款
	@Transactional(rollbackFor=Exception.class)
	public int agreeApply(IcmsApplyBean bean) {
		String memo = "1".equals(bean.getIcmsEdit())?"撤销申请":"申请编辑";
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setNewIcmsEdit("1".equals(bean.getIcmsEdit())?"0":"1");
		CommonLogger.info(memo + "：（付款单号：" + bean.getPayId()+"），IcmsApplyService，agreeApply");
		int cnt = icmsApplyDAO.agreeApply(bean);
		CommonLogger.info(memo + "：（付款单号："+bean.getPayId()+"），的付款log信息，IcmsApplyService，agreeApply");
		bean.setAuditMemo(memo + "：" + bean.getAuditMemo());
		icmsApplyDAO.addLog(bean);
		return cnt;
	}

}
