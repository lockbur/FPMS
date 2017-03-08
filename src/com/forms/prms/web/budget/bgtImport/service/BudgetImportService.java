package com.forms.prms.web.budget.bgtImport.service;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.budget.bgtImport.dao.BudgetImportDao;
import com.forms.prms.web.budget.bgtImport.domain.BudgetImportBean;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.user.domain.User;
@Service
public class BudgetImportService {
	@Autowired
	BudgetImportDao dao;
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;		//Excel导入Service
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	@Autowired
	private DBFileOperUtil dbFileOperUtil;
	@Autowired
	private ConcurrentService concurrentService;
	//获得类实例
	public static BudgetImportService getInstance(){
		return SpringUtil.getBean(BudgetImportService.class);
	}
	/**
	 * 导入汇总信息列表
	 * @param bean
	 * @return
	 */
	public List<BudgetImportBean> getImportList(BudgetImportBean bean) {
		BudgetImportDao imDao = PageUtils.getPageDao(dao);
		if ("1".equals(bean.getOrgType())) {
			CommonLogger.info("预算导入省行"+bean.getOrg21Code()+"汇总信息列表,BudgetImportService,getImportList");
		}
		return imDao.getImportList(bean);
	}
	/**
	 * 预算导入前校验
	 * @param bean
	 * @return
	 */
	public Map<String, Object> importPageAjax(BudgetImportBean bean) {
		// 

		String orgTypeDesc = ("01".equals(bean.getOrgType())) ? "省行" : "分行";
		CommonLogger.info("预算数据导入前的校验,校验信息如下：操作机构级别=["+orgTypeDesc+"]," +
				"预算年份=["+bean.getBgtYear()+"],预算类型=["+bean.getBgtType()+"],BudgetSumService,checkBeforeImportData");
		Map<String, Object> map =  new HashMap<String, Object>();
		//该类型的监控指标没有导入就不能下达预算 年份+监控指标
		int isMontDone = dao.isMontDone(bean);
		if (isMontDone ==0) {
			map.put("flag", false);
			map.put("msg",  bean.getBgtYear()+"年"+getBudTypeName(bean.getSubType())+"的监控指标还没有制定，不允许执行该类型的预算导入");
			return map;
		}
		//校验监控指标是否正在导入中
		if (dao.getMontCount(bean)>0) {
			map.put("flag", false);
			map.put("msg",  bean.getBgtYear()+"年"+getBudTypeName(bean.getSubType())+"的导入的监控指标还没有审核结束，不允许执行该类型的预算导入");
			return map;
		}
		//初次下达只能下达一次
		if ("00".equals(bean.getBgtType()) || "01".equals(bean.getBgtType())) {
			if (dao.checkInitCount(bean)>0) {
				map.put("flag", false);
				map.put("msg", "该类型的预算已经下达过了不能重复下达。");
				return map;
			}
		}
	//正式预算下达了不能下达临时预算了
		if ("00".equals(bean.getBgtType()) ) {
			BudgetImportBean bean2 = dao.checkZsBgtIsDone(bean);
			if (!Tool.CHECK.isEmpty(bean2)) {
				//已经下达了正式预算，不能下达临时预算了
				map.put("flag", false);
				if ("02".equals(bean2.getStatus()) || "06".equals(bean2.getStatus())) {
					map.put("msg", "该类型的预算已经下达了正式预算并，且状态为"+bean2.getStatusName()+"，只能删除该状态的预算才能继续导入。");
					
				}else {
					map.put("msg", "该类型的预算已经下达了正式预算,且状态为"+bean2.getStatusName()+"，不能再下达临时预算了。");
				}
				
				return map;
			}
		}else if ("02".equals(bean.getBgtType())) {
			//追加预算之前必须下达了临时预算或者正式预算
			int count = dao.isHaveLsOrZs(bean);
			if (count==0) {
				map.put("flag", false);
				map.put("msg", "该类型的预算没有下达临时预算或正式预算，不能直接追加。");
				return map;
			}
		}
		//不管是临时预算还是正式预算，也不管是初次下达还是和追加，如果要想导入必须得等其他导入的预算都走完流程，不能是01,03,05，06
		int count = dao.checkBgt(bean);
		if (count>0) {
			map.put("flag", false);
			map.put("msg", bean.getBgtYear()+"该类型的预算存在没有走完流程的数据，请提交或删除后再导入");
			return map;
		}
		map.put("flag", true);
		return map;
	
	}
	/**
	 * 根据类型来得到预算类型名称
	 * @param type
	 * @return
	 */
	public String getBudTypeName(String type){
		String subTypeDesc = ("11".equals(type)) ? "专项包" : 
			(("12".equals(type) ? "省行统购资产" : 
			(("21".equals(type) ? "非省行统购类资产" : "非专项包费用类"))));
		return  subTypeDesc;
	}
	/*8
	 * 导入
	 */
	public void bgtImport(BudgetImportBean bean) throws Exception {
		//1.[新增预算模板]操作，预算Bean处理，保存模板信息至数据库
		User instOper = WebHelp.getLoginUser();				//获取创建柜员
		//1.5【复制Excel文件另存保存】将上传的Excel文件另存为服务器指定物理路径
		//上传组件保存的文件原始文件路径
		String uploadFileUrl = bean.getFilePath();
		//上传组件自命名的文件名
		String uploadFileRename = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/")+1,uploadFileUrl.length());	
		String targetFileUrl = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+uploadFileRename;		//复制到服务器本地路径+日期+上传组件重命名文件名保存(路径+日期文件夹+生成文件名)
		//【创建文件夹路径】判断目标文件夹路径，不存在则创建
		ImportUtil.createFilePath(new File(targetFileUrl.substring(0, targetFileUrl.lastIndexOf("/"))));
		//【复制文件】将文件复制到服务器本地指定地址保存
		ImportUtil.fileCopyByBufferStream(new File(uploadFileUrl), new File(targetFileUrl));
		//将文件保存到数据库
//		dbFileOperUtil.saveFileToDB(targetFileUrl);
		//【更改DB保存路径】更改上传文件保存信息
		targetFileUrl = targetFileUrl.replaceAll("\\\\", "/");
		bean.setFilePath( targetFileUrl ); 		//上传组件在源文件下载时需要"/"标识符寻找路径

		bean.setInstOper(instOper.getUserId());
		if ("1".equals(bean.getOrgType())) {
			bean.setOrg21Code(instOper.getOrg1Code());
		}else {
			bean.setOrg21Code(instOper.getOrg2Code());
		}
		//写入汇总信息
		//得到批次信息
		String batchNo = dao.getBatchNo();
		bean.setBatchNo(batchNo);
		bean.setStatus("01");//导入中
		//插入汇总信息
		dao.insertSummary(bean);

		//3.开始进行[导入Excel]处理
		boolean ExcelImportFlag = false;
		try {
			ExcelImportFlag = this.importExcel(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonLogger.debug("[ImportExcel导入操作已启用]："+ExcelImportFlag);
	
		
	}
	public boolean importExcel(BudgetImportBean bean) throws Exception{
		boolean flag = false;
		String excelUrl = bean.getFilePath();
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileSecName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//2.Excel导入参数处理
		String excelDesc = "";
		if ("11".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"专项包预算导入";
		}else if ("12".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"省行统购资产预算导入";
		}else if ("21".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"非省行统购资产预算导入";
		}else {
			excelDesc = bean.getBgtYear()+"非专项包费用预算导入";
			
		}
		bean.setConfigId("BGT_SUM_DATA_IMPORT");
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("org21Code", bean.getOrg21Code());
		beans.put("impBatch", bean.getBatchNo());
		beans.put("subType", bean.getSubType());
		beans.put("bgtType", bean.getBgtType());
		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
		importGenernalDeal.execute(filePath, fileSecName, excelDesc, new String[]{bean.getConfigId()}, beans);	
		 
		//导入操作已进行=true
		flag = true;
		return flag;
	}
	
	public void updateStatus(String batchNo, String preStatus, String nexStatus,String memo){
		dao.updateStatus(batchNo,preStatus,nexStatus,memo);
	}
	/**
	 * 导入EXCEL的明细数据
	 * @param budgetBeans
	 */
	public void insertDetail(List<BudgetImportBean> budgetBeans) {
		dao.insertDetail(budgetBeans);
		
	}
	/**
	 * 导入后校验存储过程
	 * @param string
	 */
	public void checkBudgetProduce(String batchNo) {
		dao.checkBudgetProduce(batchNo);
	}
	/**
	 * 删除
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void del(BudgetImportBean bean) {
		//删除汇总
		dao.delSummary(bean);
		dao.delDetail(bean);
		//删除 SYS_FILE_INFO
		dao.delSysFileInfo(bean);
		//删除错误信息
		dao.delErrorInfo(bean);
		
	}
	/**
	 * 得到汇总信息
	 * @param bean
	 * @return
	 */
	public BudgetImportBean getTotalInfo(BudgetImportBean bean) {
		return dao.getTotalInfo(bean);
	}
	/**
	 * 得到错误信息明细
	 * @param bean
	 * @return
	 */
	public List<BudgetImportBean> getErrorList(BudgetImportBean bean) {
		BudgetImportDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getErrorList(bean);
	}
	/**
	 * 导出
	 * @param eb
	 * @return
	 */
	public Object downLoad(BudgetImportBean eb) {
		// TODO Auto-generated method stub
		return null;
	}
	public String dataExport(BudgetImportBean bean) throws Exception {
		if ("1".equals(bean.getOrgType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		String bgtType=bean.getBgtType();
		String tempName="EXPORT_BUDGET_ERROR";
		String excelDesc = "";
		if ("11".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"专项包";
		}else if ("12".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"省行统购资产";
		}else if ("21".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"非省行统购资产";
		}else {
			excelDesc = bean.getBgtYear()+"非专项包费用";
			
		}
		String bgtTypeName="";
		if ("00".equals(bgtType)) {
			bgtTypeName = "临时预算";
		}else if ("01".equals(bgtType)) {
			bgtTypeName = "正式预算";
		}else if ("02".equals(bgtType)) {
			bgtTypeName = "追加预算";
		}
		String sourceFileName = excelDesc+bgtTypeName+"数据导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-.xlsx";
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("batchNo", bean.getBatchNo());
		map.put("type", "02");//01是导入模板数据 ，02是导入的明细数据
		map.put("bgtYear", bean.getBgtYear());
		map.put("bgtType", bean.getBgtType());
		map.put("subType", bean.getSubType());
		map.put("org21Code", bean.getOrg21Code());
		map.put("orgType", bean.getOrgType());
		return exportDeal.execute(sourceFileName, tempName, destFile , map);
	}
	/**
	 * 导出数据的具体方法
	 * @param batchNo
	 * @return
	 */
	public List<BudgetImportBean> getImportDetailForExcel(String batchNo) {
		return dao.getImportDetailForExcel(batchNo);
	}
	/**
	 * 导入模板的下载
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public String downloadTemp(BudgetImportBean bean) throws Exception {
		if ("1".equals(bean.getOrgType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		String bgtType=bean.getBgtType();
		String tempName="EXPORT_TEMP_BUDGET";
		String excelDesc = "";
		if ("11".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"专项包";
		}else if ("12".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"省行统购资产";
		}else if ("21".equals(bean.getSubType())) {
			excelDesc = bean.getBgtYear()+"非省行统购资产";
		}else {
			excelDesc = bean.getBgtYear()+"非专项包费用";
			
		}
		String bgtTypeName="";
		if ("00".equals(bgtType)) {
			bgtTypeName = "临时预算";
		}else if ("01".equals(bgtType)) {
			bgtTypeName = "正式预算";
		}else if ("02".equals(bgtType)) {
			bgtTypeName = "追加预算";
		}
		String sourceFileName = excelDesc+bgtTypeName+"数据导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-.xlsx";
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("batchNo", bean.getBatchNo());
		map.put("type", "01");//01是导入模板数据 ，02是导入的明细数据
		map.put("bgtYear", bean.getBgtYear());
		map.put("bgtType", bean.getBgtType());
		map.put("subType", bean.getSubType());
		map.put("org21Code", bean.getOrg21Code());
		map.put("orgType", bean.getOrgType());
		return exportDeal.execute(sourceFileName, tempName, destFile , map);
	}
	/**
	 * 导入模板下载
	 * @param bean 
	 * @return
	 */
	public List<BudgetImportBean> getDownloadTemp(BudgetImportBean bean) {
		return dao.getDownloadTemp(bean);
	}
	/**
	 * 导出只有透支的预算
	 * @param bean
	 * @return
	 */
	public List<BudgetImportBean> getDownloadTempOnlyOver(BudgetImportBean bean) {
		return dao.getDownloadTempOnlyOver(bean);
	}
	
	
	public void bgtSubmit(BudgetImportBean bean) throws Exception {
		String ipAddres = InetAddress.getLocalHost().getHostName().toString();
		int affect  = dao.updateStatusAndIpAddress(bean.getBatchNo(), "03", "04",ipAddres);
		
		String org21Code = WebHelp.getLoginUser().getOrg1Code();
		String instOper = WebHelp.getLoginUser().getUserId();
		if (affect>0) {
			try {
				String memo = "";
				if ("11".equals(bean.getSubType())) {
					memo = "一级行"+bean.getOrg21Code()+"提交导入的专项包类型的预算（批次号："+bean.getBatchNo()+"）时增加锁";
				}
				if ("12".equals(bean.getSubType())) {
					memo = "一级行"+bean.getOrg21Code()+"提交导入的省行统购资产类型的预算（批次号："+bean.getBatchNo()+"）时增加锁";
				}
				if ("21".equals(bean.getSubType())) {
					memo = "二级行"+bean.getOrg21Code()+"提交导入的非省行统购资产类型的预算（批次号："+bean.getBatchNo()+"）时增加锁";
				}
				if ("22".equals(bean.getSubType())) {
					memo = "二级行"+bean.getOrg21Code()+"提交导入的非专项包费用类型的预算（批次号："+bean.getBatchNo()+"）时增加锁";
				} 
				 concurrentService.checkAndAddLock(ConcurrentType.Concurrent_B,
						ConcurrentType.B2,org21Code,instOper,memo);
			} catch (Exception e) {
				e.printStackTrace();
				dao.updateStatus(bean.getBatchNo(), "04", "06", e.getCause().getMessage());
				throw e;
			}
			//调用存储过程
			try {
				dao.callSubmitProduce(bean.getBatchNo());
			} catch (Exception e) {
				e.printStackTrace();
				dao.updateStatusAndIpAddress(bean.getBatchNo(), "04", "06","");
			}
			
			//删除锁
			try {
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_B, ConcurrentType.B2,bean.getBatchNo());
			} catch (Exception e) {
				e.printStackTrace();
				CommonLogger.error("预算提交删除锁的时候出错,批次号是"+bean.getBatchNo());
			}
			
		}
		
		
		
	}
	public BudgetImportBean getPath(String batchNo) {
		return dao.getPath(batchNo);
	}
	public String isMore(BudgetManageBean bmBean) {
		return dao.isMore(bmBean);
	}
	public String isMore1(BudgetManageBean bmBean) {
		return dao.isMore1(bmBean);
	}
	
	/**
	 * 校验是否在SUM_CNT表使用过
	 * @param bmBean
	 * @return
	 */
	public int isUsed(BudgetManageBean bmBean) {
		return dao.isUsed(bmBean);
	}

}