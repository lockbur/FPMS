package com.forms.prms.tool.constantValues;


public class FmsValues {

	public final static String LINE_SEPARATOR = "\n"; 
	public static String END_LINES = "";
	static{
		END_LINES += "|||||Begin" + LINE_SEPARATOR;
		END_LINES += "|||||TabName=${TabName}" + LINE_SEPARATOR;
		END_LINES += "|||||Version=${Version}" + LINE_SEPARATOR;
		END_LINES += "|||||SysID=${SysID}" + LINE_SEPARATOR;
		END_LINES += "|||||InfCenterID=${InfCenterID}" + LINE_SEPARATOR;
		END_LINES += "|||||ProvinceOrgID=${ProvinceOrgID}" + LINE_SEPARATOR;
		END_LINES += "|||||DataStartDate=${DataStartDate}" + LINE_SEPARATOR;
		END_LINES += "|||||DataEndDate=${DataEndDate}" + LINE_SEPARATOR;
		END_LINES += "|||||IncID=${IncID}" + LINE_SEPARATOR;
		END_LINES += "|||||RecNum=${RecNum}" + LINE_SEPARATOR;
		END_LINES += "|||||Sep=${Sep}" + LINE_SEPARATOR;
		END_LINES += "|||||GenTime=${GenTime}" + LINE_SEPARATOR;
		END_LINES += "|||||End" + LINE_SEPARATOR;
	}
	
	public final static int PAGESIZE = 1000;
	public final static String PAGEKEY = "FMS_PAGEKEY";
	
	/**
	 * 下载汇总列表数据状态：待处理
	 */
	public final static String FMS_DOWNLOAD_FORDEAL = "00";
	public final static String FMS_DOWNLOAD_DEALING = "01";
	public final static String FMS_DOWNLOAD_SUCC	= "02";
	public final static String FMS_DOWNLOAD_FAIL	= "03";
	public final static String FMS_DOWNLOAD_CONFTP_FAIL = "04";//连接FTP失败
	public final static String FMS_DOWNLOAD_CHK_FAIL = "06";//状态 06文件内容校验有误
	
	public final static String FMS_DOWNLOAD_WAITFILE	= "20";//等待FMS返回文件
	
	/**
	 * 状态 	00 待处理	
	 */
	public final static String FMS_UP_FORDEAL = "00";
	/**
	 * 状态 01 上传处理中
	 */
	public final static String FMS_UP_DEALING = "01";
	/**
	 * 状态 02 上传成功
	 */
	public final static String FMS_DOWN_FORDEAL = "02";
	
	/**
	 * 状态 09 上传处理失败
	 */
	public final static String FMS_UP_FAIL = "09";
	
	/**
	 * 状态 10 文件创建成功，等待上送
	 */
	public static final String FMS_CREATE_SUCC = "10";
	
	/**
	 * 状态 03 下载处理中
	 */
	public final static String FMS_DOWN_DEALING = "03";
	/**
	 * 状态 04 下载处理成功
	 */
	public final static String FMS_UPDATE_SUCC = "04";
	/**
	 * 状态 05 下载处理失败
	 */
	public final static String FMS_UPDATE_FAIL = "05";
	/**
	 * 状态 06文件内容校验有误
	 */
	public final static String FMS_UPDATE_CHK_FAIL = "06";
	
}
