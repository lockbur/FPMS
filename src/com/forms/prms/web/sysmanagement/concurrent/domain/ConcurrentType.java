package com.forms.prms.web.sysmanagement.concurrent.domain;

public class ConcurrentType {
	public static String Concurrent_A="CONCURRENT_A";//大类  和B C互斥
	
	public static String A1="A1";//合同确立（预提待摊费用类）
	public static String A2="A2";//有费用承担部门的合同变更【资产/费用类】
	public static String A3="A3";//新增付款单【资产类】
	//-----------------------------------------------------------
	public static String Concurrent_B="CONCURRENT_B";//大类 和AC互斥，B1和B2的互斥不是通过锁是通过导入校验代码实现的
	
	public static String B1="B1";//监控指标导入
	public static String B2="B2";//预算导入
	
	public static String Concurrent_C="CONCURRENT_C";//大类  和BC互斥
	public static String C1="C1";//机构撤并

}
