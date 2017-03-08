package com.forms.prms.tool.constantValues;

public class ApplyDevValues
{

	/*------------------资源申请工作流相关参数---------*/
	/**
	 * 设备申请-总行设备申请流程定义KEY
	 */
	public final String RESOURCE_POOL_APPLY_KEY = "resource_pool_apply";

	/**
	 * 设备申请-申请审批节点ID
	 */
	// public final String APPLY = "apply";
	/**
	 * 设备申请-申请经办节点ID
	 */
	// public final String APPLY_AUDIT = "apply_audit";
	/**
	 * 设备申请-出库经办节点ID
	 */
	// public final String OST_OPER = "ost_oper";
	/**
	 * 设备申请-出库复核节点ID
	 */
	// public final String OST_AUDIT = "ost_audit";

	/**
	 * 设备申请-出库节点ID
	 */
	public final String OPER_OST = "oper_ost";

	/**
	 * 设备申请-提出用户修改节点ID
	 */
	public final String USER_MODIFY = "user_modify";

	/**
	 * 设备申请-结束节点ID
	 */
	public final String END = "endevent1";

	/**
	 * 申请通过
	 */
	public final String APPLY_PASS = "flow7";

	/**
	 * 申请退回
	 */
	public final String APPLY_BACK = "flow14";

	/*-------------------资源申请状态------------------*/
	/**
	 * 设备申请表 申请状态：申请待提交
	 */
	public final String APPLY_STATE_A0 = "A0";

	/**
	 * 设备申请表 申请状态：申请待出库
	 */
	public final String APPLY_STATE_A1 = "A1";

	/**
	 * 设备申请表 申请状态：出库信息保存
	 */
	public final String APPLY_STATE_A2 = "A2";

	/**
	 * 设备申请表 申请状态：申请审批退回待修改
	 */
	public final String APPLY_STATE_A3 = "A3";

	/**
	 * 设备申请表 申请状态：出库经办通过（结束）
	 */
	public final String APPLY_STATE_A4 = "A4";

	/**
	 * 设备申请表 申请状态：提出用户已删除（结束）
	 */
	public final String APPLY_STATE_A5 = "A5";

	/**
	 * 设备申请表 申请状态：用户修改提交
	 */
	public final String APPLY_STATE_A6 = "A6";

	
	/**
	 * 设备申请表 申请状态：申请待提交
	 */
	public final String APPLY_STATE_0 = "0";

	/**
	 * 设备申请表 申请状态：申请待出库
	 */
	public final String APPLY_STATE_1 = "1";

	/**
	 * 设备申请表 申请状态：同意出库
	 */
	public final String APPLY_STATE_8 = "8";

	/**
	 * 设备申请表 申请状态：不同意出库
	 */
	public final String APPLY_STATE_9 = "9";
}
