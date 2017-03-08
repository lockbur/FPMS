package com.forms.prms.tool.constantValues;

public class ManDayValues

{

	/**
	 * 人日申请流程定义KEY
	 */
	public final String MAN_DAY_SERVICE_KEY = "man_day_service";

	/**
	 * 人天申请提交
	 */
	public final String SUBMIT = "submit";

	/**
	 * 人天申请保存
	 */
	public final String SAVE = "save";

	/**
	 * 设备申请-复核节点ID
	 */
	public final String CHECK = "check";

	/**
	 * 设备申请-提出用户修改节点ID
	 */
	public final String USER_MODIFY = "user_modify";

	/**
	 * 设备申请-审批节点ID
	 */

	public final String EXAMINE_AND_APPROVE = "examine_and_approve";

	/**
	 * 设备申请-出库复核节点ID
	 */
	public final String OUTBOUND_CHECK = "outbound_check";

	/**
	 * 设备申请-验收节点ID
	 */
	public final String ACCEPTANCE = "acceptance";

	/**
	 * 设备申请-验收复核节点ID
	 */
	public final String ACCEPTANCE_CHECK = "acceptance_check";

	/**
	 * 设备申请-结束节点ID
	 */
	public final String END = "endevent1";

	/*-------------------人天申请状态------------------*/

	public final String MD_APPLY_A0 = "A0"; // 申请保存

	public final String MD_APPLY_A1 = "A1"; // 申请提交

	public final String MD_APPLY_B1 = "B1"; // 复核通过（待审批）

	public final String MD_APPLY_B2 = "B2"; // 退回待修改

	public final String MD_APPLY_B3 = "B3"; // 修改已提交

	public final String MD_APPLY_B4 = "B4"; // 提出人删除

	public final String MD_APPLY_C1 = "C1"; // 审批经办通过（待出库复核）

	public final String MD_APPLY_C2 = "C2"; // 审批经办拒绝（退回复核）

	public final String MD_APPLY_C3 = "C3"; // 出库复核通过（待验收）

	public final String MD_APPLY_C4 = "C4"; // 出库复核退回（退回审批）

	public final String MD_APPLY_D1 = "D1"; // 验收经办（待验收复核）

	public final String MD_APPLY_D2 = "D2"; // 验收经办取消

	public final String MD_APPLY_D3 = "D3"; // 验收复核通过（完成）

	public final String MD_APPLY_D4 = "D4"; // 验收复核退回（待验收经办修改）

	public final String MD_STORAGE_A0 = "A0"; // 新增

	public final String MD_STORAGE_A1 = "A1"; // 提交待审批

	public final String MD_STORAGE_A2 = "A2"; // 审批通过（直接入库）

	public final String MD_STORAGE_A3 = "A3"; // 退回待修改

	public final String MD_STORAGE_A4 = "A4"; // 删除

}
