package com.forms.prms.tool.constantValues;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ReqTaskTypeValues 
 * @Description: 需求对应任务类型常量 
 * @author 王彪
 * @date 2014-4-2
 *
 */
public class ReqTaskTypeValues {

	/**
	 * P：立项，Q-在建项目变更，R-维护变更，U-分行特色，B-生产BUG，S-技术支持，O-其他
	 */
	
	//立项
	public final String TASK_TYPE_P = "P";
	
	//在建项目变更
	public final String TASK_TYPE_Q = "Q";
	
	//维护变更
	public final String TASK_TYPE_R = "R";
	
	//分行特色
	public final String TASK_TYPE_U = "U";
	
	//生产BUG
	public final String TASK_TYPE_B = "B";		
	
	//技术支持
	public final String TASK_TYPE_S = "S";		
	
	//其他
	public final String TASK_TYPE_O = "O";		
	
	public static final Map<String, String> taskTypeMap = new HashMap<String, String>();
	
	static{
		taskTypeMap.put("P", "立项");
		taskTypeMap.put("Q", "在建项目变更");
		taskTypeMap.put("R", "维护变更");
		taskTypeMap.put("U", "分行特色");
		taskTypeMap.put("B", "生产BUG");
		taskTypeMap.put("S", "技术支持");
		taskTypeMap.put("O", "其他");
	}
	
}
