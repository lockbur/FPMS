package com.forms.prms.tool.constantValues;

import java.util.HashMap;
import java.util.Map;

public enum BusTypes {

	CONTRACT("合同管理"), PROJECT("项目管理"), PAY("付款管理"),ORDER("订单管理");

	private BusTypes(String value) {
		this.value = value;
	}

	private String value;

	@Override
	public String toString() {
		return this.value;
	}

	public static Map<String, String> map = new HashMap<String, String>() {
		{
			put(BusTypes.CONTRACT.value, "url1");
			put(BusTypes.PROJECT.value, "url2");
			put(BusTypes.PAY.value, "url3");
			put(BusTypes.ORDER.value, "url4");
		}
	};

	/**
	 * 根据类型获取菜单url
	 * 
	 * @return
	 */
	public String getMenu() {
		return map.get(this.value);
	}

}
