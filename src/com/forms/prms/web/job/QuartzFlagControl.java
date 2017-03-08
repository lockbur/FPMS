package com.forms.prms.web.job;


/**
 * @ClassName: QuartzFlagControl 
 * @Description: 定时器标识
 * @author wuqm
 * @date 2014-10-8
 *
 */
public class QuartzFlagControl {
	private static boolean SendSmsFlag = true;
	
	public static boolean isSendSmsFlag() {
		return SendSmsFlag;
	}

	public static void setSendSmsFlag(boolean sendSmsFlag) {
		SendSmsFlag = sendSmsFlag;
	}
}
