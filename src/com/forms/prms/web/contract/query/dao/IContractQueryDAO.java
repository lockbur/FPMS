package com.forms.prms.web.contract.query.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.ContractBean;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.domain.WaterBook;

@Repository
public interface IContractQueryDAO {
	
	/** 
	 * @methodName queryList
	 * desc  获取合同列表信息
	 * 
	 * @param map 查询条件及责任中心
	 */
	List<QueryContract> queryList(Map<String, Object> map);

	/** 
	 * @methodName getDetail
	 * desc  获取合同信息
	 * 
	 * @param cntNum 合同编号
	 */
	QueryContract getDetail(String cntNum);

	/** 
	 * @methodName getCntProj
	 * desc   获取物料信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<CntDevice> getCntProj(String cntNum);

	/** 
	 * @methodName getDZSPProj
	 * desc   获取电子审批信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<DzspInfo> getDZSPProj(String cntNum);

	/** 
	 * @methodName getTcyDz
	 * desc   获取租金递增信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<TenancyDz> getTcyDz(String cntNum);

	/** 
	 * @methodName getOnSchedule
	 * desc   获取按进度付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<StageInfo> getOnSchedule(String cntNum);

	/** 
	 * @methodName getOnDate
	 * desc   获取按日期付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<StageInfo> getOnDate(String cntNum);

	/** 
	 * @methodName getOnTerm
	 * desc   获取按条件分期付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	List<StageInfo> getOnTerm(String cntNum);
	
	/** 
	 * @methodName getDownloadCount
	 * desc   获取查询下载的总数，控制导出写文件量
	 * 
	 * @param map 
	 */
	String getDownloadCount(Map<String, Object> map);
	
	/** 
	 * @methodName queryDownloadList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	List<QueryContract> queryDownloadList(Map<String, Object> map);
	
	/** 
	 * @methodName queryDownloadList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	List<QueryContract> szGetDownloadList(Map<String, Object> map);

	/**
	 * @methodName queryCntDevice
	 * desc 查询受益费用页面  
	 * 
	 * @param con
	 * @return
	 */
	List<CntDevice> queryCntDevice(@Param("cntNum") String cntNum);
	
	/**
	 * @methodName book
	 * desc 查看操作日志
	 * 
	 * @param cntNum
	 * @return
	 */

	List<WaterBook> book(String cntNum);

	List<String> getCntProj1(String cntNum);

	List<QueryContract> queryRelevanceList(HashMap<String, Object> paramMap);
	
	List<QueryContract> szQueryList(HashMap<String, Object> paramMap);
	
	List<ContractBean> selectCntDataFlag();
}
