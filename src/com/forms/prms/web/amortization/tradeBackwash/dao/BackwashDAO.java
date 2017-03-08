package com.forms.prms.web.amortization.tradeBackwash.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.tradeBackwash.domain.BackwashBean;

@Repository
public interface BackwashDAO {
	
	/**
	 * 获取可回冲的合同列表
	 * @param org1Code
	 * @return
	 */
	public List<BackwashBean> getBackWashList(BackwashBean bean);
	
	/**
	 * 查询是否存在停留在预提复核状态的数据
	 * @param bean
	 * @return
	 */
	public String getTradeStatus(BackwashBean bean);
	
	/**
	 * 获取当月预提待摊状态
	 * @param org1Code
	 * @return
	 */
	public String getProPreStatus(@Param("org1Code")String org1Code);
	
	
	/**
	 * 回冲处理
	 * @param org1Code
	 * @param cntNumList
	 */
	public int backWash(@Param("org1Code")String org1Code,@Param("cntNumList")String cntNumList);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public int addTradeBackWash(BackwashBean bean);
	
	/**
	 * 获取回冲历史列表
	 * @param bean
	 * @return
	 */
	public List<BackwashBean> getBackWashHistory(BackwashBean bean);
	
	/**
	 * 检查预提待摊上传文件是否已经回了校验文件
	 * @param org1Code
	 * @return
	 */
	public String check33Upload(@Param("org1Code")String org1Code);

	/**
	 * 回冲预算
	 * @param cntNum
	 */
	public void backWashBgt(Map<String, String> param);
	
	/**
	 * 查看当前任务是否为预提待摊任务
	 * @param org1Code
	 * @return
	 */
	public String getTaskTypeStatus(@Param("org1Code")String org1Code);
	
	/**
	 * 预提待摊任务时间是否为系统当前年月
	 * @param org1Code
	 * @return
	 */
	public String isSysYYYYMM(@Param("org1Code")String org1Code);
	
	
}
