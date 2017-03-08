package com.forms.prms.web.pay.icmsedit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.icmsedit.dao.IcmsEditDAO;
import com.forms.prms.web.pay.icmsedit.domain.IcmsEditBean;

@Service
public class IcmsEditService {
	@Autowired
	private IcmsEditDAO icmsEditDAO;
	
	/**
	 * 影像编辑申请信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<IcmsEditBean> icmsEditList(IcmsEditBean icmsEditBean) {
		CommonLogger.info("影像编辑申请列表查询，IcmsEditService，icmsEditList");
		IcmsEditDAO pageDao = PageUtils.getPageDao(icmsEditDAO);
		//20160822 drf P606 仅针对本ou下所有已发出“影像编辑申请”的付款单进行影像编辑操作；
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		String ouCode = WebHelp.getLoginUser().getOuCode();// 得到登录人的所属财务中心CODE
		icmsEditBean.setOrg1Code(org1Code);
		icmsEditBean.setOuCode(ouCode);	
		icmsEditBean.setInstOper(WebHelp.getLoginUser().getUserId());
		return pageDao.icmsEditList(icmsEditBean);
	}
	
	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsEditBean getEditByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"的明细,IcmsEditService,getEditByPayId");
		return icmsEditDAO.getEditByPayId(payId);
	}

	/**
	 * 通过预付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsEditBean getPreEditByPayId(String payId) {
		CommonLogger.info("查询预付款单号"+payId+"的明细,IcmsEditService,getPreEditByPayId");
		return icmsEditDAO.getPreEditByPayId(payId);
	}

	/**
	 * 通过预付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<IcmsEditBean> getPreEditDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的预付款采购设备,IcmsEditService,getPreEditDeviceListByPayId");
		IcmsEditDAO pageDao = PageUtils.getPageDao(icmsEditDAO);
		return pageDao.getPreEditDeviceListByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<IcmsEditBean> getEditDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的正常付款采购设备,IcmsEditService,getEditDeviceListByPayId");
//		IcmsEditDAO pageDao = PageUtils.getPageDao(icmsEditDAO);
//		return pageDao.getEditDeviceListByPayId(payId);
		return icmsEditDAO.getEditDeviceListByPayId(payId);
	}

	/**
	 * 通过合同号查找对应的核销集合
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<IcmsEditBean> getPreEditCancleListByCntNum(IcmsEditBean bean) {
		CommonLogger.info("查询合同号"+bean.getCntNum()+"的核销集合,IcmsEditService,getPreEditCancleListByCntNum");
//		IcmsEditDAO pageDao = PageUtils.getPageDao(icmsEditDAO);
//		return pageDao.getPreEditCancleListByCntNum(bean);
		return icmsEditDAO.getPreEditCancleListByCntNum(bean);
	}
	
	/**
	 * 得到登录人的ouCode
	 * @param org1Code
	 * @return
	 */
	public List<IcmsEditBean>  ouCodeList(String org1Code){
		CommonLogger.info("查询一级行"+org1Code+"下的ouCode集合,IcmsEditService,ouCodeList");
		return icmsEditDAO.ouCodeList(org1Code);
	}
	
	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public IcmsEditBean getEdit(String payId, String icmsEdit) {
		CommonLogger.info("查询正常付款单号"+payId+"的信息,IcmsEditService,getEdit");
		return icmsEditDAO.getEdit(payId, icmsEdit);
	}
	
}
