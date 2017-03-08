package com.forms.prms.web.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.forms.dealdata.download.DownloadThread;
import com.forms.dealdata.upload.UploadThread;
import com.forms.dealexceldata.exceldealtool.ExcelConfig;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.init.Init;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.platform.core.util.Tool;
import com.forms.platform.excel.exports.configparse.ExportConfig;
import com.forms.platform.excel.imports.configparse.ParseConfig;
import com.forms.prms.tool.Values;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportDealThread;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportDealThread;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.tool.fms.FmsScanLock;
import com.forms.prms.tool.fms.FmsUploadLock;
import com.forms.prms.tool.fms.WakeThread;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.web.common.config.ConfigLoader;
import com.forms.prms.web.sysmanagement.promptinfo.domain.PromptInfoBean;
import com.forms.prms.web.sysmanagement.promptinfo.service.PromptInfoService;
import com.forms.prms.web.tag.service.SystemCommonService;

@Init
public class SystemInitServlet {
	
	public  static  List<PromptInfoBean> list = new ArrayList<PromptInfoBean>();
	String taskTime1 = "01:00";
	boolean isRunUp1 = true;
	ServletContext context=null;
	
	//导入线程
	private ExcelImportDealThread importThread = null;
	//导出线程
	private ExcelExportDealThread exportThread = null;
	
	private Thread downloadThread = null;
	
	private Thread uploadThread = null;
	
	private Thread wakeThread = null;

	/**
	 * 初始化
	 */
	public void init() throws ServletException {
		try {
			context = ConfigLoader.getInstance().getServletContext();
			//取环境变量(数据库参数)
			boolean isTomcat = context.getServerInfo().contains("Tomcat");
			if(isTomcat)
			{
//				System.setProperty("dbconstr", "FPMS_YGZ/FPMS_YGZ@127.0.0.1/orcl");
				System.setProperty("dbconstr", "FPMS/FPMS@127.0.0.1/fpmstest");
				System.setProperty("sqlldr_fullpath", "sqlldr");//此值实际在没有用到，只是用来判断window中存环境变量
			}
			//检测环境变量有无配置
			String dbconstr = System.getProperty("dbconstr");
			CommonLogger.info("dbconstr: " + dbconstr);
			if(Tool.CHECK.isEmpty(dbconstr))
			{
				throw new Exception("未获取到was环境变量(数据库连接)："+dbconstr);
			}
			
			//检测sqlldr的全路径有无配置
			String sqlldr_fullpath = System.getProperty("sqlldr_fullpath");
			CommonLogger.info("dbconstr: " + sqlldr_fullpath);
			if(Tool.CHECK.isEmpty(sqlldr_fullpath))
			{
				throw new Exception("未获取到sqlldr_fullpath环境变量："+sqlldr_fullpath);
			}
			
			CommonLogger.info("/----------------------------------------------------------------------/");
			SystemParamManage.getInstance().init();
			PageUtils.setDefaultPageSize(Values.DEFAULT_PAGE_SIZE);
			
			//fms配置文件读取
			CommonLogger.info("/----------------------<Fmsconfig.xml> reading....---------------------/");
			FMSConfig.init("classpath:FMSConfig.xml");
			CommonLogger.info("/----------------------<FmsConfig.xml> readed success!-----------------/");
			
			//读取页面提示信息至缓存
			CommonLogger.info("/----------------------<提示信息表数据> reading....-------------------------/");
			getPrompt();
			CommonLogger.info("/----------------------<提示信息表数据> readed success!---------------------/");
			
			//excel导入文件的配置文件解析-lsj 2016-01-21
			CommonLogger.info("/----------------------<Excel ExcelConfig> reading.....----------------/");
			ExcelConfig.init("classpath:ImportExcelConfig.xml");
			CommonLogger.info("/----------------------<Excel ExcelConfig> readed success!-------------/");
			
			//excel导入文件的配置文件解析
			CommonLogger.info("/----------------------<Excel ParseConfig> reading.....----------------/");
			ParseConfig.init(context.getRealPath("/WEB-INF/classes/excels/imports"));
			CommonLogger.info("/----------------------<Excel ParseConfig> readed success!-------------/");
			
			//excel导出文件的配置文件解析
			CommonLogger.info("/----------------------<Excel ExportConfig> reading.....---------------/");
			ExportConfig.init(context.getRealPath("/WEB-INF/classes/excels/exports"));
			CommonLogger.info("/----------------------<Excel ExportConfig> readed success!------------/");
			
			//启动excel文件的导入线程
			importThread = new ExcelImportDealThread();
			new Thread(importThread).start();
			CommonLogger.info("/--------------------- excel import thread start success---------------/");
			
			//启动excel文件的导出线程
			exportThread = new ExcelExportDealThread();
			new Thread(exportThread).start();
			CommonLogger.info("/--------------------- excel export thread start success---------------/");
			
			//启动fms文件下载处理线程
			downloadThread = new Thread((Runnable)SpringHelp.getBean("downloadThread"));
			downloadThread.start();
			CommonLogger.info("/--------------------- fms thread start success------------------------/");
			
			//启动fms文件下载处理线程
			uploadThread = new Thread((Runnable)SpringHelp.getBean("uploadThread"));
			uploadThread.start();
			CommonLogger.info("/--------------------- uploadThread start success------------------------/");
			
			//启动唤醒fms文件下载处理线程
			wakeThread = new Thread((Runnable)SpringHelp.getBean("wakeThread"));
			wakeThread.start();
			CommonLogger.info("/--------------------- wake thread start success------------------------/");
			
			CommonLogger.info("/--------------------Create Folder In System Start success----------------------/");
			//系统初始化时检查创建相关路径的文件夹
			createFolderInSysInit();
			SystemCommonService service = SpringHelp.getBean(SystemCommonService.class);
			CommonLogger.info("/--------------------初始化机构树到内存----------------------/");
			service.init();
			
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("System initalizing error:" + e.getMessage());
		}
	}

