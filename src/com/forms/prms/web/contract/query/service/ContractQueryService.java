package com.forms.prms.web.contract.query.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.ContractBean;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.modify.service.ContractModifyService;
import com.forms.prms.web.contract.query.dao.IContractQueryDAO;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.domain.WaterBook;
import com.forms.prms.web.pay.orderquery.dao.OrderQueryDao;
import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;

@Service
public class ContractQueryService {

	@Autowired
	private IContractQueryDAO dao;
	
	//公共Excel导出更新dao
	@Autowired
	private ICommonExcelDealDao excelDao;
	
	@Autowired
	private ExcelExportGenernalDeal exportDeal;
	
	@Autowired
	private ContractModifyService contractModifyService;
	
	@Autowired
	private OrderQueryDao  orderDao;
	
	
	public static ContractQueryService getInstance(){
		return SpringUtil.getBean(ContractQueryService.class);
	}

	/** 
	 * @methodName queryList
	 * desc  获取合同列表信息
	 * 
	 * @param con 查询条件
	 */
	public List<QueryContract> queryList(QueryContract con) {
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("org1Code", org1Code);
		paramMap.put("org2Code", org2Code);
		
		List<QueryContract> cntList = null;
		IContractQueryDAO pageDao = PageUtils.getPageDao(dao);
		if(!Tool.CHECK.isBlank(con.getCntNum()) && "1".equals(con.getFlag())){//关联合同查询
			cntList = pageDao.queryRelevanceList(paramMap);
		}else{
			cntList = pageDao.queryList(paramMap);
		}
		CommonLogger.info("查询合同列表,ContractQueryService,queryList()");
		return cntList;
	}
	
	/** 
	 * @methodName queryList
	 * desc  获取合同列表信息
	 * 
	 * @param con 查询条件
	 */
	public List<QueryContract> szQueryList(QueryContract con) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", con);
		paramMap.put("org1Code", org1Code);
		paramMap.put("dataFlags",  Arrays.asList(con.getDataFlags()));
		
