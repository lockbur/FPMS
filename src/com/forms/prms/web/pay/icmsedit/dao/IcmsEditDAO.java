package com.forms.prms.web.pay.icmsedit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.icmsedit.domain.IcmsEditBean;

/**
 * author : xf <br>
 * date : 2016-04-05<br>
 * 影像编辑申请DAO
 */
@Repository
public interface IcmsEditDAO {

	/**
	 * 影像编辑申请列表查询
	 * 
	 * @param IcmsEditBean
	 * @return
	 */
	public List<IcmsEditBean> icmsEditList(IcmsEditBean icmsEditBean);

	public List<IcmsEditBean> ouCodeList(String org1Code);

	IcmsEditBean getEditByPayId(String payId);// 查找 付款单号的所有信息

	IcmsEditBean getPreEditByPayId(String payId);// 查找 预付款单号的所有信息

	public List<IcmsEditBean> getPreEditDeviceListByPayId(String payId);

	public List<IcmsEditBean> getEditDeviceListByPayId(String payId);

	public List<IcmsEditBean> getPreEditCancleListByCntNum(IcmsEditBean bean);
	
	IcmsEditBean getEdit(String payId, String icmsEdit);// 查找 付款单号的所有信息
}
