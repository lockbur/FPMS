package com.forms.dealexceldata.exceldealtool;

import java.io.File;

import com.forms.dealdata.ShellTool;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;

public class LoadData {
	public void load(String taskBatchNo,String configId, String filePath, String fileName) throws Exception {
		CommonLogger.info("sqlldr start,configId:" + configId + " file :"
				+ filePath + File.separator + fileName);
		File file = new File(filePath, fileName);
		if (!file.exists()) {
			throw new Exception("" + fileName + " is not exist");
		} else {
			filePath = file.getParent();
		}

		String loadDir = "";
		String sqlldr_fullpath = "";
		String osType = System.getProperties().getProperty("os.name").toUpperCase();
		if (osType.startsWith("WINDOWS")) {
			loadDir = WebHelp.getSysPara("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH") + File.separator + "loaddata"+ File.separator +"excel";
		}
		else
		{
			loadDir = WebHelp.getSysPara("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH") + File.separator + "loaddata"+ File.separator +"excel";
			sqlldr_fullpath = System.getProperty("sqlldr_fullpath");
		}
		String shellDir = loadDir+File.separator + "sh";
		String ctlDir = loadDir + File.separator + "ctl";
		String shellCommand = "";
		String loadCmd = "";


//		String dbconstr ="HQERP/HQERP@172.21.1.18/ORCL" ;
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
		shellCommand += " " + configId;
		shellCommand += " " + taskBatchNo;
		shellCommand += " " + sqlldr_fullpath;

		CommonLogger.info("Command:" + shellCommand);
		try {
			ShellTool.executeShell(shellCommand);
		} catch (Exception e) {
			throw new Exception("sqlldr加载文件：" + fileName + "失败");
		}
		CommonLogger.info("sqlldr (success):configId:" + configId + " file :"
				+ filePath + File.separator + fileName);
	}

}
