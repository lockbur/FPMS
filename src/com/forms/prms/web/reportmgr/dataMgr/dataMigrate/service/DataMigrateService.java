package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.tool.exceltool.service.POIParseExcelUtil;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.dao.DataMigrateDao;
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
import com.forms.prms.web.sysmanagement.uploadfilemanage.domain.UpFileBean;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;

@Service
public class DataMigrateService {
	
	@Autowired
	private DataMigrateDao dataMigrateDao;
	
	@Autowired
	public ExcelImportGenernalDeal importGeneralDeal;			//Excel导入Service
	
	@Autowired
	public UpFileManagerService upFileMgrService;				//上传文件管理Service
	
	@Autowired
	public DBFileOperUtil dbFileUtil;							//集成文件下载工具类Service
	
	/*
	 * 获得当前Service类实例
	 */
	public static DataMigrateService getInstance(){
		return SpringUtil.getBean(DataMigrateService.class);
	}
	
	/*
	 * 【【测试】】数据迁移至表SYS_ROLE
	 */
	@SuppressWarnings("finally")
	@Transactional(rollbackFor = Exception.class)
	public boolean importToZZZSYSROLE(String filePath){			//返回Excel导入的处理结果
		boolean invokeFlag = false;
		try {
			importExcel(filePath , 2);							//当第二个参数=2时，为测试SQLLdr导入，否则是simple导入
			invokeFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return invokeFlag;
		}
	}

	/*
	 * 测试--SQLldr导入方式
	 */
	public void importExcel(String filePath , int callType) throws Exception{
		String[] excelInfos = CommonFileUtils.getExcelInfoByPath(filePath);
		String[] configIds = new String[]{"DATAMIGRATE_TEST_TB1"};	
		String excelDesc ; 
		if(2 == callType){
			excelDesc = "【SQLldr-测试】数据导入-"+new Date()+"-ZsysRole数据";
		}else{
			excelDesc = "【Simple-测试】数据导入-"+new Date()+"-ZsysRole数据";
		}
		
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("instOper", WebHelp.getLoginUser().getUserId());				//操作者Id
		beans.put("org1Code", WebHelp.getLoginUser().getOrg1Code());			//操作者的一级机构号
		beans.put("uploadType", "00");											//Excel模板：00
		beans.put("dataType", "01");											//Excel中第一个Sheet
		
		String importTaskNo = importGeneralDeal.execute(excelInfos[1], excelInfos[2], excelDesc, configIds, beans);
		System.out.println("【当次导入任务号为】："+importTaskNo);
	}
	
	/*
	 * 【【测试】】调用导入Excel操作
	 */
	public void importExcel(String filePath) throws Exception{
		String[] excelInfos = CommonFileUtils.getExcelInfoByPath(filePath);
		String[] configIds = new String[]{"DATAMIGRATE_TEST_TB1"};				//"DATAMIGRATE_TEST_TB1","DATAMIGRATE_TEST_TB2"
		String excelDesc = "【Test】数据导入-"+new Date()+"-ZsysRole数据";

		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("instOper", WebHelp.getLoginUser().getUserId());				//操作者Id
		beans.put("org1Code", WebHelp.getLoginUser().getOrg1Code());			//操作者的一级机构号
		beans.put("uploadType", "00");											//Excel模板：00
		beans.put("dataType", "01");											//Excel中第一个Sheet
		
		//执行调用Excel导入组件
		importGeneralDeal.execute(excelInfos[1], excelInfos[2], excelDesc, configIds, beans);		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
	}
	
	/*
	 * 【【测试】】将List中的数据插入表ZZSYSROLE(使用于Deal处理逻辑中End()方法)
	 */
	public void insertZZZSYSROLE(ZsysRoleBean zsysRole){
		dataMigrateDao.dataMigrateToTb1(zsysRole);
	}
	
