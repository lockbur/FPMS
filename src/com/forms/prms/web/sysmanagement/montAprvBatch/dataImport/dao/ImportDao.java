package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.AprvChainBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontBean;
@Repository
public interface ImportDao {
	
	public List<ImportBean> shList(ImportBean eb);

	public List<ImportBean> fhList(ImportBean eb);
	
	public List<ImportBean> getDataList(ImportBean bean) ;

	public List<ImportBean> getBeanAprv(ImportBean bean) ;
	/**
	 * 得到主键
	 * @param bean
	 */
	public String getId(ImportBean bean);
	/**
	 * 添加汇总信息
	 * @param bean
	 */
	public void addData(ImportBean bean);
	/**
	 * 修改状态
	 * @param batchNo
	 * @param status
	 */
	public void updateExcelStatus(@Param("batchNo")String batchNo, @Param("statusPre")String status,@Param("statusNex")String status2);
	
	public void updateCntStatus(@Param("batchNo")String batchNo, @Param("statusPre")String status,@Param("statusNex")String status2);

	/**
	 * 监控指标明细表数据
	 * @param montBeans
	 */
	public void insertMontDetail(@Param("montBeans")List<MontBean> montBeans);
	/**
	 * 监控指标明细表数据
	 * @param montBeans
	 */
	public void insertMontDetailNext(@Param("montBeans")List<MontBean> montBeans);

	public void insertAprvDetail(@Param("aprvBeans")List<AprvChainBean> aprvBeans);
	
	/**
	 * 监控指标的校验
	 * @param batchNo
	 */
	public void checkMont(String batchNo);
	/**
	 * 检查已经存在的数据
	 * @param bean
	 * @return
	 */
	public List<MontBean> getExistData(ImportBean bean);
	/*8
	 * 删除汇总信息
	 */
	public void deleteBatch(ImportBean montBean);
	/**
	 * 删除明细信息
	 * @param montBean
	 */
	public void deleteMontDetail(ImportBean montBean);
	/**
	 * 删除明细信息
	 * @param montBean
	 */
	public void deleteAprvDetail(ImportBean montBean);
	/**
	 * 删除错误信息
	 * @param montBean
	 */
	public void deleteError(ImportBean montBean);
	/**
	 * 审批链存储过程校验
	 * @param batchNo
	 * @param org1Code
	 */
	public void checkAprv(@Param("batchNo")String batchNo);
	/**
	 * 用户职责信息导入校验存储过程
	 * @param batchNo
	 * @param org1Code 
	 */
	public void checkUserRoleRln(@Param("batchNo")String batchNo,@Param("isCheck")String isCheck,@Param("userId")String userId);

	public List<ImportBean> getErrData(ImportBean bean);

	public ImportBean getDetail(ImportBean bean);

	/**
	 * 分行可以导入哪些数据
	 * @param roleId 
	 * @param fhAuthList
	 * @return
	 */
	public List<Map<String, Object>> getAuthList(Map<String, Object> map);
	/**
	 * copy数据
	 * @param batchNo
	 */
	public void copyData(String batchNo);
	/**
	 * isExistMont
	 * @param bean
	 * @return
	 */
	public String isExistMont(ImportBean bean);

	public int ajaxCopyExists(ImportBean bean);

	public int isExistMontThisYear(ImportBean bean);

	public int isExistAprvThisYear(ImportBean bean);

	public List<ImportBean> getMontTypeList(ImportBean bean);

	public int isExistsMontType(ImportBean bean);

	public void updateError(@Param("batchNo")String batchNo, @Param("status")String status, @Param("memo")String message);
	/**
	 * 看是否有未完成的流程
	 * @param bean
	 * @return
	 */
	public int isHaveNotOver(ImportBean bean);

	/**
	 * 校验是否有带勾选的合同
	 * @param eb
	 * @return
	 */
	public int isHaveSplitCnt(ImportBean eb);

	public void deleteTaskLoad(ImportBean eb);

	public int checkBgtIsOver(ImportBean bean);

	public int isHaveNotOverNextYear(ImportBean bean);



}
