package com.forms.prms.web.amortization.fmsMgr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.fmsMgr.domain.DealCountBean;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsCglBatch;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsDownload;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsMgr;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsUpload;

@Repository
public interface FmsMgrDAO {
	
	public FmsMgr getPayCntInfo(@Param("org1Code")String org1Code,@Param("monDataFlag")String monDataFlag);
	
	//public FmsMgr getAdvCntInfo(@Param("org1Code")String org1Code);
	
	public FmsMgr getOrderCntInfo(@Param("org1Code")String org1Code);
	
	public List<FmsDownload> getFmsDownloadList(FmsDownload bean);

	public FmsDownload getFmsDownload(FmsDownload bean);

	public List<FmsUpload> getFmsUploadList(FmsUpload bean);

	public FmsUpload getFmsUpload(FmsUpload bean);
	
	public List<FmsCglBatch> getCglBatchList(FmsCglBatch bean);
	
	public String check31Upload(@Param("org1Code")String org1Code);
	
	public FmsCglBatch checkProvision(@Param("org1Code")String org1Code);
	
	public String checkProvisionMonth(@Param("yyyyMM")String yyyyMM);
	
	public String checkPPMonth(@Param("yyyyMM")String yyyyMM);

	/**
	 * 查询log日志信息
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String querLog(@Param("batchNo")String batchNo, @Param("tradeType")String tradeType, @Param("tableName")String tableName);

	public List<FmsMgr> getPaySumAmt(@Param("org1Code")String org1Code, @Param("monDataFlag")String monDataFlag);

	public DealCountBean getDealCount(String org1Code);

	public List<String> getSucResult();

	public List<String> getCheckFileList();
	
	public void initCglBatchPriv(@Param("org1Code")String org1Code);
	
	public void initCglBatchPP(@Param("org1Code")String org1Code);

	public List<FmsUpload> getOuList(FmsUpload bean);
}
