package com.forms.prms.web.sysmanagement.montindex.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;

@Repository
public interface MontIndexDao {

	public List<MontIndexBean> shList(MontIndexBean bean);// 查看省行监控指标

	public List<MontIndexBean> fhList(MontIndexBean bean);// 查看分行监控指标

	public List<MontIndexBean> fyzcList(MontIndexBean bean);

	public List<MontIndexBean> fyList(MontIndexBean bean);

	public List<MontIndexBean> zcList(MontIndexBean bean);

	public String checkMont(MontIndexBean bean);// 查找监控指标是否存在

	/**
	 * 查询监控指标的个数
	 * 
	 * @param bean
	 * @return
	 */
	public String selectCount(MontIndexBean bean);

	/**
	 * 新增监控指标
	 * 
	 * @param bean
	 * @return
	 */
	public int addMont(MontIndexBean bean);

	/**
	 * 增加监控物料指标
	 * 
	 * @return
	 */
	public int addMontMatr(MontIndexBean bean);

	/**
	 * 增加日志信息表
	 * 
	 * @param bean
	 * @return
	 */
	public int addMontMatrLog(MontIndexBean bean);

	/**
	 * 根据监控指标code查找物料列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> getMontMatrByMontCode(MontIndexBean bean);

	/**
	 * 根据监控指标code得到详细信息
	 * 
	 * @param bean
	 * @return
	 */
	public MontIndexBean getMontInfoByMontCode(MontIndexBean bean);

	/**
	 * 删除物料
	 * 
	 * @param bean
	 * @return
	 */
	public int delMatr(MontIndexBean bean);

	/**
	 * 单个删除时的日志
	 * 
	 * @param bean
	 * @return
	 */
	public int addLogForDel(MontIndexBean bean);

	/**
	 * 删除监控指标
	 * 
	 * @param bean
	 * @return
	 */
	public int delMont(MontIndexBean bean);

	/**
	 * 删除监控指标和物料
	 * 
	 * @param bean
	 * @return
	 */
	public int delMontMatr(MontIndexBean bean);

	/**
	 * 批量删除时的日志
	 * 
	 * @param bean
	 * @return
	 */
	public int addLogForBatchDel(MontIndexBean bean);

	/**
	 * 修改监控指标
	 * 
	 * @param bean
	 * @return
	 */
	public int editMont(MontIndexBean bean);

	/**
	 * 检测监控指标是否存在于关联表中
	 * 
	 * @param bean
	 * @return
	 */
	public MontIndexBean selectCheckBean(MontIndexBean bean);

	/**
	 * 监控指标查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> montList(MontIndexBean bean);

	/**
	 * 监控指标历史表查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> montHisList(MontIndexBean bean);

	public List<MontIndexBean> selectProjType(MontIndexBean bean);

//	public List<MontIndexBean> shHisList(MontIndexBean bean);

//	public List<MontIndexBean> fhHisList(MontIndexBean bean);

	public String getYear();

	public List<MontIndexBean> allMontList(MontIndexBean bean);

	/**
	 * 删除专向包审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delApproveChainSpec(MontIndexBean bean);

	/**
	 * 删除非专向包审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delApproveChainNoSpec(MontIndexBean bean);

	/**
	 * 删除专向包某一个物料的审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delApproveChainSpecMatr(MontIndexBean bean);

	/**
	 * 删除非专向包某一个物料的审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delApproveChainNoSpecMatr(MontIndexBean bean);

	/**
	 * 修改监控指标增加监控物料指标
	 * 
	 * @return
	 */
	public int editAddMontMatr(MontIndexBean bean);

	/**
	 * 删除没有传到后台的已维护物料
	 * 
	 * @param bean
	 * @return
	 */
	public int delNotExistMatr(MontIndexBean bean);

	/**
	 * 删除没有传到后台的已维护物料对应的专向包中审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delNotExistApproveChainSpec(MontIndexBean bean);

	/**
	 * 删除没有传到后台的已维护物料对应的非专向包中审批链
	 * 
	 * @param bean
	 * @return
	 */
	public int delNotExistApproveChainNoSpec(MontIndexBean bean);

	/**
	 * 根据监控指标代码检测是否存在于合同设备表
	 * 
	 * @param montCode
	 * @return
	 */
	public String checkMontCode(@Param("montCode")String montCode, @Param("matrCode")String matrCode);
	public String checkMontIsBud(@Param("montCode")String montCode, @Param("matrCode")String matrCode);

	public List<MontIndexBean> montFutList(MontIndexBean bean);
	/**
	 * 查询当年的监控指标
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> selectMontThisYear(MontIndexBean bean);

	public List<MontIndexBean> selectHis(MontIndexBean bean);

	public String preAddIsTrue(MontIndexBean bean);

	public List<MontIndexBean> getCglCodeList();

	public List<MontIndexBean> getMontMatrByMontCodeInvalid(MontIndexBean bean);

	public void changeValid(MontIndexBean bean);

	public void deleteByMontCode(MontIndexBean bean);

	public int selectImpBatch(MontIndexBean bean);

	public void updateMont(MontIndexBean bean);

	public MontIndexBean getOrg21Name(@Param("org2Code")String org2Code);

	public List<String> getSelectMatrs(String montCode);

	public void delAprvChain(MontIndexBean bean);



}
