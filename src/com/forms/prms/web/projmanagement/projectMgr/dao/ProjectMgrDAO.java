package com.forms.prms.web.projmanagement.projectMgr.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.projmanagement.projectMgr.domain.Project;

@Repository
public interface ProjectMgrDAO {
	/**
	 * 查找项目类型选择列表
	 * 
	 * @return
	 */
	public List<Project> getProjectType(@Param("org1Code") String org1Code);

	/**
	 * 项目 列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> listProj(Project proj);

	/**
	 * 项目信息Option 列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> getProjOption(Project proj);

	/**
	 * 修改项目列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> listUpdProj(Project proj);

	/**
	 * 新增项目
	 * 
	 * @param proj
	 */
	public int addProj(Project proj);

	/**
	 * 新增项目版本
	 */
	public int addProjLog(Project proj);

	/**
	 * 查看项目详情
	 * 
	 * @param projId
	 * @return
	 */
	public Project veiwProj(String projId);

	/**
	 * 查询项目责任中心列表
	 * 
	 * @param projId
	 * @return
	 */
	public List<String> getDutyCodes(String projId);

	/**
	 * 更新项目修改信息
	 * 
	 * @param proj
	 */
	public int updProject(Project proj);

	/**
	 * 更新终止日期
	 * 
	 * @param proj
	 */
	public int updEndDate(Project proj);

	/**
	 * 校验预算金额
	 * 
	 * @param proj
	 * @return
	 */
	public Project checkAmt(Project proj);

	/**
	 * 插入项目与责任中心关系表
	 * 
	 * @param map
	 * @return
	 */
	public int addProjDept(Map<String, Object> map);

	/**
	 * 插入项目与责任中心关系表Log
	 * 
	 * @param map
	 * @return
	 */
	public int addProjDeptLog(Map<String, Object> map);

	/**
	 * 删除项目与责任中心关系表
	 * 
	 * @param projId
	 */
	public int deleteProjDept(String projId);

	/**
	 * 修改冻结金额
	 * 
	 * @param freezeTotalAmt
	 * @param projId
	 * @param taxNamt 
	 * @return
	 */
	public int updateFreezeAmt(@Param("freezeTotalAmt") BigDecimal freezeTotalAmt, @Param("projId") String projId,@Param("taxNamt")  BigDecimal taxNamt);

	/**
	 * 修改合同执行金额
	 * 
	 * @param cntTotalAmt
	 * @param projId
	 * @param taxNamt 
	 * @return
	 */
	public int updateCntAmt(@Param("cntTotalAmt") BigDecimal cntTotalAmt, @Param("projId") String projId, @Param("taxNamt")BigDecimal taxNamt);

	/**
	 * 修改已付款金额
	 * 
	 * @param payTotalAmt
	 * @param projId
	 * @return
	 */
	public int updatePayAmt(@Param("payTotalAmt") BigDecimal payTotalAmt, @Param("projId") String projId);

	/**
	 * 获取金额信息
	 * 
	 * @param projId
	 * @return
	 */
	public Project getAmtInfo(@Param("projId") String projId);

	/**
	 * @methodName getCntDeviceByOrder
	 * desc  根据订单号获取合同物料  
	 * 
	 * @param orderId
	 * @return
	 */
	public List<CntDevice> getCntDeviceByOrder(@Param("orderId")String orderId);

	/**
	 * @methodName orderBackFree
	 * desc  订单退回释放占用金额
	 * 
	 * @param projId
	 * @param execAmt
	 * @param bigDecimal 
	 */
	public int orderBackFree(@Param("projId")String projId, @Param("execAmt")BigDecimal execAmt, @Param("taxNamt")BigDecimal taxNamt);

	public List<Project> getProjOption1(Project proj);

}
