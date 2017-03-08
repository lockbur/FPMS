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

public class CodeTableTag extends TagSupport{

		private static final long	serialVersionUID	= 7540156377479904417L;
		
		private String tableName;
		private String valueColumn;
		private String textColumn;
		private String selectColumn;
		private String orderColumn;
		private String orderType = "ASC";
		private String conditionStr	= "";
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

		public String getTableName()
		{
			return tableName;
		}

		public void setTableName(String tableName)
		{
			this.tableName = tableName;
		}

		public String getValueColumn()
		{
			return valueColumn;
		}

		public void setValueColumn(String valueColumn)
		{
			this.valueColumn = valueColumn;
		}

		public String getTextColumn()
		{
			return textColumn;
		}

		public void setTextColumn(String textColumn)
		{
			this.textColumn = textColumn;
		}

		public String getOrderColumn()
		{
			return orderColumn;
		}

		public void setOrderColumn(String orderColumn)
		{
			this.orderColumn = orderColumn;
		}

		public String getOrderType()
		{
			return orderType;
		}

		public void setOrderType(String orderType)
		{
			this.orderType = orderType;
		}

		public String getConditionStr()
		{
			return conditionStr;
		}

		public void setConditionStr(String conditionStr)
		{
			this.conditionStr = conditionStr;
		}

		
		@SuppressWarnings("unchecked")
		public int doStartTag() throws JspException 
		{
			JspWriter out = null;
			try {

				out = pageContext.getOut();
				TagCommon tagCommon =new TagCommon();
				tagCommon.setTableName(tableName);
				tagCommon.setSelectColumn(selectColumn);
				tagCommon.setValueColumn(valueColumn);
				tagCommon.setTextColumn(textColumn);
				tagCommon.setConditionStr(conditionStr);
				tagCommon.setOrderColumn(orderColumn);
				tagCommon.setOrderType(orderType);
				List<Map> list=SpringUtil.getBean(TagCommonService.class).getOptionList(tagCommon);
				StringBuilder outStr = new StringBuilder("");
				if (null != list && list.size() > 0)
				{
					for (Map map : list)
					{
						outStr.append("<option value=\"");
						String key = Tool.STRING.convertToCamel(valueColumn);
						outStr.append(map.get(key));
						outStr.append("\"");
						if (map.get(key).toString().equals(this.getSelectedValue()))
						{
							outStr.append(" selected=\"selected\"");
						}
						outStr.append(">");
						String value =Tool.STRING.convertToCamel(textColumn);
						outStr.append(map.get(value));
						outStr.append("</option>");
					}
				}
				else
				{
					outStr.append("<option value=\"\">记录为空</option>");
				}
				out.print(outStr.toString());
			} catch (Exception e) {
				throw new JspException(e);
			}
			return super.doStartTag();
		}

}
