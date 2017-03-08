package com.forms.prms.web.sysmanagement.montAprvBatch.apprv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain.ApprvBean;

@Repository
public interface ApprvDao {

	public List<ApprvBean> getList(ApprvBean apprvBean);
	
	public void audit(ApprvBean apprvBean);
	
	
	public ApprvBean getPath(@Param("batchNo") String batchNo);
	/*8
	 * 得到批次的所有监控指标名称
	 */
	public List<ApprvBean> getAllMontName(ApprvBean apprvBean);
	/**
	 * 更新临时表 的监控指标代码
	 * @param batchNo
	 * @param montName
	 * @param montCode
	 */
	public void updateMontCode(@Param("batchNo")String batchNo, @Param("montName")String montName, @Param("montCode")String montCode);
	/**
	 * 根据监控指标名称 修改监控指标代码
	 * @param apprvBean
	 */
	public void updateCodeByName(ApprvBean apprvBean);
	/**
	 * 拿出监控指标最大的
	 * @param apprvBean
	 * @return
	 */
	public String getMaxCode(ApprvBean apprvBean);
	/**
	 * copy数据审核通过
	 * @param apprvBean
	 */
	public void auditCopy(ApprvBean apprvBean);


	public boolean updateExcelStatus(@Param("batchNo")String batchNo, @Param("statusPre")String preStatus, @Param("statusNext")String status);
	public boolean updateCntStatus(@Param("batchNo")String batchNo, @Param("statusPre")String preStatus, @Param("statusNext")String status);
	/**
	 * 查找这一类型的数据是否存在
	 * @param apprvBean
	 * @return
	 */
	public String getCode(ApprvBean apprvBean);

	/**
	 * 复核合同勾选列表查询
	 * 
	 * @param apprvBean
	 * @return
	 */
	public List<ApprvBean> cntChooseList(ApprvBean apprvBean);

	/**
	 * 复核处理（非所有）
	 * @param apprvBean
	 * @return
	 */
	public int checkDeal(ApprvBean apprvBean);

	/**
	 * 复核处理同一批次下的所有的数据
	 * @param apprvBean
	 * @return
	 */
	public int checkDealAll(ApprvBean apprvBean);

	/**
	 * TBL_MONT_SPLIT表中这个批次下还01状态的数据
	 * @param apprvBean
	 * @return
	 */
	public List<ApprvBean> queryList(ApprvBean apprvBean);

	//public int addMontSplit(ApprvBean apprvBean);

	public int updateCntStatusC7(ApprvBean apprvBean);

	public int updateCntStatusC3(ApprvBean apprvBean);


	public int intoSplit(ApprvBean apprvBean);

	public int getCountCntSplit(ApprvBean apprvBean);

	public String getLastStatus(ApprvBean apprvBean);
	
	public boolean updateCntStatusMemo(@Param("batchNo")String batchNo, @Param("statusPre")String preStatus, @Param("statusNext")String status,@Param("memo")String memo);

	public void updateBack(ApprvBean apprvBean);

	public void updateIpAddress(@Param("batchNo")String batchNo, @Param("ipAddress")String ipAddress);

	public void updateC4AndIpAddress(@Param("batchNo")String batchNo, @Param("statusPre")String preStatus, @Param("statusNext")String status,@Param("ipAddress")String ipAddress);
	
	public boolean updateExcelStatusMemo(@Param("batchNo")String batchNo, @Param("statusPre")String preStatus, @Param("statusNext")String status,@Param("memo")String memo);



}
