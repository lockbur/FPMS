package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service;

import java.io.File;
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
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.dao.ImportDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.AprvChainBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.dao.ExportDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;
import com.forms.prms.web.user.domain.User;

@Service
public class ImportService {
	@Autowired
	private ExportDao edao;
	@Autowired
	private ImportDao dao;
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;		//Excel导入Service
	//获得类实例
	public static ImportService getInstance(){
		return SpringUtil.getBean(ImportService.class);
	}
	/**
	 * 判断是否已经导入过 了
	 * @param bean
	 * @return 
	 */
	public Map<String, Object> ajaxDataExist(ImportBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String flag ="Y";
		String org21Code = "";
//		String desc = "";
//		if ("11".equals(bean.getSubType())) {
//			desc = "专项包";
//		}
//		if ("12".equals(bean.getSubType())) {
//			desc = "省行统购资产";
//		}
//		if ("21".equals(bean.getSubType())) {
//			desc = "非省行统购资产";
//		}
//		if ("22".equals(bean.getSubType())) {
//			desc = "非专项包费用";
//		}
		if ("01".equals(bean.getOrgType())) {
			//省行  
			org21Code = WebHelp.getLoginUser().getOrg1Code();
		}else {
			org21Code = WebHelp.getLoginUser().getOrg2Code();
		}
		bean.setOrg21Code(org21Code);
		//看看有么有流程未走完的 ，没走完就不准导入新的数据
		int count3 = dao.isHaveNotOver(bean);
		if (count3>0) {
			map.put("msg", "该类型的数据在之前已经经办过，且没有走完流程，请在流程结束后再重新导入!");
			map.put("flag", "N");
			return map;
		}
		int count4 = dao.isHaveNotOverNextYear(bean);
		if (count4>0) {
			map.put("msg", "该类型下一年的数据已经经办提交了，不能重新导入本年的数据，只能将下一年的数据退后后再导入!");
			map.put("flag", "N");
			return map;
		}
		//增加一个看下一年的数据 有没有走完流程
		
		//监控指标导入校验预算是否走完流程
		if("01".equals(bean.getProType())){
			if(dao.checkBgtIsOver(bean)>0){
				map.put("msg", bean.getDataYear()+"该类型导入的预算还在提交中，为避免预算数据错误，请在提交完成后再重新导入!");
				map.put("flag", "N");
				return map;
			}
		}
		if ("02".equals(bean.getInstType()) || "03".equals(bean.getInstType())) {
			//导入的是下一年的数据
			//检查 本年的数据是否存在 如果不存在也不让他导入
			if ("01".equals(bean.getProType())) {
				//监控指标
				int count = dao.isExistMontThisYear(bean);
				if (count<=0) {
					//本年的监控指标不存在
					map.put("msg", "该类型的数据在当年不存在，不能导入下一年的数据!");
					map.put("flag", "N");
					return map;
				}else{ 
					//监控指标指标导入下一年校验本年的审批链是否导入，没有导入审批链就不允许新增
					int count2 = dao.isExistAprvThisYear(bean);
					if (count2 ==0) {
						map.put("msg", "该类型的审批链在当年还没有维护，不能导入下一年的数据监控指标!");
						map.put("flag", "N");
						return map;
					}
				}
			}else if ("02".equals(bean.getProType())) {
				//审批链
				if ("12".equals(bean.getSubType())) {
					bean.setIsProvinceBuy("0");
				}else {
					bean.setIsProvinceBuy("1");
				}
				int count = dao.isExistAprvThisYear(bean);
				if (count<=0) {
					//本年的审批链不存在
					map.put("msg", "该类型的数据在当年不存在，不能导入下一年的数据!");
					map.put("flag", "N");
					return map;
				}
			}
		}
		if ("02".equals(bean.getProType())) {
			//如果是审批链导入 检查 对应的监控指标hi否已经维护
			String year = Tool.DATE.getDateStrNO().substring(0,4);
			if ("02".equals(bean.getInstType()) || "03".equals(bean.getInstType())) {
				year = Integer.parseInt(Tool.DATE.getDateStrNO().substring(0,4))+1+"";
				
			}
			bean.setDataYear(year);
			int count= 0;
			count= dao.ajaxCopyExists(bean);
			if (count==0) {
				// 监控指标是空的 必须先维护监控指标
				flag = "N";
				msg = bean.getDataYear()+"年对应的监控指标还没有维护不能导入审批链";
			}
		}
		map.put("msg", msg);
		map.put("flag", flag);
		return map;
	}
	/**
	 * 导入
	 * @param bean
	 * @throws Exception 
	 */
	public void addExcel(ImportBean bean) throws Exception {

		//1.[新增预算模板]操作，预算Bean处理，保存模板信息至数据库
		User instOper = WebHelp.getLoginUser();				//获取创建柜员
		if (!"03".equals(bean.getInstType())) {
			//1.5【复制Excel文件另存保存】将上传的Excel文件另存为服务器指定物理路径
			//上传组件保存的文件原始文件路径
			String uploadFileUrl = bean.getPath();
			//上传组件自命名的文件名
			String uploadFileRename = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/")+1,uploadFileUrl.length());	
			String targetFileUrl = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+uploadFileRename;		//复制到服务器本地路径+日期+上传组件重命名文件名保存(路径+日期文件夹+生成文件名)
			//【创建文件夹路径】判断目标文件夹路径，不存在则创建
			ImportUtil.createFilePath(new File(targetFileUrl.substring(0, targetFileUrl.lastIndexOf("/"))));
			//【复制文件】将文件复制到服务器本地指定地址保存
			ImportUtil.fileCopyByBufferStream(new File(uploadFileUrl), new File(targetFileUrl));
			//将文件保存到数据库
//			dbFileOperUtil.saveFileToDB(targetFileUrl);
			//【更改DB保存路径】更改上传文件保存信息
			targetFileUrl = targetFileUrl.replaceAll("\\\\", "/");
			bean.setPath( targetFileUrl ); 		//上传组件在源文件下载时需要"/"标识符寻找路径
		}
		
		
		bean.setInstUser(instOper.getUserId());
		String org21Code = "";
		if ("01".equals(bean.getOrgType())) {
			//省行
			org21Code = instOper.getOrg1Code();
		}else {
			org21Code = instOper.getOrg2Code();
		}
		bean.setOrg21Code(org21Code);
		if ("02".equals(bean.getInstType()) || "03".equals(bean.getInstType())) {
			//导入的是下一年的数据
			String dataYear = Integer.parseInt(bean.getDataYear())+1+"";
			bean.setDataYear(dataYear);
		}
		//取得批次号
		String batchNo = dao.getId(bean);
		bean.setBatchNo(batchNo);
		bean.setOrg21Code(org21Code);
		bean.setExcelStatus(MontAprvType.EXCEL_E0);							//初始插入状态为"00"(待处理)
		if ("03".equals(bean.getInstType())) {
			bean.setExcelStatus(MontAprvType.EXCEL_E2);
		}
		dao.addData(bean);					//插入导入的汇总信息
		
		if (!"03".equals(bean.getInstType())) {
			//3.开始进行[导入Excel]处理
			boolean ExcelImportFlag = false;
			try {
				ExcelImportFlag = this.importExcel(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			CommonLogger.debug("[ImportExcel导入操作已启用]："+ExcelImportFlag);
		}
	}
	/**
	 * 调用导入Excel操作
	 * @param 	budget	导入Excel相关的预算模板
	 * @return			Excel导入操作是否已执行(与导入结果无关)
	 * @throws 	Exception
	 */
	public boolean importExcel(ImportBean bean) throws Exception{
		boolean flag = false;
		String excelUrl = bean.getPath();
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileSecName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//2.Excel导入参数处理
		String excelDesc = "";
		if("01".equals(bean.getProType())){
			//监控指标
			if ("11".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"专项包监控指标导入";
				bean.setConfigId("MONT_APRV_TEMP01_11");
			}else if ("12".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"省行统购资产监控指标导入";
				bean.setConfigId("MONT_APRV_TEMP01_12");
			}else if ("21".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"非省行统购资产监控指标导入";
				bean.setConfigId("MONT_APRV_TEMP01_12");
			}else {
				excelDesc = bean.getDataYear()+"非专项包费用监控指标导入";
				bean.setConfigId("MONT_APRV_TEMP01_12");
			}
		}else if ("02".equals(bean.getProType())) {
			//审批链的
			if ("11".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"专项包审批链导入";
				bean.setConfigId("MONT_APRV_TEMP02_11");
			}else if ("12".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"省行统购资产审批链导入";
				bean.setConfigId("MONT_APRV_TEMP02_12");
			}else if ("21".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"非省行统购资产审批链导入";
				bean.setConfigId("MONT_APRV_TEMP02_12");
			}else if ("22".equals(bean.getSubType())) {
				excelDesc = bean.getDataYear()+"非专项包费用审批链导入";
				bean.setConfigId("MONT_APRV_TEMP02_12");
			}
		} 
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("org21Code", bean.getOrg21Code());
		beans.put("org1Code", WebHelp.getLoginUser().getOrg1Code());
		beans.put("impBatch", bean.getBatchNo());
		beans.put("subType", bean.getSubType());
		beans.put("proType", bean.getProType());
		beans.put("loadType", "01");
		beans.put("instType", bean.getInstType());
		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
		importGenernalDeal.execute(filePath, fileSecName, excelDesc, new String[]{bean.getConfigId()}, beans);	
		 
		//导入操作已进行=true
		flag = true;
		return flag;
	}
	/**
	 * 修改状态
	 * @param batchNo
	 * @param status 
	 */
	public void updateExcelStatus(String batchNo, String statusPre,String statusNex) {
		dao.updateExcelStatus(batchNo,statusPre,statusNex);
	}
	/**
	 * 明细数据  插入当年的
	 * @param montBeans
	 */
	public void insertMontDetail(List<MontBean> montBeans) {
		 dao.insertMontDetail(montBeans);
		
	}
	/**
	 * 明细数据  插入下一年的
	 * @param montBeans
	 */
	public void insertMontDetailNext(List<MontBean> montBeans) {
		 dao.insertMontDetailNext(montBeans);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<ImportBean> shList(ImportBean eb) {
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		eb.setOrg21Code(org1Code);
		ImportDao pageDao=PageUtils.getPageDao(dao);
		return pageDao.shList(eb);
	}
	public List<ImportBean> fhList(ImportBean eb) {
		String org2Code=WebHelp.getLoginUser().getOrg2Code();
		eb.setOrg21Code(org2Code);
		ImportDao pageDao=PageUtils.getPageDao(dao);
		return pageDao.fhList(eb);
	}
	@Transactional(rollbackFor = Exception.class)
	public void delete(ImportBean eb) {
		dao.deleteTaskLoad(eb);
		dao.deleteBatch(eb);
		dao.deleteError(eb);
		String proType=eb.getProType();
		if (proType.equals("01")) {
			dao.deleteMontDetail(eb);
		}else if(proType.equals("02")){
			dao.deleteAprvDetail(eb);
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	public void submit(ImportBean eb) {
		//校验是否有待勾选的合同
//		int count = dao.isHaveSplitCnt(eb);
		dao.updateExcelStatus(eb.getBatchNo(),MontAprvType.EXCEL_E2,MontAprvType.EXCEL_E4);
	}

//	public String downLoad(ExportBean eb) throws Exception {
//		String subTypeName = "";
//		String subType=eb.getSubType();
//		String proType=eb.getProType();
//		String proTypeName = "";
//		String tempName="";
//		if (proType.equals("01")) {
//			proTypeName="监控指标";	
//			if (subType.equals("11")) {
//				subTypeName="专项包";
//				tempName="MONT_ZXB_BATCH_EXPORT";
//			}else if(subType.equals("12")){
//				subTypeName="省行统购资产";
//				tempName="MONT_NOT_ZXB_BATCH_EXPORT";
//			}else if(subType.equals("21")){
//				subTypeName="非省行统购类资产";
//				tempName="MONT_NOT_ZXB_BATCH_EXPORT";
//			}else if(subType.equals("22")){
//				subTypeName="非专项包费用类";
//				tempName="MONT_NOT_ZXB_BATCH_EXPORT";
//			}
//		} else if(proType.equals("02")) {
//			proTypeName="审批链";	
//			if (subType.equals("11")) {
//				subTypeName="专项包";
//				tempName="APRV_ZXB_BATCH_EXPORT";
//			}else if (subType.equals("12")) {
//				subTypeName="省行统购资产";
//				tempName="APRV_NOT_ZXB_BATCH_EXPORT";
//			}else if (subType.equals("21")) {
//				subTypeName="非省行统购非专项包";
//				tempName="APRV_NOT_ZXB_BATCH_EXPORT";
//			}
//		}
//		String sourceFileName = eb.getDataYear()+subTypeName+proTypeName+"数据导出";
//		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
//		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-"+eb.getBatchNo()+".xlsx";;
//		//导出Excel操作
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("batchNo", eb.getBatchNo());
//		map.put("proType", eb.getProType());
//		if ("01".equals(eb.getProType())) {
//			 
//			List<ExportBean> list=this.getMontContent(eb.getBatchNo());
//			if (list.size()<1||list.isEmpty()) {
//				return null;
//			} else {
//				return exportDeal.execute(sourceFileName+"【"+eb.getBatchNo()+"】", tempName, destFile , map);
//			}
//
//		}else if ("02".equals(eb.getProType())) {
//			List<ExportBean> list=this.getAprvContent(eb.getBatchNo());
//			if (list.size()<1||list.isEmpty()) {	
//				return null;
//			} else {
//				return exportDeal.execute(sourceFileName+"【"+eb.getBatchNo()+"】", tempName, destFile , map);
//			}		
//		}else{
//			return null;
//		}
//	}
	

	public List<ExportBean> getMontContent(String batchNo) {
		return edao.getMontContent(batchNo);
	}

	public List<ExportBean> getAprvContent(String batchNo) {
		return edao.getAprvContent(batchNo);
	}
	/**
	 * 审批链导入
	 * @param aprvBeans
	 */
	public void insertAprvDetail(List<AprvChainBean> aprvBeans) {
		 dao.insertAprvDetail(aprvBeans);
	}
	/**
	 * 监控指标的校验
	 * @param string
	 */
	public void checkMont(String batchNo) {
		dao.checkMont(batchNo);
		
	}
	/**
	 * 审批链校验存储过程
	 * @param org1Code 
	 * @param string
	 */
	public void checkAprv(String batchNo) {
		dao.checkAprv(batchNo);
		
	}
	/**
	 * 用户职责信息导入校验存储过程
	 * @param org1Code 
	 * @param string
	 */
	public void checkUserRoleRln(String batchNo,String isCheck,String userId) {
		dao.checkUserRoleRln(batchNo,isCheck,userId);
		
	}
	public List<ImportBean> getErrData(ImportBean bean) {
		ImportDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getErrData(bean);
	}
	public ImportBean getDetail(ImportBean bean) {
		 if ("01".equals(bean.getOrgType())) {
				bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
			}else {
				bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
			}
		return dao.getDetail(bean);
	}
	/**
	 * 分行 导入哪些数据
	 * @param roleId 
	 * @param fhAuthList
	 * @return
	 */
	public List<Map<String, Object>> getAuthList(String roleId, List<String> fhAuthList) {
		CommonLogger.info("导入大类的权限,ImportService,getAuthList");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("fhAuthList", fhAuthList);
		return dao.getAuthList(map);
	}
	/**
	 * copy数据
	 * @param string
	 */
	public void copyData(String batchNo) {
		dao.copyData(batchNo);
		
	}
	/**
	 * copy数据的 校验
	 * @param bean
	 * @return
	 */
	public Map<String, Object> ajaxCopyExists(ImportBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String msg = "";
		//导入的是下一年的数据
		int dataYear =0;
		String waring = "";
		if ("03".equals(bean.getInstType())) {
			dataYear = Integer.parseInt(bean.getDataYear());
			bean.setDataYear(String.valueOf(dataYear+1));
			waring = bean.getDataYear() +"的该类型的数据已经维护通过了，不能进行拷贝操作!";
		}else {
			//下一年的数据已经导入就不能导入本年的 
			dataYear = Integer.parseInt(bean.getDataYear());
			bean.setDataYear(String.valueOf(dataYear+1));
			waring = bean.getDataYear() +"的该类型的数据已经维护通过了，不能重新导入"+dataYear+"年的数据!";
		}
		
		if ("01".equals(bean.getProType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		int count  = dao.ajaxCopyExists(bean);
		if (count>0) {
			//已经维护了数据 
			flag= false;
			msg = waring;
		}else {
			flag = true;
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 更新错误信息
	 * @param string
	 * @param string2
	 * @param message
	 */
	public void updateError(String batchNo, String status, String message) {
		dao.updateError(batchNo,status,message);
		
	}
}
