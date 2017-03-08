package com.forms.prms.tool.constantValues;

public enum OperateValues {

	ADD("新增"), UPDATE("修改"), DELETE("删除"),TOCHANGE("发起变更"),STARTDELIVER("发起移交"),CHECKPASS("物料复核通过"),CONTRACTPASS("合同复核通过")
	,CHECKBACK("物料复核退回"),ACCEPTDELIVER("接受移交"),REJECTDELIVER("拒绝移交"),CANCELDELIVER("取消移交"),CNTEND("合同取消")
	,CNTFINISH("合同终止"),FREEZE("合同冻结"),UNFREEZE("合同解冻"),PROJEND("项目终止"),CNTCONFIRM("合同确认通过")
	,CNTRETURN("合同确认退回"),CONFIRMCHG("合同变更确认"),ORDERSTARTBACK("订单确认退回"),ORDEREDITBACK("订单修改退回");

	private String value;

	private OperateValues(String code) {
		this.value = code;
	}

	@Override
	public String toString() {
		return value;
	}
}
