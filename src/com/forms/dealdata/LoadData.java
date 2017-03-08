package com.forms.dealdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.UpLoadBean;

public class LoadData 
{
	public static String FILE_NAME = "fms";
	//1
	//2
	//3
	public int load(String tradeType,String filePath, String fileName,UpLoadBean updateBean,FMSDownloadBean downBean ) throws Exception 
	{
		CommonLogger.info("sqlldr start,tradetpye:" + tradeType + " file :"+filePath+File.separator+fileName);
		File file = new File(filePath,fileName);
	    int fileCount = 0;
		if (!file.exists()) {
			downBean.setDealLog(filePath + "不存在文件:"+fileName);
			updateBean.setDealLog(filePath + "不存在文件:"+fileName);
			throw new Exception("" + fileName + " is not exist");
		}
		else
		{
			filePath = file.getParent();
			fileCount = getFileCount(file);
		}
		String loadDir = "";
		String sqlldr_fullpath = "";
		String osType = System.getProperties().getProperty("os.name").toUpperCase();
		if (osType.startsWith("WINDOWS")) {
			loadDir = WebHelp.getSysPara("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH") + File.separator + "loaddata" + File.separator + "fms";
		}
		else
		{
			loadDir = WebHelp.getSysPara("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH") + File.separator + "loaddata" + File.separator + "fms";
			sqlldr_fullpath = System.getProperty("sqlldr_fullpath");
		}
		String shellDir = loadDir + File.separator + "sh";
		String ctlDir = loadDir + File.separator + "ctl";
		String shellCommand = "";
		String loadCmd = "";
		
		String dbconstr = System.getProperty("dbconstr");
		
		loadCmd = "load.cmd";
		if (!System.getProperty("os.name").toLowerCase().contains("win")) {
			loadCmd = loadCmd.replace(".cmd", ".sh");
		}
		File shellFile = new File(shellDir, loadCmd);
		shellCommand = shellFile.getAbsolutePath();
		shellCommand += " " + dbconstr;
		shellCommand += " " + ctlDir;
		shellCommand += " " + filePath;
		shellCommand += " " + fileName;
		shellCommand += " " + FILE_NAME + tradeType;
		shellCommand += " " + sqlldr_fullpath;
		
		CommonLogger.info("Command:" + shellCommand);
		try
		{
			ShellTool.executeShell(shellCommand);
		}
		catch(Exception e)
		{
			downBean.setDealLog("sqlldr加载文件："+fileName+"失败");
			updateBean.setDealLog("sqlldr加载文件："+fileName+"失败");
			throw e;
		}
		CommonLogger.info("sqlldr (success):tradetpye:" + tradeType + " file :"+filePath+File.separator+fileName);
		return fileCount;
	}
	

	
	private int getFileCount(File file) throws IOException {
		
		BufferedReader br = null;
		String line = "";
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while ((line = br.readLine()) != null) 
			{
				if (line.indexOf("RecNum") != -1)
				{
					return Integer.parseInt(line.split("=")[1]);
				}
			}
		}
		finally 
		{
			IOUtils.closeQuietly(br);
		}
		return 0;
	}
	
	
	private boolean checkCmdRes(String workDir,String tradeType) throws IOException {
		// 存在导数失败文件报错
		File badFile = new File(workDir,FILE_NAME+tradeType+".bad");
		if(badFile.exists())
		{
			return false;
		}
		File logFile = new File(workDir,FILE_NAME+tradeType+".log");
		if(logFile.exists())
		{
			BufferedReader br = null;
			String line = "";
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
				while ((line = br.readLine()) != null) {
					if (line.indexOf("ORA-") != -1)
						return false;
				}
			} finally {
				IOUtils.closeQuietly(br);
			}
			return true;
		}
		return false;
		
	}
	
	public static void main(String[] args) throws Exception {
//		System.setProperty("dbconstr", "cwadm/cwadm@ORAA34");
//		System.setProperty("dbconstr", "HQERP/HQERP@drf");
		System.setProperty("dbconstr", "CWADM/CWADM@orcl82");
		
//		new LoadData().load("11", "E:\\tesgtdata", "TOERP_EMPLOYEE_20151224.TXT");
//		new LoadData().load("12", "E:\\tesgtdata", "TOERP_COMCC_20151224.TXT");
//		new LoadData().load("13", "E:\\tesgtdata", "TOERP_VENDORS_20151224.TXT");
		
		
	}
	
}
