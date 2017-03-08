package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntFqfkBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntMatrInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntRelPayInfo;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntTenancyCondiBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntVerifyBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayAdvanceBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayAdvanceCancelBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayDeviceBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.UploadDataControlInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.ZsysRoleBean;


@Repository
public interface DataMigrateDao {

	//【测试】-测试插入ZZZ_SYS_ROLE表
	public void dataMigrateToTb1(ZsysRoleBean zsysRole);
	
	
	//【存量业务数据】-插入合同数据信息-合同信息Sheet————UPLOAD_TD_CNT表
	public void addCntInfo(@Param("cntInfoList") List<CntInfoBean> cntInfoList);

	
	//【存量业务数据】-插入合同数据信息-合同物料信息Sheet————UPLOAD_TD_CNT_DEVICE表
	public void addCntDevice(@Param("cntMatrList") List<CntMatrInfoBean> cntMatrList);
	
	
	//【存量业务数据】-插入合同数据信息-合同分期付款信息Sheet————UPLOAD_TD_CNT_FK表
	public void addCntFk(@Param("cntFqfkList") List<CntFqfkBean> cntFqfkList);
	
	
	//【存量业务数据】-插入合同数据信息-合同租金递增条件Sheet————UPLOAD_TD_CNT_TENANCY_DZ表
	public void addCntTenancy(@Param("cntTenancyList") List<CntTenancyCondiBean> cntTenancyList);
	
	
	//【存量业务数据】-插入付款数据信息-Sheet1：预付款信息————UPLOAD_TD_PAY_ADVANCE表
	public void addTdPayAdvance(@Param("payAdvanceInfo") List<TdPayAdvanceBean> payAdvanceInfo);
	
	
	//【存量业务数据】-插入付款数据信息-Sheet2：正常付款信息————UPLOAD_TD_PAY表
	public void addTdPayInfo(@Param("payInfo") List<TdPayBean> payInfo);
	
	
	//【存量业务数据】-插入付款数据信息-Sheet3：预付款核销信息————UPLOAD_TD_PAY_ADVANCE_CANCEL表
	public void addTdPayAdvanceCancel(@Param("payAdvanceCancelInfo") List<TdPayAdvanceCancelBean> payAdvanceCancelInfo);
	
	
	//【存量业务数据】-插入付款数据信息-(Sheet4：预付款核销物料信息   / Sheet5：正常付款物料信息)————UPLOAD_TD_PAY_DEVICE表
	public void addTdPayDevice(@Param("paramObj") Map<String , Object> paramObj);
	
	
	//【导入公共执行方法】导入时，往表UPLOAD_DATA_CONTROL_INFO插入导入信息记录
	public void addUpDataControlInfo(UploadDataControlInfoBean upDataBean);
	
	
	//【导入公共执行方法】根据导入状况，更新导入状态
	public void updateUpDataConInfoDataFlag(@Param("dataFlag")String dataFlag , @Param("batchNo")String batchNo);
	
	//调用存储过程校验合同数据+付款数据
	public int callInitMainCheck(@Param("batchNo") String batchNo);
	
	//根据登录者一级行信息查找导入批次数据
	public UploadDataControlInfoBean queryUploadControlByOrg1Code(@Param("org1Code") String org1Code);
	
	//根据导入批次号查找该导入批次下的合同列表信息
	public List<CntVerifyBean> queryCntList(CntVerifyBean selectInfo);
	
	//调用存储过程实现根据批次号将该导入合同数据+付款数据从缓存表转移至系统生产表中；随后更新当次[确认]的操作信息等；
	public void callDataInitFlash(UploadDataControlInfoBean selectInfo);
	
	//创建获取导入批次号(根据序列生成)
	public String createImportBatchSeq();
	
	//根据导入批次号查找udcBean(UPLOAD_DATA_CONTROL_INFO表)对象
	public UploadDataControlInfoBean getUdcBeanByBatchNo(@Param("batchNo") String batchNo );
	
	//【删除】功能操作：根据导入批次号找到导入批次，将该批次的状态更改为"03"删除；并记录该删除操作的操作时间和操作者信息  
	public int dmExecDelOperByBatchNo(UploadDataControlInfoBean udcBean);

	//根据导入批次号获取改批次的数据状态
	public String getUpDataControllerDataFlagByBatchNo(@Param("batchNo") String batchNo);
	
	//根据(UPLOAD_DATA_CONTROL_INFO表中)导入批次其中一个TaskId查询导入批次对象UploadDataControlBean
	public UploadDataControlInfoBean getUpDataControlByOneTaskId(@Param("taskId") String taskId);
	
	//根据导入批次号batchNo在UPLOAD_DATA_ERROR_INFO表中查询校验错误信息
	public List<UploadDataErrorInfoBean> getUpDataErrByBatchNo(@Param("batchNo") String batchNo);
	
	//根据batchNo和uploadType查找对应的导入校验错误数据条数
	public String getUpDataErrCountByBatchAndUpType(@Param("batchNo") String batchNo , @Param("uploadType") String uploadType , @Param("sectionType") String sectionType);
	
	//根据batchNo查找CNT合同的通过基配校验的插入数据条数
	public String getCntSuccImpCountByBatchNo(@Param("batchNo") String batchNo);
	
	//根据batchNo查找Pay付款的通过基配校验的插入数据条数
	public String getPaySuccImpCountByBatchNo(@Param("batchNo") String batchNo);
	
	//通过导入批次号+合同号查询指定导入合同号的主体信息
	public CntVerifyBean getCntMainInfoByCntNum(CntVerifyBean cntVerifyBean);
	
	//通过导入批次号+合同号查询指定导入合同的相关联的合同内设备物料列表
	public List<CntMatrInfoBean> getCntRelDevices(CntVerifyBean cntVerifyBean);
	
	//根据导入批次号+合同号查询指定导入合同的相关联合同内租金递增条件(只有费用类合同并且费用子类为房屋租赁类才存在此信息)
	public List<CntTenancyCondiBean> getCntRelTenancies(CntVerifyBean cntVerifyBean);
	
	//通过导入批次号+合同号查询指定导入合同的相关付款信息(条件类型一个，进度和日期类型多个)
	public List<CntFqfkBean> getCntRelFqfkInfos(CntVerifyBean cntVerifyBean);
	
	//根据导入批次号+合同号，查找该合同下拥有的付款信息列表(正常付款+预付款)
	public List<CntRelPayInfo> getImpPayListByCntAndBatchNo(CntVerifyBean cntVerifyBean);
	
	//查询正常付款及其关联信息
	public TdPayBean getNormalPayDetail(CntRelPayInfo cntPayBean);
		//查询正常付款下相关的预付款核销信息
		public List<TdPayAdvanceCancelBean> getPayAdCancelByBatchAndPayId(CntRelPayInfo cntPayBean);
		//查询正常付款下相关的物料信息(正常+预付款核销)
		public List<TdPayDeviceBean> getPayDevByBatchAndPayId(CntRelPayInfo cntPayBean);
	
	//查询预付款及其关联信息
	public TdPayAdvanceBean getAdPayDetail(CntRelPayInfo cntPayBean);
}