		List<QueryContract> cntList = null;
		IContractQueryDAO pageDao = PageUtils.getPageDao(dao);
		cntList = pageDao.szQueryList(paramMap);
		CommonLogger.info("深圳合同数据导出列表查询,ContractQueryService,szQueryList()");
		return cntList;
	}
	
	public List<ContractBean> selectCntDataFlag() {
		return dao.selectCntDataFlag();
	}
	/**
	 * 根据查詢條件获取合同列表
	 * 
	 * @param con
	 * @return 返回生成的任务ID
	 * @throws Exception
	 */
	public String queryDownloadList(QueryContract con) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,ContractQueryService，queryDownloadList");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/contactExport.xlsx";
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("dutyCode", dutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		map.put("con", con);
		
		return exportDeal.execute("contract", "CONTRACT_01",destFile,map);
	}

	/**
	 * 根据查詢條件获取合同列表
	 * 
	 * @param con
	 * @return 返回生成的任务ID
	 * @throws Exception
	 */
	public String szQueryDownloadList(QueryContract con) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,ContractQueryService，szQueryDownloadList");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/szContactExport.xlsx";
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("org1Code", org1Code);
		map.put("startDate", con.getBefDate());
		map.put("endDate", con.getAftDate());
		String temp = "";
		
		if(con.getDataFlags()!=null){
			
			for(int i = 0;i<con.getDataFlags().length;i++){
				if(i == 0){
					temp = con.getDataFlags()[i];
					
				}else{
					temp = temp+","+con.getDataFlags()[i];
					
				}
			}
		}
		map.put("dataFlagString",  temp);
		
		return exportDeal.execute("合同导出数据", "SZ_CNT_MATR_EXPORT",destFile,map);
	}
	
	/** 
	 * @methodName queryDownloadCount
	 * desc   获取查询下载的总数，控制导出写文件量
	 * 
	 * @param map 
	 */
	public String queryDownloadCount(Map map){
		return dao.getDownloadCount(map);
	}
	
	/** 
	 * @methodName queryContactList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	public List<QueryContract> queryContactList(Map map) {
		return dao.queryDownloadList(map);
	}
	/** 
	 * @methodName queryContactList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	public List<QueryContract> szQueryContactList(Map<String, Object> map) {
		return dao.szGetDownloadList(map);
	}
	

	/** 
	 * @methodName getDetail
	 * desc  获取合同信息
	 * 
	 * @param cntNum 合同编号
	 * @param dutyCode 
	 */
	public QueryContract getDetail(String cntNum) {
		CommonLogger.info("得到合同号："+cntNum+"的详细信息，ContractQueryService.getDetail");
		QueryContract cnt = dao.getDetail(cntNum);
		// 物料信息
		cnt.setDevices(getCntProj(cntNum));
		// 审批类别 1：电子审批;
		if ("1".equals(cnt.getLxlx())) {
			// 电子审批信息
			cnt.setDzspInfos(getDZSPProj(cntNum));
		}
		// 合同类型：1-费用类 && 费用类型： 0-金额固定、受益期固定 && 费用子类型 1-房屋租赁类
		if ("1".equals(cnt.getCntType()) && "0".equals(cnt.getFeeType()) && "1".equals(cnt.getFeeSubType())) {
			// 租金信息
			List<TenancyDz> tenancyList = contractModifyService.getTcyDz(cntNum);
			String tenancyStr = "";
			if(null!=tenancyList)
			{
				tenancyStr = JSONArray.fromObject(tenancyList).toString();
			}
			cnt.setTenancies(tenancyList);
			cnt.setTenanciesStr(tenancyStr);
		}
		// 付款条件 ： 3-分期付款
		if ("3".equals(cnt.getPayTerm())) {
			// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			if ("0".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnSchedule(cntNum));
			} else if ("1".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnDate(cntNum));
			} else if ("2".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnTerm(cntNum));
			}
		}
		return cnt;
	}
	
	
	public QueryContract getDetailCheck(String cntNum) {
		CommonLogger.info("得到合同号："+cntNum+"的详细信息，ContractQueryService.getDetail");
		QueryContract cnt = dao.getDetail(cntNum);
		// 物料信息
		cnt.setDevices(getCntProj(cntNum));
		// 审批类别 1：电子审批;
		if ("1".equals(cnt.getLxlx())) {
			// 电子审批信息
			cnt.setDzspInfos(getDZSPProj(cntNum));
		}
		// 合同类型：1-费用类 && 费用类型： 0-金额固定、受益期固定 && 费用子类型 1-房屋租赁类
		if ("1".equals(cnt.getCntType()) && "0".equals(cnt.getFeeType()) && "1".equals(cnt.getFeeSubType())) {
			// 租金信息
			List<Map<String, Object>> tenanciesList = contractModifyService.getTcyDzList(cntNum);
			String tenancyStr = "";
			if(null!=tenanciesList)
			{
				tenancyStr = JSONArray.fromObject(tenanciesList).toString();
			}
			cnt.setTenanciesList(tenanciesList);
			cnt.setTenanciesStr(tenancyStr);
		}
		// 付款条件 ： 3-分期付款
		if ("3".equals(cnt.getPayTerm())) {
			// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			if ("0".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnSchedule(cntNum));
			} else if ("1".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnDate(cntNum));
			} else if ("2".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnTerm(cntNum));
			}
		}
		return cnt;
	}
	public QueryContract getDetailCheckByDutyCode(String cntNum) {
		CommonLogger.info("得到合同号："+cntNum+"的详细信息，ContractQueryService.getDetail");
		QueryContract cnt = dao.getDetail(cntNum);
		// 物料信息
		cnt.setDevices(getCntProj(cntNum));
		// 审批类别 1：电子审批;
		if ("1".equals(cnt.getLxlx())) {
			// 电子审批信息
			cnt.setDzspInfos(getDZSPProj(cntNum));
		}
		
		// 付款条件 ： 3-分期付款
		if ("3".equals(cnt.getPayTerm())) {
			// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			if ("0".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnSchedule(cntNum));
			} else if ("1".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnDate(cntNum));
			} else if ("2".equals(cnt.getStageType())) {
				cnt.setStageInfos(getOnTerm(cntNum));
			}
		}
		return cnt;
	}

	/** 
	 * @methodName getCntProj
	 * desc   获取物料信息
	 * 
	 * @param cntNum 合同编号
	 * @param dutyCode 
	 */
	public List<CntDevice> getCntProj(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的物料信息，ContractQueryService，getCntProj");
		return dao.getCntProj(cntNum);
	}

	/** 
	 * @methodName getDZSPProj
	 * desc   获取电子审批信息
	 * 
	 * @param cntNum 合同编号
	 */
	public List<DzspInfo> getDZSPProj(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的电子审批信息，ContractQueryService，getDZSPProj");
		return dao.getDZSPProj(cntNum);
	}

	/** 
	 * @methodName getTcyDz
	 * desc   获取租金递增信息
	 * 
	 * @param cntNum 合同编号
	 */
	public List<TenancyDz> getTcyDz(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的租金递增信息，ContractQueryService，getTcyDz");
		return dao.getTcyDz(cntNum);
	}

	/** 
	 * @methodName getOnSchedule
	 * desc   获取按进度付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	public List<StageInfo> getOnSchedule(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的按进度付款信息，ContractQueryService，getOnSchedule");
		return dao.getOnSchedule(cntNum);
	}

	/** 
	 * @methodName getOnDate
	 * desc   获取按日期付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	public List<StageInfo> getOnDate(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的按日期付款信息，ContractQueryService，getOnDate");
		return dao.getOnDate(cntNum);
	}

	/** 
	 * @methodName getOnTerm
	 * desc   获取按条件分期付款信息
	 * 
	 * @param cntNum 合同编号
	 */
	public List<StageInfo> getOnTerm(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的按条件付款信息，ContractQueryService，getOnTerm");
		return dao.getOnTerm(cntNum);
	}
	
	/** 
	 * @methodName updateExcelResult
	 * desc   更新导出状态数据
	 * 
	 * @param bean 
	 */
	public void updateExcelResult(CommonExcelDealBean bean){
		excelDao.updateExportResult(bean);
	}

	/**
	 * @methodName queryFeeType
	 * desc 查询受益信息页面  
	 * 
	 * @param con
	 */
	public List<CntDevice> queryFeeType(String cntNum) {
		CommonLogger.info("合同查询时查看合同号为"+cntNum+"对应的收益信息，ContractQueryService，queryCntDevice");
		return dao.queryCntDevice(cntNum);
	}
	
	/**
	 * @methodName book
	 * desc 查看操作日志
	 * 
	 * @param cntNum
	 * @return
	 */

	public List<WaterBook> book(String cntNum) {
		CommonLogger.info("合同查询时查看合同号为"+cntNum+"的操作日志，ContractQueryService，book");
		IContractQueryDAO vdao=PageUtils.getPageDao(dao);
		return vdao.book(cntNum);
	}
	
	/**
	 * @methodName queryOrderPage
	 * desc  查询订单信息    
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<OrderQueryBean> queryOrderPage(String cntNum){
		CommonLogger.info("合同查询时查看合同号为"+cntNum+"的所有订单信息，ContractQueryService，orderListByCntNum");
		return orderDao.orderListByCntNum(cntNum);
	}

	public List<String>  getCntProj1(String cntNum) {
		CommonLogger.info("获取合同号为"+cntNum+"对应的物料信息，ContractQueryService，getCntProj");
		return dao.getCntProj1(cntNum);
	}

	public String exportData(QueryContract bean) throws Exception {
		String sourceFileName = "合同明细数据导出"+bean.getCntNum();
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("cntNum", bean.getCntNum());
		return exportDeal.execute(sourceFileName, "CNT_DEVICE_EXPORT", destFile , map);
	}

	public List<CntDevice> exportExcute(String cntNum) {
		return this.getCntProj(cntNum);
	}
	
}
