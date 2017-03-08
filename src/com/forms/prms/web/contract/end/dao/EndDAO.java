package com.forms.prms.web.contract.end.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.end.domain.EndForm;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;

@Repository
public interface EndDAO {
	/**
	 * 合同取消列表
	 * 
	 * @param form
	 * @return
	 */
	public List<EndForm> list(EndForm form);
	/**
	 * 合同终止列表
	 * 
	 * @param form
	 * @return
	 */
	public List<EndForm> endList(EndForm form);

	/**
	 * 合同终止
	 * 
	 * @param cntNum
	 * @return
	 */
	public int end(String cntNum);

	/**
	 * 合同完成
	 * 
	 * @param cntNum
	 * @return
	 */
	public int finish(String cntNum);
	
	/**
	 * @methodName updateProjAmt
	 * desc 合同完成 释放占用的项目预算
	 * 
	 * @param cntNum
	 * @return
	 */
	public int updateProjAmt(String cntNum);
	
	/**
	 * @methodName updateProjAmt
	 * desc  合同取消释放占用的项目预算
	 * 
	 * @param cntNum
	 * @return
	 */
	public int updateCancelProjAmt(String cntNum);
	
	public int releaseProjPayAmt(String cntNum);
	
	/**
	 * @methodName getEndAmt
	 * desc  获取合同的退款总和是否为0 
	 * 
	 * @param cntNum
	 * @return
	 */
	public EndForm getEndAmt(String cntNum);
	
	/**
	 * 合同完成（终止） 查看预提待摊合同剩余的待摊余额
	 * @param cntNum
	 * @return
	 */
	public BigDecimal getPrepaidRemainAmt(String cntNum);
	
	/**
	 * 更新一次待摊合同total表信息
	 * @param cntNum
	 * @return
	 */
	public int updatePrepaidStatus(String cntNum);

	/**
	 * 合同取消未发送fms的付款单状态全部置为“失效（AX）”状态）
	 * @param cntNum
	 * @return
	 */
	public int updatePayStatus(String cntNum);
	public int updatePrePayStatus(String cntNum);
	
	/**
	 * 合同取消释放预算
	 * @param param
	 * @return
	 */
	public String cntCancelFreeBgt(Map<String, String> param);

	public List<PayAddBean> getAxList(@Param("cntNum")String cntNum,@Param("userId")String userId);

	public void batchInsert(@Param("cntNum")String cntNum,@Param("userId")String userId);

	public List<String> dutyList(String cntNum);

	public void delBatchPay(String cntNum);

	public void updateOrdStatus(String cntNum);

	public List<OrderStartBean> getOrdList(String cntNum);
}
