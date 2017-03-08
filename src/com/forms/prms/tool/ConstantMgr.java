package com.forms.prms.tool;

import com.forms.prms.tool.constantValues.ApplyDevValues;
import com.forms.prms.tool.constantValues.CoopActivityValues;
import com.forms.prms.tool.constantValues.DeptNOValues;
import com.forms.prms.tool.constantValues.ManDayValues;
import com.forms.prms.tool.constantValues.ReqTaskTypeValues;
import com.forms.prms.tool.constantValues.RequestSystemValues;
import com.forms.prms.tool.constantValues.SysAttachmentValues;
import com.forms.prms.tool.constantValues.SystemValues;
import com.forms.prms.tool.constantValues.TableIdTypeValues;

/**
 * 
 * @ClassName: ConstantMgr 
 * @Description: 系统常量使用管理类
 * @author 王彪
 * @date 2014-4-2
 *
 */
public abstract class ConstantMgr
{
	/**
	 * 需求对应任务类型常量 
	 */
	public static final ReqTaskTypeValues REQ_TASK_TYPE = new ReqTaskTypeValues();
	
	/**
	 * 系统常用的常量类 add by RYAN 20140409 
	 */
	public static final SystemValues SYSTEM_VALUES = new SystemValues();
	
	/**
	 * 涉及机构部门编号常量类
	 */
	public static final DeptNOValues DEPT_NO_VALUES = new DeptNOValues();
	
	/**
	 * 数据库表ID产生时的seqType定义常量类
	 */
	public static final TableIdTypeValues TABLE_ID_TYPE = new TableIdTypeValues();
	
	/**
	 * 上传附件类型常量
	 */
	public static final SysAttachmentValues SYS_ATT_VALUES = new SysAttachmentValues();
	
	/**
	 * 需求阶段系统参数常量类
	 */
	public static final RequestSystemValues REQUEST_SYSTEM_VALUES = new RequestSystemValues();
	
	/**
	 * 协同管理工作流相关常量类
	 */
	public static final CoopActivityValues COOP_ACT_VALUES = new CoopActivityValues();
	
	/**
	 * 设备资源申请工作流常量类
	 */
	public static final ApplyDevValues APPLY_DEV_VALUES = new ApplyDevValues();
	
	/**
	 * 设备资源申请工作流常量类
	 */
	public static final ManDayValues MAN_DAY_VALUES = new ManDayValues();

}