package com.forms.prms.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.forms.platform.core.util.Tool;

public class StringTag extends TagSupport{

		private static final long	serialVersionUID	= 7540156377479904417L;
		
		private String length;
		private String value;
		private String direction;//方向
		
		
		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public String getLength() {
			return length;
		}

		public void setLength(String length) {
			this.length = length;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		public int doStartTag() throws JspException 
		{
			JspWriter out = null;
			try {
				out = pageContext.getOut();
				StringBuilder outStr = new StringBuilder("");
				outStr.append("<span");
				String outValue ="";
				if (value.length()<=Integer.parseInt(this.length)) {
					outValue = value;
				}else {
					//这里暂时按照都是汉字来截取 
					if (Tool.CHECK.isBlank(direction) || "left".equals(direction)) {
						//从左往右截取
						outValue = this.value.substring(0,Integer.parseInt(length));
					}else {
						outValue = this.value.substring(this.value.length()-Integer.parseInt(this.length),this.value.length());
					}
					
				}
				String title = value;
				outStr.append(" title='");
				outStr.append(title);
				outStr.append("'");
				outStr.append(">");
				outStr.append(outValue);
				outStr.append("</span>");
				out.print(outStr.toString());
			} catch (Exception e) {
				throw new JspException(e);
			}
			return super.doStartTag();
		}
		public static void main(String[] args) {
			String string="0123456";
			System.out.println(string.substring(string.length()-4, string.length()));
		}

}
