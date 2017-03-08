package com.forms.prms.web.amortization.provisionMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.provisionMgr.domain.ProvisionBean;

@Repository
public interface ProvisionDAO {
	
	//获取预提经办列表
	public List<ProvisionBean> listProvHandles(Map<String , Object> argsMap);
	
	//获取预提/待摊金额汇总
	public ProvisionBean getPPSumAmt(Map<String , Object> argsMap);

	//获取预算待复核列表
	public List<ProvisionBean> listProvRechecks(Map<String , Object> argsMap);
 
	//获取当前省份预提待摊处理中的任务年月
	public String getYYYYMM(@Param("org1Code")String org1Code);
	
	//根据合同号和受益年月从TD_PROVISION_MANAGE表中查询指定的ProvisionBean对象
	public ProvisionBean getProvByCntAndFeeYear(@Param("cntNum") String cntNum , @Param("feeYyyymm") String feeYyyymm);
	
	//更新一个前端提交过来的预算经办处理
	public int handleSubimit(ProvisionBean provisionBean);
	
	//预提复核通过与退回的Bean信息更改(其实相当于更新)
	public void recheckPassOrReturn(ProvisionBean provisionBean);
	
	//预提通过信息查询管理
	public List<ProvisionBean> getProvQueryList(Map<String , Object> argsMap);
	
	/**
	 * 获取预提待摊数据生成状态
	 * @return
	 */
	public String getPPStatus(@Param("org1Code")String org1Code,@Param("yyyymm")String yyyymm);
	
	/**
	 *  查询当年当月复核是否存在不通过
	 */
	public String getNotPass(@Param("org1Code")String org1Code,@Param("targetYYYYMM")String targetYYYYMM);
	
	/**
	 * 更新total表当月预提状态--不预提
	 */
	public void updateProviFlag(@Param("advCglList")String advCglList,@Param("targetYYYYMM")String targetYYYYMM);
	
	/**
	 * 更新total表下月冲销状态--不冲销
	 */
	public void updateProviCancelFlag(@Param("advCglList")String advCglList,@Param("targetYYYYMM")String targetYYYYMM);
	
	/**
	 * 将年末不进行预提的物料金额记录到表LACK_CNT_FEE
	 */
	public void addLackBgt(@Param("targetYYYYMM")String targetYYYYMM);
	
	/**
	 * 获取广告宣传费核算码列表
	 * @return
	 */
	public String getAdvCglList();
	
	/**
	 * 检查是否为存在回冲的二级行
	 * @param org2Code
	 * @return
	 */
	public String checkOrg2Ok(@Param("org2Code")String org2Code,@Param("targetYYYYMM")String targetYYYYMM);
	
	/**
	 * @methodName updateProvFuncTypeInSysWarn
	 * 		1.当预提经办通过后，根据CntNum和OrgCode更新SYS_WARN_PREPRO_INF的FUNC_TYPE为"T4"
	 * 		2.当预提复核退回后，根据CntNum和OrgCode更新SYS_WARN_PREPRO_INF的FUNC_TYPE为"T3"
	 * @param cntNum
	 * @param dutyCode
	 */
	public void updateProvFuncTypeInSysWarn(@Param("cntNum")String cntNum , @Param("org1Code")String org1Code , @Param("funcType")String funcType);
	
	//当预提通过后，根据CntNum和OrgCode删除SYS_WARN_PREPRO_INF的相关记录
	public void deleteProvFuncTypeInSysWarn(@Param("cntNum")String cntNum , @Param("org1Code")String org1Code);

	public String getDownloadCount(Map map);

	public List<ProvisionBean> queryDownloadList(Map map);
}
