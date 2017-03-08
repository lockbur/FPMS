package com.forms.prms.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.forms.platform.core.logger.CommonLogger;

public class ShellTool {
	public synchronized static void executeShell(String shellCommand)
			throws Exception {
		int success = -1;
		// 执行正常数据读入io
		InputStream dataInputStream = null;
		InputStreamReader dataReader = null;
		BufferedReader bufferReader = null;

		// 执行错误数据读入io
		InputStream errorInputStream = null;
		InputStreamReader errorReader = null;
		BufferedReader bufferErrorReader = null;
		Process pid = null;
		try {
			pid = Runtime.getRuntime().exec(shellCommand);
			dataInputStream = pid.getInputStream();
			dataReader = new InputStreamReader(dataInputStream);
			bufferReader = new BufferedReader(dataReader);
			String errosStr="";
			String tmp=null;
			while ((tmp=bufferReader.readLine()) != null) {
				// 清除process的流，以免造成process阻塞
				errosStr +=tmp;
			}
			CommonLogger.info("errosStr1: " + errosStr);
			errorInputStream = pid.getErrorStream();
			errorReader = new InputStreamReader(errorInputStream);
			bufferErrorReader = new BufferedReader(errorReader);
			errosStr="";
			 tmp=null;
			while ((tmp=bufferErrorReader.readLine()) != null) {
				// 清除process的流，以免造成process阻塞
				errosStr +=tmp;
			}
			CommonLogger.info("errosStr2: " + errosStr);
			pid.waitFor();
			success = pid.exitValue();
			if (0 != success && 2 != success) {
				throw new Exception("执行【" + shellCommand + "】命令异常");
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
			throw ioe;
		} finally {
			if (bufferReader != null)
				bufferReader.close();

			if (dataReader != null)
				dataReader.close();

			if (dataInputStream != null)
				dataInputStream.close();

			if (bufferErrorReader != null)
				bufferErrorReader.close();

			if (errorReader != null)
				errorReader.close();

			if (errorInputStream != null)
				errorInputStream.close();

			if (pid != null)
				pid.destroy();
		}
	}
}