	/**
	 * 销毁线程
	 */
	public void destory(){
		DownloadThread.bStop=true;
		FmsScanLock.newInstance().executeWake();
		UploadThread.bStop=true;
		FmsUploadLock.newInstance().executeWake();
		WakeThread.bStop=true;
//		downloadThread.interrupt();
		importThread.stopRun();
		exportThread.stopRun();
	}
	/**
	 * 将提示信息放进内存
	 */
	public void getPrompt(){
		list = PromptInfoService.getInstance().promptInfoList();
		
	}
	
	/**
	 *	系统在初始化时，根据SYS_PARAMETER中配置的路径参数，创建相应的文件夹目录 
	 *		FILE_UPLOAD_BASE_URL		本地服务器上传文件根目录
	 *		UPFILE_MNGT_BASE_URL		上传文件管理模块文件保存目录
	 *		EXCEL_UPLOAD_FILE_DIR		Excel导入文件保存路径
	 *		EXCEL_DOWNLOAD_FILE_DIR		Excel导出文件保存路径
	 *		FMS_DOWNLOAD_LOCAL_FOLDER	FMS下载文件路径
	 *		FTP_DOWNLOAD_FOLDER			FTP下载文件路径
	 */
	public void createFolderInSysInit(){
		CommonLogger.info("执行创建系统相关文件目录操作,系统初始化时执行START........,SystemInitServlet,createFolderInSysInit()");
		//1.创建List，用于保存需要创建的文件夹路径字符串
		List<String> initDestPathList = new ArrayList<String>();
		//2.将需要创建的路径加入list
		initDestPathList.add(WebHelp.getSysPara("FILE_UPLOAD_BASE_URL"));						//平台上传控件保存文件的缓存路径
		initDestPathList.add(WebHelp.getSysPara("UPFILE_MNGT_BASE_URL")+"/"+"file_type01");		//01系统控件类
		initDestPathList.add(WebHelp.getSysPara("UPFILE_MNGT_BASE_URL")+"/"+"file_type02");		//02Excel模板类
		initDestPathList.add(WebHelp.getSysPara("UPFILE_MNGT_BASE_URL")+"/"+"file_type03");		//03其他类
		initDestPathList.add(WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR"));						//Excel上传文件夹
		initDestPathList.add(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR"));					//Excel下载文件夹
		initDestPathList.add(WebHelp.getSysPara("FMS_DOWNLOAD_LOCAL_FOLDER"));					//FMS本地下载文件夹
		initDestPathList.add(WebHelp.getSysPara("FMS_UPLOAD_LOCAL_FOLDER"));					//FMS本地上传文件夹
		//3.循环判断并创建所需要的路径
		for(int i=0;i<initDestPathList.size();i++){
			//校验若该路径不存在，则创建文件夹；若已存在则不创建
			CommonFileUtils.createFileFolder(null,initDestPathList.get(i));
		}
		CommonLogger.info("执行创建系统相关文件目录完毕,系统初始化时执行END........,SystemInitServlet,createFolderInSysInit()");
	}
	
}
