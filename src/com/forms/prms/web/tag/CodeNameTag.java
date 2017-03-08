package com.forms.prms.web.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.web.tag.domain.TagCommon;
import com.forms.prms.web.tag.service.TagCommonService;

public class CodeNameTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String tableName;
	private String valueColumn;
	private String textColumn;
	private String selectColumn;
	private String conditionStr = "";
	private String selectedValue;

	public String getSelectColumn() {
		return selectColumn;
	}

	public void setSelectColumn(String selectColumn) {
		this.selectColumn = selectColumn;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public String getTextColumn() {
		return textColumn;
	}

	public void setTextColumn(String textColumn) {
		this.textColumn = textColumn;
	}

	public String getConditionStr() {
		return conditionStr;
	}

	public void setConditionStr(String conditionStr) {
		this.conditionStr = conditionStr;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		JspWriter out = null;
		try {

			out = pageContext.getOut();
			TagCommon tagCommon = new TagCommon();
			tagCommon.setTableName(tableName);
			tagCommon.setSelectColumn(selectColumn);
			tagCommon.setValueColumn(valueColumn);
			tagCommon.setTextColumn(textColumn);
			tagCommon.setConditionStr(conditionStr);
			List<Map> list = SpringUtil.getBean(TagCommonService.class).getOptionList(tagCommon);
			StringBuilder outStr = new StringBuilder("");
			if (null != list && list.size() > 0) {
				for (Map map : list) {
					String key = Tool.STRING.convertToCamel(valueColumn);
					if (map.get(key).toString().equals(this.getSelectedValue())) {
						outStr.append(map.get(Tool.STRING.convertToCamel(textColumn)));
						break;
					}
				}
			}
			out.print(outStr.toString());
		} catch (Exception e) {
			throw new JspException(e);
		}
		return super.doStartTag();
	}

}
