package com.forms.prms.web.pay.icmsapply.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.icmsapply.domain.IcmsApplyBean;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;

/**
 * author : xf <br>
 * date : 2016-04-05<br>
 * 影像编辑申请DAO
 */
@Repository
public interface IcmsApplyDAO {

	/**
	 * 影像编辑申请列表查询
	 * 
	 * @param IcmsApplyBean
	 * @return
	 */
	public List<IcmsApplyBean> icmsApplyList(IcmsApplyBean icmsApplyBean);

	public List<IcmsApplyBean> ouCodeList(String org1Code);

	IcmsApplyBean getApplyByPayId(String payId);// 查找 付款单号的所有信息

	IcmsApplyBean getPreApplyByPayId(String payId);// 查找 预付款单号的所有信息

	public List<IcmsApplyBean> getPreApplyDeviceListByPayId(String payId);

	public List<IcmsApplyBean> getApplyDeviceListByPayId(String payId);

	public List<IcmsApplyBean> getPreApplyCancleListByCntNum(IcmsApplyBean bean);
	
	public int agreePreApply(IcmsApplyBean bean);

	public int agreeApply(IcmsApplyBean bean);
	
	public void addLog(IcmsApplyBean bean);// 增加操作日志
	
	IcmsApplyBean getApply(String payId, String icmsEdit);// 查找 付款单号的所有信息
}
