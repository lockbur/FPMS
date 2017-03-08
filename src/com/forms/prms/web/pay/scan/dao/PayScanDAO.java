package com.forms.prms.web.pay.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.icms.domain.ScanBean;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;
import com.forms.prms.web.pay.scan.domain.PayScanBean;

@Repository
public interface PayScanDAO {
	public void clearBatchDetail(PayScanBean payScanBean);
	public void clearBatchHeader(PayScanBean payScanBean);
	public void insertBatchDetail(PayScanBean payScanBean);
	public void updateBatchGeneral(PayScanBean payScanBean);
	public void updateBatchDetail(PayScanBean payScanBean);
	public List<PayScanBean> selectBatchDetail(PayScanBean payScanBean);
	public void validate(PayScanBean payScanBean);
	public void updatePay(PayScanBean payScanBean);
	public void updatePayAdvance(PayScanBean payScanBean);
	public String findDutyCode(@Param("payId")String payId);
	/**
	 * 查询付款单的责任中心及一级行
	 * @param string
	 * @return
	 */
	public PaySureBean queryPay(String payId);
	public void updatePayFail(PayScanBean payScanBean);
	public void updatePayAdvanceFail(PayScanBean payScanBean);
	
	public int agreePreEdit(ScanBean bean);
	public int agreeEdit(ScanBean bean);
	public void addLog(ScanBean bean);// 增加操作日志
}
