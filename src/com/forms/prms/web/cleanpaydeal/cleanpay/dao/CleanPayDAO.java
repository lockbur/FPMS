package com.forms.prms.web.cleanpaydeal.cleanpay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;

/**
 * 
 * author : lisj <br>
 * date : 2015-03-12<br>
 * 暂收结清DAO
 */
@Repository
public interface CleanPayDAO {
	/**
	 * 查询可做暂收结清的列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public List<CleanPayBean> cleanpayList(CleanPayBean cleanPayBean);

	/**
	 * 正常付款暂收结清处理信息提交
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public int cleanPayDealSubmit(CleanPayBean cleanPayBean);

	/**
	 * 查询已结清列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryPayCleanInfo(CleanPayBean cleanPayBean);

	/**
	 * 已结清明细查询
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryCleanPayDetail(CleanPayBean cleanPayBean);

	/**
	 * 查询合同信息
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean constractInfo(CleanPayBean cleanPayBean);

	/**
	 * 查询付款信息
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryPayInfo(CleanPayBean cleanPayBean);

	/**
	 * 查询正在结清的总金额
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public String queryCleanAmtIng(CleanPayBean cleanPayBean);

	/**
	 * 生成暂收结清编号
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public String createCleanPayId(CleanPayBean cleanPayBean);

	/**
	 * 查询已结清列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public List<CleanPayBean> queryCleanedPayInfo(CleanPayBean cleanPayBean);

	/**
	 * (正常付款)暂收结清处理信息提交
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public int payCleanDealSaveOrSubmit(CleanPayBean cleanPayBean);

	/**
	 * 已结清明细查询
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryCleanedDetail(CleanPayBean cleanPayBean);
	/**
	 * 查询当前新增的子序号
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean QuerySortId(CleanPayBean cleanPayBean);

	/**
	 * 拿到当前付款单下的暂收信息
	 * @param normalPayId
	 * @return
	 */
	public List<CleanPayBean> getClenPayById(@Param("normalPayId")String normalPayId);

}
