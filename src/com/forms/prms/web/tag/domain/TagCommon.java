package com.forms.prms.web.tag.domain;

import java.io.Serializable;

/**
 * author : wuqm <br>
 * date : 2013-11-4<br>
 * 
 */
public class TagCommon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tableName;
	private String valueColumn;
	private String textColumn;
	private String selectColumn;
	private String orderColumn;
	private String orderType ;
	private String conditionStr	;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionStr == null) ? 0 : conditionStr.hashCode());
		result = prime * result + ((orderColumn == null) ? 0 : orderColumn.hashCode());
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + ((selectColumn == null) ? 0 : selectColumn.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((textColumn == null) ? 0 : textColumn.hashCode());
		result = prime * result + ((valueColumn == null) ? 0 : valueColumn.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagCommon other = (TagCommon) obj;
		if (conditionStr == null) {
			if (other.conditionStr != null)
				return false;
		} else if (!conditionStr.equals(other.conditionStr))
			return false;
		if (orderColumn == null) {
			if (other.orderColumn != null)
				return false;
		} else if (!orderColumn.equals(other.orderColumn))
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		if (selectColumn == null) {
			if (other.selectColumn != null)
				return false;
		} else if (!selectColumn.equals(other.selectColumn))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (textColumn == null) {
			if (other.textColumn != null)
				return false;
		} else if (!textColumn.equals(other.textColumn))
			return false;
		if (valueColumn == null) {
			if (other.valueColumn != null)
				return false;
		} else if (!valueColumn.equals(other.valueColumn))
			return false;
		return true;
	}
	
	public String getTableName() {
		return tableName.toUpperCase().trim();
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
	public String getSelectColumn() {
		return selectColumn;
	}
	public void setSelectColumn(String selectColumn) {
		this.selectColumn = selectColumn;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getConditionStr() {
		return conditionStr;
	}
	public void setConditionStr(String conditionStr) {
		this.conditionStr = conditionStr;
	}
	
}