	/**
	 * 【【不再使用】】@methodName downLoadTempFileFromPj
	 * 		描述：	下载[合同/付款]数据的基础模板Excel文件
	 * 				fileType=1=合同数据 模板 ， fileType=2=付款数据	模板
	 * @param response	响应输出流
	 * @param fileType	文件类型
	 * @throws Exception
	 */
	public void downLoadTempFileFromPj(HttpServletResponse response , String fileType , String subPath , String[] downFileNames) throws Exception{
		CommonLogger.info("【存量业务数据】-下载数据导入模板,fileType=["+fileType+"],DataMigrateService,downLoadTempFileFromPj()");
		String fileUrl = DataMigrateService.class.getClassLoader().getResource("").getPath();
		fileUrl = fileUrl.substring(1)+subPath+"/";			
		//fileUrl = fileUrl.substring(1)+"templates/dataMigrate/";			//下载的文件路径
		if("1".equals(fileType)){
			fileUrl = fileUrl + downFileNames[0];
			//fileUrl = fileUrl + "ContractDataTemp.xlsx";
		}else if("2".equals(fileType)){
			fileUrl = fileUrl + downFileNames[1];
			//fileUrl = fileUrl + "PayDataTemp.xlsx";
		}
		File tempFile = new File(fileUrl);
		String tempFileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1, fileUrl.length());
		if(!tempFile.exists()){
			throw new Exception("【存量业务数据】模板文件--模板类型:["+fileType+"]不存在！");
		}
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(tempFileName.getBytes("GB2312"), "ISO-8859-1"));
		InputStream in = null;
		OutputStream out = null;
		try{
			in = new FileInputStream(tempFile);
			out = response.getOutputStream();
			IOUtils.copy(in, out);
		}catch (Exception e) {
			throw e;
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * @methodName  createImportBatchSeq
	 * 		描述：	使用序列递增的方式创建导入批次的批次号，并返回该批次号值  
	 * @return
	 */
	public String createImportBatchSeq(){
		CommonLogger.info("存量业务数据模块,进行数据导入前的创建导入批次号操作,DataMigrateService,createImportBatchSeq()");
		return dataMigrateDao.createImportBatchSeq();
	}
	
	//下载模板文件
	public Map<String ,Object> downloadTemp(HttpServletRequest request , HttpServletResponse response){
		Map<String , Object> resultMap = new HashMap<String , Object>();								//resultMap :用于保存DownloadFileUtils.download方法返回Map的key：operFlag、errorType、errorMsg(操作结果、错误类型、错误Msg)			
		//1.确定用户需要下载的Excel模板类型
		String downloadFileType = request.getParameter("downloadFileType");								//前端传递参数：用于区别下载的是合同模板还是付款模板
			//dmService.downLoadTempFileFromPj(response, downloadFileType);								//从项目内部路径下载Excel模板文件(不再使用此方法进行下载)
		downloadFileType = ("1".equals(downloadFileType)) ? "DM_CONTARCT_IMP_TEMP" : ( ("2".equals(downloadFileType)) ? "DM_PAY_IMP_TEMP" : "OTHER_TEMP");
		
		CommonLogger.info("存量业务数据模块中模板文件下载,需要下载的模板文件为："+downloadFileType+",DataMigrateService,downloadTemp");
		//2.根据上传文件的ID从表TB_UPLOAD_FILE_MNGT中查找该Excel模板的相关信息
		UpFileBean upFile = upFileMgrService.getUpFileById(downloadFileType);
		
		if(Tool.CHECK.isEmpty(upFile)){
			//根据downloadFileType从表TB_UPLOAD_FILE_MNGT中查询不到模板配置文件信息，返回报错信息
			CommonLogger.error("存量业务数据模块中模板文件下载,无该下载模板配置信息,请联系管理员检查！模板ID为:["+downloadFileType+"],DataMigrateService,downloadDMTemp");
			resultMap.put("errorCode", "01");					//errorCode=01:TB_UPLOAD_FILE_MNGT中无该id的文件信息
		}else{
			try {
				//3.根据模板路径和Response响应，将文件下载到用户客户端
				//resultMap = DownloadFileUtils.download(upFile.getSourceFName(), upFile.getSourceFPath(),response);	//WebUtils.getResponse()
				dbFileUtil.getFileDownFromPathAndDb(upFile.getSourceFPath(), upFile.getSourceFName(), request, response);
				resultMap.put("errorCode", "00");				//errorCode=00:表示是正常的结束结果
				CommonLogger.debug("存量业务数据模块中模板文件下载成功!下载的模板ID为:"+downloadFileType+",DataMigrateController,downloadDMTemp()");
			} catch (Exception e) {
				CommonLogger.error("存量业务数据模块中模板文件下载,使用集群方式下载发生异常,模板ID为:["+downloadFileType+"] ,DataMigrateService,downloadDMTemp,错误信息描述 ："+e.getMessage().substring(0, 250));
				e.printStackTrace();
				response.reset();
				resultMap.put("errorCode", "02");				//errorCode=02:下载该文件发生异常
			}
		}
		return resultMap;
	}
	
	
	/**
	 * @methodName  mGetUploadControlBean
	 * 		描述：	进入存量业务数据主页，查询当前登录用户一级行下的(dataFlag过滤后)导入批次相关信息，并展示在结果列表中  
	 * @return
	 */
	public UploadDataControlInfoBean mGetUploadControlBean(){
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		CommonLogger.info("查找当前登录用户["+WebHelp.getLoginUser().getUserId()+"]所在一级行机构["+org1Code+"]的导入存量业务批次对象,DataMigrateService,mGetUploadControlBean");
		UploadDataControlInfoBean udcBean = queryUploadControlByOrg1Code(org1Code);
		if(udcBean == null)
		{
			CommonLogger.debug("一级行机构["+org1Code+"]没有进行存量业务数据导入,系统将初始化一个并赋值状态为09未导入,DataMigrateService,mGetUploadControlBean");
			//表中若无该一级行的导入数据信息时，则新建一个，并将其状态设为09"未导入";			
			udcBean = new UploadDataControlInfoBean();
			udcBean.setDataFlag("09");
			udcBean.setOrg1Code(org1Code);
		}
		return udcBean;
	}
	
	/**
	 * @methodName  queryUploadControlByOrg1Code
	 *		描述：	通过登录用户一级行机构查询数据迁移批次号(SQL中对DATA_FLAG != '02' && DATA_FLAG != '03'进行过滤，即非"确认完成"、非"删除"状态的)
	 * @param 		org1Code	登录用户一级行编号
	 * @return
	 */
	public UploadDataControlInfoBean queryUploadControlByOrg1Code(String org1Code){
		CommonLogger.info("存量业务数据模块,根据一级行查询其所导入批次的业务数据,查询的一级行为["+org1Code+"],DataMigrateService,queryUploadControlByOrg1Code()");
		return dataMigrateDao.queryUploadControlByOrg1Code(org1Code);
	}
	
	/**
	 * @methodName addUpDataControlInfo
	 * 		描述：	添加数据迁移的导入批次数据记录	——————>	表UPLOAD_DATA_CONTROL_INFO		
	 * @param batchId		导入批次ID
	 * @param instUserId	操作者ID
	 * @param instOrg1Code	操作者一级行编号
	 * @param cntTaskId		合同导入任务TaskId
	 * @param payTaskId		付款导入任务TaskId
	 */
	public void addUpDataControlInfo( String batchId , String instUserId , String instOrg1Code ,String cntTaskId , String payTaskId ){
		CommonLogger.info("存量业务数据模块,新增导入批次数据记录,批次号为["+batchId+"],操作机构为["+instOrg1Code+"]," +
								"启动合同数据导入任务ID["+cntTaskId+"],启动付款数据导入任务ID["+payTaskId+"],DataMigrateService,addUpDataControlInfo");
		UploadDataControlInfoBean upDataInfo = new UploadDataControlInfoBean(batchId , instUserId , instOrg1Code ,cntTaskId , payTaskId);
		dataMigrateDao.addUpDataControlInfo(upDataInfo);
	}
	
	/**
	 * @methodName 	importCntDataToCNTDATA
	 * 		方法描述：	导入Excel模板-[合同数据]相关信息  
	 * 		处理逻辑：	1.添加导入相关的ConfigId数组和Excel导入的描述
	 * 					2.取得上传的Excel文件信息
	 * 					3.添加导入处理的map传递参数
	 * 					4.执行调用Excel导入组件
	 * @param 		request
	 */
	@Transactional(rollbackFor = Exception.class)
	public String importCntDataToCNTDATA(String filePath , String impBatch) {
		CommonLogger.info("存量业务数据模块,执行合同数据导入操作,导入批次为：["+impBatch+"],DataMigrateService,importCntDataToCNTDATA()");
		//1.根据数据导入的类型进行相关配置和导入描述
		String[] configIds = new String[]{ "DM_CNTINFO" , "DM_CNTMATR" , "DM_CNTFK" , "DM_CNTTENANCY"};		//Sheet对应的导入配置Id数组(有顺序要求)
		String   excelDesc = "【合同数据】模板相关Excel导入操作，导入批次号["+impBatch+"]";					//当前Excel导入操作描述
		boolean invokeFlag = false;																			//是否正常调用导入任务标识(当Excel前置校验不通过时，则不进行调用Excel导入组件)
		String   cntTaskId = "";																			//合同Excel导入任务TaskId(调用Excel导入组件时，会产生该任务Id，并作为返回值返回)
		
		//【公共】记录Log日志
		CommonLogger.info("【存量业务数据】导入合同相关数据，使用Excel导入模板[合同数据.xls],"+configIds.length+"个Sheet，DataMigrateService，importCntDataToCNTDATA()");
		
		//2.获取上传需要导入的Excel文件信息(1=前置路径、2=文件名称)
		String[] excelInfos = CommonFileUtils.getExcelInfoByPath(filePath);
		
		//3.添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("impBatch", impBatch);								//导入批次号
		beans.put("uploadType", "01");									//Excel模板类型：01 = 合同数据.xlsx
		beans.put("lastConfigId", configIds[configIds.length-1]);		//配置Config的最后一张Sheet的Id----处理一个Excel中存在多个Sheet导入时(用于最后更新该Task任务状态和ControlBean的dataFlag)
		
		//4.执行调用Excel导入组件
		if(true){							//暂时使用
//		if(POIParseExcelUtil.validateCellValue(filePath, 0, 19, 0, Cell.CELL_TYPE_STRING, "9527")){				//【暂时注释】校验指定cell Key(参数说明：文件路径,SheetIndex,rowIndex,colIndex,Cell值类型,预计值)
			//【Check1】：Excel文件Key值校验通过，鉴定为系统模板，开始执行Excel数据导入操作
			
			//【Check2】：校验Sheet下标是否会发生越界
			HSSFWorkbook workbook = POIParseExcelUtil.getWorkbookByFilePath(filePath);			//获取工作簿
			if(configIds.length > workbook.getNumberOfSheets()){								//获取工作簿的工作表个数，再进行比较
				CommonLogger.error("解析配置ConfigId个数与Excel工作簿中Sheet个数不一致（源文件Sheet个数少于需要解析Sheet个数），发生Excel配置解析异常！请检查。,DataMigrateService，importCntDataToCNTDATA()");
				return null;
			}
			
			try {
				cntTaskId = importGeneralDeal.execute(excelInfos[1], excelInfos[2], excelDesc, configIds, beans);	//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
				invokeFlag = true;
			} catch (Exception e) {
				CommonLogger.error("【存量业务数据】导入合同相关数据发生错误！导入批次为：["+impBatch+"],DataMigrateService，importCntDataToCNTDATA()");
				e.printStackTrace();
				Throw.throwException(e);
			}
			CommonLogger.info("【存量业务数据】批次号["+impBatch+"]的合同数据导入操作执行完毕，操作成功！,DataMigrateService，importCntDataToCNTDATA()");
			return cntTaskId;
		}else{
			//Excel文件Key值校验错误，任务非系统模板，不执行Excel数据导入操作
			return null;
		}
		
		
	}
	
	/**
	 * @methodName importPayDataToPAYDATA
	*		方法描述：	导入Excel模板-[付款数据]相关信息  
	 * 		处理逻辑：	1.取得上传的Excel文件信息
	 * 					2.添加导入相关的ConfigId数组和Excel导入的描述
	 * 					3.添加导入处理的map传递参数
	 * 					4.执行调用Excel导入组件
	 * @param request
	 */
	@Transactional(rollbackFor = Exception.class)
	public String importPayDataToPAYDATA(String filePath , String impBatch){
		CommonLogger.info("【存量业务数据】导入付款相关数据,导入批次号为["+impBatch+"]使用Excel导入模板[付款数据.xls],共5个Sheet,DataMigrateService,importPayDataToPAYDATA()");
		boolean invokeFlag = false;
		String payTaskId = "";
		//1.获取上传导入的Excel文件信息
		String[] excelInfos = CommonFileUtils.getExcelInfoByPath(filePath);
		
		//2.根据数据导入的类型进行相关配置和导入描述
		String[] configIds = new String[]{"DM_TDPAYADVANCE","DM_TDPAY","DM_TDPAYADCANCEL","DM_TDPAYDEVICEAD","DM_TDPAYDEVICENOR"};
		String excelDesc = "【付款数据】模板相关Excel导入操作；";
		
		//3.添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("impBatch", impBatch);
		beans.put("uploadType", "02");
		beans.put("lastConfigId", configIds[configIds.length-1]);		//配置Config的最后一张Sheet的Id----处理一个Excel中存在多个Sheet导入时(用于最后更新该Task任务状态和ControlBean的dataFlag)
		
		//4.执行调用Excel导入组件
		try {
			payTaskId = importGeneralDeal.execute(excelInfos[1], excelInfos[2], excelDesc, configIds, beans);	//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
			invokeFlag = true;
		} catch (Exception e) {
			CommonLogger.error("【存量业务数据】导入付款相关数据发生错误！导入批次为["+impBatch+"],,DataMigrateService,importPayDataToPAYDATA()");
			e.printStackTrace();
		}
		CommonLogger.info("【存量业务数据】-付款数据导入成功,导入批次为["+impBatch+"],DataMigrateService,importPayDataToPAYDATA()");
		return payTaskId;
	}
	
	/**
	 * @methodName callInitMainCheck
	 *		描述：	调用存储过程进行合同+付款数据的校验(该存储过程内部再进行子存储过程调用校验) 
	 * @param batchNo		导入批次号
	 * @return
	 */
	public int callInitMainCheck(String batchNo){
		CommonLogger.info("存量业务数据模块,执行存储过程校验批次下导入的合同数据+付款数据,当前操作的批次为["+batchNo+"],DataMigrateService,callInitMainCheck()");
		return dataMigrateDao.callInitMainCheck(batchNo);
	}
	
	/**
	 * @methodName updateUpDataConInfoDataFlag
	 * 		描述：	根据导入批次号，更新导入批次数据记录的数据状态(根据传递参数dataFlag决定)
	 * @param dataFlag		数据状态
	 * @param batchNo		导入批次号
	 */
	public void updateUpDataConInfoDataFlag(String dataFlag , String batchNo){
		CommonLogger.info("存量业务数据模块,执行根据导入批次号更新数据记录的数据状态,当前操作的批次为["+batchNo+"],DataMigrateService,updateUpDataConInfoDataFlag()");
		dataMigrateDao.updateUpDataConInfoDataFlag(dataFlag, batchNo);
	}
	
	/**
	 * @methodName mGetCntList
	 * 		描述：	用于【明细】功能中，根据导入批次号，查询该导入批次中的合同列表数据
	 * @param selectInfo
	 * @return
	 */
	public List<CntVerifyBean> mGetCntList(CntVerifyBean selectInfo){
		CommonLogger.info("存量业务数据导入模块,查询导入批次["+selectInfo.getBatchNo()+"]下的导入合同数据操作-START,DataMigrateService,mGetCntList");
		DataMigrateDao pageDao = PageUtils.getPageDao(dataMigrateDao);
		return pageDao.queryCntList(selectInfo);
	}
	
	/**
	 * @methodName dmExecDelOperByBatchNo
	 * 		描述：	【删除】功能操作；
	 * 				功能逻辑：根据导入批次号找到导入批次，将该批次的状态更改为"03"删除；并记录该删除操作的操作时间和操作者信息  
	 * @param batchNo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int dmExecDelOperByBatchNo(String batchNo){
		CommonLogger.info("存量业务数据模块，执行导入批次["+batchNo+"]删除操作；DataMigrateService,dmDelImpAndRelDataByBatchNo()");
		int result = 0;
		UploadDataControlInfoBean udcBean = dataMigrateDao.getUdcBeanByBatchNo(batchNo);
		udcBean.setUpdtOper(WebHelp.getLoginUser().getUserId());					//记录删除操作的操作者信息
		try {
			result = dataMigrateDao.dmExecDelOperByBatchNo(udcBean);
		} catch (Exception e) {
			CommonLogger.error("存量业务数据模块,删除导入批次数据发生异常,当前批次号为["+batchNo+"],DataMigrateService,dmDelImpAndRelDataByBatchNo()");
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 根据导入批次号，获取该导入批次的数据状态DataFlag
	 */
	public String getUpDataControllerDataFlagByBatchNo(String batchNo){
		CommonLogger.error("存量业务数据模块,执行查询导入批次的数据状态,当前查询批次号为["+batchNo+"],DataMigrateService,getUpDataControllerDataFlagByBatchNo()");
		return dataMigrateDao.getUpDataControllerDataFlagByBatchNo(batchNo);
	}
	
	/*
	 * 根据一个导入批次中其中一个导入任务TaskId获取其中的另一个导入任务TaskId
	 */
	public UploadDataControlInfoBean getRelTaskIdByBatchTaskId(String taskId){
		CommonLogger.error("存量业务数据模块,根据同一导入批次下的一个导入任务ID查询另一个导入任务ID,当前导入任务ID为["+taskId+"],DataMigrateService,getRelTaskIdByBatchTaskId()");
		return dataMigrateDao.getUpDataControlByOneTaskId(taskId);
	}
	
	/*
	 * 根据导入批次号获取该批次中的导入校验错误信息
	 */
	public List<UploadDataErrorInfoBean> getUpDataErrByBatchNo(String batchNo){
		CommonLogger.error("存量业务数据模块,根据导入批次号获取该批次中的导入校验错误信息,当前操作批次号为["+batchNo+"],DataMigrateService,getUpDataErrByBatchNo()");
		return dataMigrateDao.getUpDataErrByBatchNo(batchNo);
	}
	
	/*
	 * 根据批次号batchNo和Excel模板类型uploadType从UPLOAD_DATA_ERROR_INFO表中查找数据[参数1：导入批次号 、 参数2：Excel模板类型  、参数3：校验区]
	 */
	public int getUpDataErrCountByBatchAndUpType(String batchNo , String uploadType ,String sectionType){
		CommonLogger.error("存量业务数据模块,根据导入批次号和导入的Excel模板类型统计其校验为错误的数据行数,当前操作批次号为["+batchNo+"],Excel的类型为["+uploadType+"],DataMigrateService,getUpDataErrCountByBatchAndUpType()");
		return Integer.parseInt(dataMigrateDao.getUpDataErrCountByBatchAndUpType(batchNo, uploadType ,sectionType));
	}
	
	/*
	 * 根据批次号batchNo取得CNT合同的基配校验成功插入数据
	 */
	public int getCntSuccImpCountByBatchNo(String batchNo){
		int cntSuccCount = Integer.parseInt(dataMigrateDao.getCntSuccImpCountByBatchNo(batchNo));
		CommonLogger.info("存量业务数据导入模块,统计导入批次中校验无错的付款数据行数,当前批次为["+batchNo+"],无错行["+cntSuccCount+"]行,DataMigrateService,getImpCntRelateInfoByCntNum");
		return cntSuccCount;
	}
	
	/*
	 * 根据批次号batchNo取得PAY付款的基配校验成功插入数据
	 */
	public int getPaySuccImpCountByBatchNo(String batchNo){
		int paySuccCount = Integer.parseInt(dataMigrateDao.getPaySuccImpCountByBatchNo(batchNo));
		CommonLogger.info("存量业务数据导入模块,统计导入批次中校验无错的付款数据行数,当前批次为["+batchNo+"],无错行["+paySuccCount+"]行,DataMigrateService,getImpCntRelateInfoByCntNum");
		return paySuccCount;
	}
	
	
	/*
	 * 根据合同导入批次号+导入信息中的合同号，查询该批次中合同的关联信息数据
	 */
	public CntVerifyBean getImpCntRelateInfoByCntNum(CntVerifyBean cntVerBean){
		CommonLogger.info("存量业务数据导入模块,查询导入合同中指定合同下的明细信息操作,当前查询合同号为["+cntVerBean.getCntNum()+"],DataMigrateService,getImpCntRelateInfoByCntNum");
		CntVerifyBean cntRelateBean = dataMigrateDao.getCntMainInfoByCntNum(cntVerBean);					//合同数据主体信息
		cntRelateBean.setCntRelDeviceList(dataMigrateDao.getCntRelDevices(cntVerBean));						//合同关联的物料信息
		cntRelateBean.setCntRelPayInfoList(dataMigrateDao.getImpPayListByCntAndBatchNo(cntVerBean));		//合同关联的付款数据信息
		if("3".equals(cntRelateBean.getPayTerm())){															//当该合同的付款类型为"3分期付款"时，查询其分期付款相关信息
			//STAGE_TYPE	分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
			cntRelateBean.setCntRelFqfkList(dataMigrateDao.getCntRelFqfkInfos(cntVerBean));					//合同关联的分期付款信息
		}
		if("1".equals(cntRelateBean.getCntType()) && "1".equals(cntRelateBean.getFeeSubType())){			//当该合同类型为"1费用类"并且费用子类型为"1房屋租赁类"时，合同Bean才有下述属性值
			cntRelateBean.setCntRelTenancyList(dataMigrateDao.getCntRelTenancies(cntVerBean));				//合同关联-房屋租赁类-房租递增信息
		}
		return cntRelateBean;
	}
	
	/*
	 * 查询正常付款/预付款的相关详情数据
	 */
	public Object getPayDetailInfo(CntRelPayInfo cntPayBean){
		CommonLogger.info("存量业务数据导入模块,查询导入"+("01".equals(cntPayBean.getPayType())? "正常" : "预")+"付款明细数据的操作,DataMigrateService,getPayDetailInfo");
		if("01".equals(cntPayBean.getPayType())){
			//查询正常付款相关
			TdPayBean norPayRelInfo = dataMigrateDao.getNormalPayDetail(cntPayBean);
			norPayRelInfo.setPayAdCancelList(dataMigrateDao.getPayAdCancelByBatchAndPayId(cntPayBean));		//塞入正常付款关联的预付款核销信息	
			CommonLogger.info("存量业务数据导入模块,查询正常付款关联的预付款核销信息操作,正常付款ID["+cntPayBean.getPayId()+"]");
			norPayRelInfo.setPayDeviceList(dataMigrateDao.getPayDevByBatchAndPayId(cntPayBean));			//塞入正常付款关联的物料信息
			CommonLogger.info("存量业务数据导入模块,查询正常付款关联的物料信息操作,正常付款ID["+cntPayBean.getPayId()+"]");
			return norPayRelInfo;
		}else if("02".equals(cntPayBean.getPayType())){
			//查询预付款相关
			TdPayAdvanceBean adPayRelInfo = dataMigrateDao.getAdPayDetail(cntPayBean);
			return adPayRelInfo;
		}else{
			return cntPayBean;
		}
	}
	
	//插入合同数据Sheet1[合同信息]		——————>表UPLOAD_TD_CNT
	public void insertToTbCNTDATA(List<CntInfoBean> cntInfoList){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至合同信息表,DataMigrateService,insertToTbCNTDATA()");
		dataMigrateDao.addCntInfo(cntInfoList);
	}
	
	//插入合同数据Sheet2[合同物料信息]	——————>表UPLOAD_TD_CNT_DEVICE
	public void insertToTDCNTDEVICE(List<CntMatrInfoBean> cntMatrList){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至合同物料信息表,DataMigrateService,insertToTDCNTDEVICE()");
		dataMigrateDao.addCntDevice(cntMatrList);
	}
	
	//插入合同数据Sheet3[合同分期付款信息]——————>表UPLOAD_TD_CNT_FK
	public void insertToTDCNTFK(List<CntFqfkBean> cntFqfkList){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至合同分期付款信息表,DataMigrateService,insertToTDCNTFK()");
		dataMigrateDao.addCntFk(cntFqfkList);
	}
	
	//插入合同数据Sheet4[租金递增条件]	——————>表UPLOAD_TD_CNT_TENANCY_DZ
	public void insertToTDCNTTENANCY(List<CntTenancyCondiBean> cntTenancyList){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至合同租金递增条件表,DataMigrateService,insertToTDCNTTENANCY()");
		dataMigrateDao.addCntTenancy(cntTenancyList);
	}
	
	//插入付款数据Sheet1[预付款信息]		——————>表UPLOAD_TD_PAY_ADVANCE
	public void insertToTDPAYADVANCE(List<TdPayAdvanceBean> payAdvanceInfo){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至预付款信息表,DataMigrateService,insertToTDPAYADVANCE()");
		dataMigrateDao.addTdPayAdvance(payAdvanceInfo);
	}
	
	//插入付款数据Sheet2[正常付款信息]	——————>表UPLOAD_TD_PAY
	public void insertToTDPAY(List<TdPayBean> payInfo){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至正常付款信息表,DataMigrateService,insertToTDPAY()");
		dataMigrateDao.addTdPayInfo(payInfo);
	}
	
	//插入付款数据Sheet3[预付款核销信息]	——————>表UPLOAD_TD_PAY_ADVANCE_CANCEL
	public void insertToTdPayAdvanceCancel(List<TdPayAdvanceCancelBean> payAdCancelInfo){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至预付款核销信息表,DataMigrateService,insertToTdPayAdvanceCancel()");
		dataMigrateDao.addTdPayAdvanceCancel(payAdCancelInfo);
	}
	
	//插入付款数据Sheet4[预付款核销物料]/Sheet5[正常付款物料]——————>表UPLOAD_TD_PAY_ADVANCE_DEVICE / 表UPLOAD_TD_PAY_DEVICE
	public void insertToTdPayDevice(List<TdPayDeviceBean> payDeviceInfo , String payType){
		CommonLogger.info("存量业务数据导入模块,批量插入导入数据至预付款核销物料/正常付款物料表,DataMigrateService,insertToTdPayDevice()");
		Map<String , Object> paramObj = new HashMap<String , Object>();
		paramObj.put("table", "");
		//（使用字段PAY_TYPE区分----0:预付款核销物料、1：正常付款物料），插入数据到不同的表
		if("0".equals(payType)){
			//Sheet4：预付款核销物料信息
			paramObj.put("table", "UPLOAD_TD_PAY_ADVANCE_DEVICE");
		}else if("1".equals(payType)){
			//Sheet5：正常付款物料信息
			paramObj.put("table", "UPLOAD_TD_PAY_DEVICE");
		}
		paramObj.put("payDeviceInfo", payDeviceInfo);
		dataMigrateDao.addTdPayDevice(paramObj);
	}
	
	//【工具类】：校验检查多个文件的大小是否复核要求(可以考虑放置于CommonFileUtil类中)
	public Map<String , Object> getFileSizeCheckResult(String[] filePaths , int allowFileSize){
		CommonLogger.info("【工具类】执行文件大小校验操作：需要校验的文件路径为["+StringUtil.arrayToString(filePaths)+"],允许上传的文件大小为["+allowFileSize+"MB],DataMigrateService,getFileSizeCheckResult()");
		//该Map的key为：fileSizes(记录校验通过的每个文件的大小) 、validateResult 、errMsg
		Map<String , Object> returnMap = new HashMap<String , Object>();		
		File file ;
		double fileSize ;														//当前读取的文件大小
		String[] fileSizes = new String[2]; 									//两个文件的大小(下表从0开始)
		boolean validateResult = true;
		String errMsg = "";
		//循环检查文件List中各文件大小是否均符合大小要求
		for(int i=0;i<filePaths.length;i++){
			file = new File(filePaths[i]);
			//四舍五入取两位小数，最后保留两位小数
			fileSize = Math.round(((double)file.length() / 1024 / 1024 )* 100 ) / 100.0;
			fileSizes[i] = String.valueOf(fileSize);
			if(fileSize > allowFileSize){
				validateResult = false;
				errMsg = "第["+(i+1)+"]个文件超过允许大小,<br/>该文件实际大小为："+fileSize+"MB；<br/>请上传小于"+allowFileSize+"MB的文件";
				break;
			}
		}
		returnMap.put("fileSizes", fileSizes);
		returnMap.put("validateResult", validateResult );
		returnMap.put("errMsg", errMsg);
		return returnMap;
		
	}
	
	
	/**
	 * @methodName copyFile
	 *		【工具类】：IO流复制多个文件 
	 * @param impFiles
	 * @param urls
	 * @throws IOException
	 */
	public void copyFile(List<MultipartFile> impFiles , String[] urls) throws IOException{
		CommonLogger.info("【工具类】执行IO流复制多个文件,复制文件个数：["+impFiles.size()+"],DataMigrateService,copyFile()");
		for(int i=0;i<urls.length;i++){
			OutputStream out = null;
			InputStream in = null;
			try {
				out = new FileOutputStream(urls[i]);
				in = impFiles.get(i).getInputStream();
				IOUtils.copy(in, out);
			} catch (IOException e) {
				throw e; 
			}finally{
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}
	}
	
	/**
	 * @methodName mGetDataVerify
	 *		描述：	【确认】按钮，数据确认操作，根据导入批次号将导入数据从缓存表转移至生产表中
	 *		逻辑处理： 调用存储过程DATA_INIT_MAIN_FLASH执行数据迁移操作
	 * @param selectInfo
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void mGetDataVerify(UploadDataControlInfoBean selectInfo) throws Exception{
		CommonLogger.info("存量业务数据模块,执行数据确认操作,当数据校验无错时确认并提交该数据,操作机构为["+selectInfo.getOrg1Code()+"],提交的批次数据["+selectInfo.getBatchNo()+"],DataMigrateService,mGetDataVerify()");
		UploadDataControlInfoBean bean = queryUploadControlByOrg1Code(selectInfo.getOrg1Code());
		bean.setUpdtOper(WebHelp.getLoginUser().getUserId());
		try {
			CommonLogger.info("存量业务数据模块,执行数据确认时调用存储过程进行导入合同+付款数据的迁移至生产表中,操作批次为["+selectInfo.getBatchNo()+"],DataMigrateService,mGetDataVerify()");
			dataMigrateDao.callDataInitFlash(bean);
		} catch (Exception e) {
			CommonLogger.error("存量业务数据【数据确认】功能中数据迁移调用的存储过程操作出现异常,当前操作的批次为["+selectInfo.getBatchNo()+"],DataMigrateService,mGetDataVerify()");
			throw new Exception(e);
		}
	}
	
	/**
	 * @methodName mGetDataVerifyCheckResult
	 * 		【确认】按钮：数据确认前的检查及检查结果返回
	 * @param org1Code	登录用户一级行代码
	 * @return
	 */
	public String mGetDataVerifyCheckResult(String org1Code){
		String result = "TRUE";
		//1.根据一级行代码查找该机构当前导入Control批次
		UploadDataControlInfoBean bean = queryUploadControlByOrg1Code(org1Code);			
		//2.根据批次号，查询当前批次是否有检验为错误的数据
		CommonLogger.info("存量业务数据模块,Ajax执行导入数据确认前检查是否存在校验错误的数据,检查的批次为["+bean.getBatchNo()+"],DataMigrateService,mGetDataVerifyCheckResult()");
		List<UploadDataErrorInfoBean> errorList = getUpDataErrByBatchNo(bean.getBatchNo());
		if(errorList != null && errorList.size() > 0){
			result = "FALSE";
		}
		//3.返回校验的结果
		CommonLogger.info("存量业务数据模块,Ajax执行导入数据确认前校验,校验结果为["+result+"],DataMigrateService,mGetDataVerifyCheckResult()");
		return result;
	}
	
	//将前端传进来的文件路径，复制文件至服务器的指定地址
	public String[] saveFileToServerPath( UploadDataControlInfoBean uploadFiles ) throws IOException{
		CommonLogger.info("存量业务数据模块,将上传的多个文件复制保存至服务器的指定地址,DataMigrateService,saveFileToServerPath()");
		List<MultipartFile> impFiles = new ArrayList<MultipartFile>();
		impFiles.add(uploadFiles.getImpFile1());
		impFiles.add(uploadFiles.getImpFile2());
		String[] serverFiles = new String[2];
		String uploadTempUrl = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL");
		//判断上传缓存路径是否存在，不存在则首先创建该路径
		CommonFileUtils.createFileFolder(uploadTempUrl,null);
		serverFiles[0] = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/"+DateUtil.getDateTimeStrNo()+"_"+impFiles.get(0).getOriginalFilename();
		serverFiles[1] = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/"+DateUtil.getDateTimeStrNo()+"_"+impFiles.get(1).getOriginalFilename();
		CommonLogger.info("存量业务数据模块,保存的地址分别为["+serverFiles[0]+"]+["+serverFiles[1]+"],DataMigrateService,saveFileToServerPath()");
		try {
			this.copyFile(impFiles , serverFiles);						//可能发生异常，将该异常抛出Controller层进行处理
		} catch (Exception e) {
			Throw.throwException(e);
		}
		return serverFiles;											//将保存至服务器上的路径[](数组)返回
	}
	
}
