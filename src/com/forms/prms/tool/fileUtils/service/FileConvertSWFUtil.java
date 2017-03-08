package com.forms.prms.tool.fileUtils.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import com.forms.platform.core.config.Config;

/**
 * 各格式文件转换为swf文件
 * @author wangzf
 * @data 2014-6-17
 */
public class FileConvertSWFUtil extends Thread{
	protected final Logger logger = Logger.getLogger(FileConvertSWFUtil.class);
   
	/**
	 * 原文件
	 */
	private File docFile;
	
	/**
	 * 原文件的保存路径
	 */
	private String docFilePath;
	
	/**
	 * 原文件名（不带格式后缀）
	 */
	private String docFileName;
	
	/**
	 * 原文件的文件类型
	 */
	private String docFileType;
	
	/**
	 * pdf文件
	 */
	private File pdfFile;
    
	/**
	 * swf文件
	 */
	private File swfFile;
	
	/**
	 * 当前操作系统名称1：windows 2:linux(涉及pdf2swf路径问题)
	 */
	private final static int  ENVIRONMENT;
	
	/**
	 * swf文件的输出路径
	 */
	private final static String SWF_OUTPUT_PATH;
	
	/**
	 * 是否使用FTP上传
	 */
	private final static boolean FTP_SWITCH;
	
	/**
	 * pdf文件存放的位置(本地上传模式下忽略)
	 */
	private final static String TEMP_DIR;
	
	static{
		ENVIRONMENT = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0 ? 1 : 2;
		SWF_OUTPUT_PATH = Config.getConfig("file.swfOuput");
		TEMP_DIR = Config.getConfig("file.tempDir");
		FTP_SWITCH = Config.getBooleanConfig("file.ftpSwitch");
	}
	
	public FileConvertSWFUtil(String docFilePath){
		if(!"/".equals(File.separator)){
			docFilePath = docFilePath.replace("\\", "/");
		}
    	this.docFileName = docFilePath.substring(docFilePath.lastIndexOf("/")+1,docFilePath.lastIndexOf("."));
    	this.docFile = new File(docFilePath);
    	this.docFilePath = docFilePath;
    	this.docFileType = docFilePath.substring(docFilePath.lastIndexOf(".")+1).toUpperCase();
        init(docFilePath);
	}
	
    /**
     * 初始化参数
     * @param docFilePath
     */
    private void init(String docFilePath){
    	String[] pathArr = docFilePath.split("/");
    	int len = pathArr.length;
		String tempPath = "/";
    	if(len>=2){
			tempPath += pathArr[len-2];
		}else{
			String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			tempPath += newDate+"/";
		}
    	if(FTP_SWITCH){//ftp模式上传
    		String pdfFilePath = TEMP_DIR + tempPath;
    		try {
    			File floder = this.buildFolder(pdfFilePath);
    			this.pdfFile = new File(floder,this.docFileName+".pdf");
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}else{
    		String tempStr = docFilePath.substring(0,docFilePath.lastIndexOf("."));
    		this.pdfFile = new File(tempStr+".pdf");
    	}
    	String swfFilePath = SWF_OUTPUT_PATH + tempPath;
    	try {
			File floder = this.buildFolder(swfFilePath);
			this.swfFile = new File(floder,this.docFileName+".swf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void run(){
    	this.conver();
    }
    
    /*
     * 转换主方法
     */
    public boolean conver(){
        if(swfFile.exists()){
            logger.debug("****swf转换器开始工作，该文件已经转换为swf****");
            return true;
        }
        
        if(FTP_SWITCH){
        	FtpFileConvertPDFUtil ftp = new FtpFileConvertPDFUtil(docFilePath,pdfFile,docFileType);
        	ftp.convert();
        }else{
        	FileConvertPDFUtil file = new FileConvertPDFUtil(docFile,pdfFile,docFileType);
        	file.convert();
        }
        
        try {
        	PDFConvertSWFUtil swf = new PDFConvertSWFUtil(pdfFile,swfFile,ENVIRONMENT);
			swf.pdfConvertSWF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        if(swfFile.exists()){
        	logger.debug("****swf转换器开始工作，该文件已经转换完成****");
            return true;
        }else {
            return false;
        }
    }
    
   /**
	 * 创建目录
	 * 
	 * @return
	 */
	private File buildFolder(String fileUrl)throws Exception {
		File floder = new File(fileUrl);
		if (!floder.exists()) {
			if (!floder.mkdirs()) {
				logger.error("创建文件夹出错！path=" + fileUrl);
				throw new Exception("创建文件路径出错！");
			}
		}
		return floder;
	}
}
