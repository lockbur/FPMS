package com.forms.prms.tool.fileUtils.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 * pdf文件转为swf文件
 * @author wangzf
 * @date 2014-08-06
 */
public class PDFConvertSWFUtil {
	protected final Logger logger = Logger.getLogger(PDFConvertSWFUtil.class);
	
	/**
	 * 待转换的pdf文件
	 */
	private File pdfFile;
	
	/**
	 * 转换后的swf文件
	 */
	private File swfFile;
	
	/**
	 * 当前操作系统类型，1为windows,2为linux
	 */
	private int environment;
	
	public PDFConvertSWFUtil(File pdfFile,File swfFile,int environment){
		this.pdfFile = pdfFile;
		this.swfFile = swfFile;
		this.environment = environment;
	}
	
	/**
     * 将PDF格式文件转换为SWF文件
     * @throws Exception
     */
	public void pdfConvertSWF() throws Exception
    {
        Runtime r=Runtime.getRuntime();
        if(!swfFile.exists()){
            if(pdfFile.exists()){
                if(environment==1){
                    try {
                    	//pdf2swf的安装路径可以改为读取配置文件
                        Process p=r.exec("D:/Program Files/SWFTools/pdf2swf.exe "+pdfFile.getPath()+" -s flashversion=9 -o "+swfFile.getPath()+" -T 9");
                        new loadStream(p.getInputStream()).start();
                        new loadStream(p.getErrorStream()).start();
                        logger.debug("****swf转换成功，文件输出："+swfFile.getPath()+"****");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }else if(environment==2){
                    try {
                        Process p=r.exec("pdf2swf "+pdfFile.getPath()+" -s flashversion=9 -o "+swfFile.getPath()+" -T 9");
                        new loadStream(p.getInputStream()).start();
                        new loadStream(p.getErrorStream()).start();
                        logger.debug("****swf转换成功，文件输出："+swfFile.getPath()+"****");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }else {
            	logger.debug("****pdf不存在，无法转换****");
            }
        }else {
        	logger.debug("****swf已存在不需要转换****");
        }
    }
	
	/**
     * 多线程内部类
      * 读取转换时cmd进程的标准输出流和错误输出流，这样做是因为如果不读取流，进程将死锁
     */
   private static class loadStream extends Thread {
       public InputStream is;
     
       //构造方法
        public loadStream(InputStream is) {
           this.is = is;
       }
     
       public void run() {
           BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
           try {
        	   String str = null;
               //这里并没有对流的内容进行处理，只是读了一遍
               while ((str = br.readLine()) != null);
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               if (br != null) {
                   try {
                       br.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
       }
   }
}
