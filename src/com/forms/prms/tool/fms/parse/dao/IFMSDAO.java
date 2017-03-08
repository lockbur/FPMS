package com.forms.prms.tool.fms.parse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.web.sysmanagement.homepage.domain.ExcepInfoBean;

@Repository
public interface IFMSDAO {
	
	public String getSysdate(@Param("format")String format);
	
	public String getDownloadBatchNo(@Param("tradeType")String tradeType, @Param("tradeDate")String tradeDate);
	
	public List<Map<String, String>> getFmsDataList(UpLoadBean bean);
	
	public int updateFms(Map<String, Object> map);
	
	public List<UpLoadBean> getSummaryList(UpLoadBean bean);
	
	public int updateStatus(UpLoadBean bean);
	
	public int insertDetails(Map<String, Object> map);
//	public int insertDetails(FMSBean fms);
	
	public int insertSummary(FMSDownloadBean bean);
	
	public int updateDlStatus(FMSDownloadBean bean);

	public FMSDownloadBean getBathch(String tempFileName);

	public int updateSummary(FMSDownloadBean downloadBeana);

	public int insertCheckDetails(Map<String, Object> map);

	public FMSDownloadBean getFirstFailJob();

	public FMSDownloadBean getDownloadFileBymd5(String md5Str);

	public void deleteErrorLog(String tardeType);

	public void addErrorLog(@Param("tardeType")String tardeType, @Param("errlog")String errlog);

	public void delDwnByBatchNO(String batchNo);

	public void delDetailByBatchNO(@Param("batchNo")String batchNo,@Param("tableName")String tableName);

	public void insertExceInfo(ExcepInfoBean exceInfoBean);

	public int deleteSummary(FMSDownloadBean bean);

	public void deleteExceInfo(ExcepInfoBean exceInfoBean);

	/**
	 * 从check表进行批量更新
	 * @param fms
	 */
	public int updateBatchFms(FMSBean fms);

	/**
	 * 删除文件对应数据库的数据
	 * @param batchNo
	 */
	public int deleteCheckData(FMSBean fms);
	
	public int delCheckData(@Param("checkTableName")String checkTableName,@Param("batchNo")String batchNo);

	public String checkFileData(Map<String, String> param);


	/**
	 * 添加校验失败的信息
	 * @param updateBean
	 * @return
	 */
	public int addCheckLog(UpLoadBean updateBean);

	public int addDealLog(FMSDownloadBean bean);

	public int getDownLoadCount();

	public void initDownload();

	public void mergeDownload(FMSDownloadBean bean);

	public String getDealDate(Map<String,Object> map);

	public String getFirstTradeDate(Map<String,Object> map);

	public String getDealBatchNo(@Param("tradeDate")String tradeDate, @Param("tradeType")String tradeType);

	public void callProcedure(@Param("prcName")String prcName);

	public List<FMSDownloadBean> unsuccTask(String tradeDate);

	public void transTmpData(@Param("tranTmpSql")String tranTmpSql);
	
	public void truncateTmpData(@Param("truncateTmpSql")String truncateTmpSql);

	public int getLoadCount(@Param("tableNameTmp")String tableNameTmp);

	public void updateDownload(FMSDownloadBean bean);
	
	public void updateUploadDownPath(UpLoadBean bean);
	
	public void updateDownDownPath(FMSDownloadBean bean);

	public List<UpLoadBean> getUploadTask();

	public void delCleanLog(@Param("batchNo")String batchNo);

	public void insertCleanLog(@Param("batchNo")String batchNo, @Param("tableNameTmp")String tableNameTmp);

	public void delFormalTableData(@Param("batchNo")String batchNo, @Param("tableName")String tableName);
	
	public void updateCheckStatus(@Param("mergeSql")String mergeSql);

	public String getDataFlagByBatchNo(@Param("batchNo")String batchNo);

	public void downloadInitStatus(@Param("batchNo")String batchNo, @Param("fmsDownloadFordeal")String fmsDownloadFordeal);

	public void uploadInitStatus(@Param("batchNo")String batchNo);

	public void updateCostTime(@Param("batchNo")String batchNo,@Param("costMills")String costMills);

}
