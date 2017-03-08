package com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.domain.DutyMergeBean;

@Repository
public interface DutyMergeDao {
	/**
	 * 经办列表查询
	 * 
	 * @param bean
	 * @return
	 */
	List<DutyMergeBean> mergeHandleList(DutyMergeBean bean);

	/**
	 * 根据
	 * 
	 * @param bean
	 * @return
	 */
	DutyMergeBean getBean(DutyMergeBean bean);

	/**
	 * 经办新增
	 * 
	 * @param bean
	 * @return
	 */
	int handleAdd(DutyMergeBean bean);

	/**
	 * 经办删除
	 * 
	 * @param bean
	 * @return
	 */
	int handleDel(DutyMergeBean bean);

	/**
	 * 经办编辑
	 * 
	 * @param bean
	 * @return
	 */
	boolean edit(DutyMergeBean bean);

	/**
	 * 查询这个撤并的责任中心在批量表里有没有
	 * 
	 * @param codeBef
	 * @return
	 */
	String getCount(String codeBef);

	/**
	 * 关联信息
	 * 
	 * @param bean
	 * @return
	 */
	List<DutyMergeBean> glxx(DutyMergeBean bean);

	/**
	 * 1-2 2-3的新增
	 * 
	 * @param bean
	 * @return
	 */
	int handleAdd2(DutyMergeBean bean);

	int updateSeq(DutyMergeBean bean);

	/**
	 * 复核提交
	 * 
	 * @param bean
	 * @return
	 */
	int aprv(DutyMergeBean bean);

	/**
	 * 撤并前责任中心列表
	 * 
	 * @param bean
	 * @return
	 */
	List<DutyMergeBean> getMergeDutys(DutyMergeBean bean);

	/**
	 * 校验交叉表是否存在这个责任中心
	 * 
	 * @param dutyCode
	 * @param type
	 * @param isValid
	 * @return
	 */
	String existFndwrr(@Param("dutyCode") String dutyCode,
			@Param("type") String type);

	String existBatch(@Param("dutyCode") String dutyCode,
			@Param("type") String type);

	String isHaveMerge(String code);

	List<DutyMergeBean> selectGlgxList(DutyMergeBean bean2);

	/**
	 * 得到主键
	 * 
	 * @param bean
	 * @return
	 */
	String getId(DutyMergeBean bean);

	/**
	 * 查看ti_download 责任中心同步是否成功
	 * 
	 * @return
	 */
	String checkDownload12();

	/**
	 * 查看 tb_fndwrr_batch 处理状态
	 * 
	 * @param org1Code
	 * @return
	 */
	String checkBatchStatus(@Param("org1Code") String org1Code);

	/**
	 * 得到在这个责任中心失效前还没有完成撤并的责任中心
	 * 
	 * @param invalidDate
	 * @param type
	 * @return
	 */
	List<DutyMergeBean> getNoMergeDuty(
			@Param("invalidDate") String invalidDate, @Param("type") String type);

	/**
	 * 反向更新撤并的状态
	 * 
	 * @param code
	 * @param codeCur
	 * @param invalidDate
	 * @return
	 */
	boolean updateStatusForBatch(DutyMergeBean bean);

	/**
	 * 得到在这个责任中心失效前还没有完成撤并复核的责任中心
	 * 
	 * @param codeBef
	 * @return
	 */
	List<DutyMergeBean> getNoAprvDuty(@Param("invalidDate") String invalidDate);

	/**
	 * 检查编码是否存在
	 * 
	 * @param codeCur
	 * @param type
	 * @return
	 */
	int existsCode(@Param("code") String code, @Param("type") String type);

	/**
	 * 根据撤并前责任中心得到撤并的集合
	 * 
	 * @param bean
	 * @return
	 */
	List<DutyMergeBean> getInvalidDutys(DutyMergeBean bean);

	void updateStatusForDel(DutyMergeBean bean);

	/**
	 * 经办明细
	 * 
	 * @param bean
	 */
	void handleDetailAdd(DutyMergeBean bean);

	/**
	 * 待处理的撤并日期
	 * 
	 * @return
	 */
	public String getDealDate();

	/**
	 * 以下列表的数据日期
	 * 
	 * @return
	 */
	public DutyMergeBean getListDate();

	/**
	 * 查询经办列表
	 * 
	 * @param batchNo
	 * @return
	 */
	public List<DutyMergeBean> list(@Param("batchNo") String batchNo);

	/**
	 * 通过撤并后的查询
	 * 
	 * @param dutyMergeBean
	 * @return
	 */
	public DutyMergeBean queryFndwrr(DutyMergeBean dutyMergeBean);

	public int updateFndChangeDetail(DutyMergeBean dutyMergeBean);

	public int updateFndChange(DutyMergeBean dutyMergeBean);

	public List<DutyMergeBean> queryFndwrrChnageDetail(
			@Param("batchNo") String batchNo);

	public int updateStatus(DutyMergeBean bean);

	public DutyMergeBean getFndwrrChangeInfo(@Param("batchNo") String batchNo);

	/**
	 * 查询复核列表
	 * @param batchNo
	 * @return
	 */
	public List<DutyMergeBean> checkList(String batchNo);

	public DutyMergeBean queryDetail(DutyMergeBean bean);
	/**
	 * 删除锁定表的信息
	 * @param bean
	 */
	void deleteLock(DutyMergeBean bean);

	List<DutyMergeBean> getMergeList(DutyMergeBean bean);


	void callDutyMerge(@Param("codeBef")String codeBef, @Param("codeCur")String codeCur);

	void callOrgMerge(@Param("codeBef")String codeBef, @Param("codeCur")String codeCur);

	void updateBatch(DutyMergeBean bean);

	void updateFndBatch(DutyMergeBean dutyMergeBean);

	DutyMergeBean getDutyBean(DutyMergeBean dutyMergeBean);

	int update5IpAddress(DutyMergeBean bean);

}
