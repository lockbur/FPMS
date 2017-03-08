package com.forms.prms.web.report.domain;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.forms.prms.web.user.domain.User;

public class ThisReport {
	
	private HttpServletRequest request = null;
	
	private User user = null;
	
	private String tableFile = null;
	
	private ArrayList tableList = new ArrayList();
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public ThisReport(String tableFile) throws Exception {
		super();
		this.tableFile = tableFile;
		
		//根据reportID读取表定义xml文件
		ReportConfig config = ReportConfig.getInstance();
		if(config == null)
			throw new Exception("no ReportConfig's instance. please call ReportConfig.newInstance first!");
		
		config.readReportTable(this);
		
	}

    void addTable(ThisTable thisTable) {
    	tableList.add(thisTable);
    }
    
    /**
     * 获取数据字符串
     * @return
     * @throws Exception 
     */
    public void addDataString(StringBuffer outInfo) throws Exception {
    	ThisTable thisTable = null;
    	
    	//列出所有的表
    	for(int i = 0; i < tableList.size(); ++i)
    	{
    		thisTable = (ThisTable) tableList.get(i);
    		
               DocumentFactory factory = DocumentFactory.getInstance();
        
                Element tb= factory.createElement("tb");
                Attribute name=  factory.createAttribute(tb, "name", thisTable.getTableName());
                Attribute title =factory.createAttribute(tb, "title", thisTable.getTableTitle());
                tb.add(name);  tb.add(title);
			//outInfo.append("<tb name=\"" + thisTable.getTableName() +  "\" title=\"" + thisTable.getTableTitle() + "\">");
              
			//列出当前表的所有行
			ArrayList rowList = thisTable.getRowList();
			
			//列出当前表的所有字段
			ArrayList fieldList = thisTable.getFieldList();
			ThisRow thisRow = null;
	    	String fieldName = null, fieldValue = null;

	    	for(int j = 0; j < rowList.size(); ++j) //插入?行记录
			{
				thisRow = (ThisRow) rowList.get(j);
                 Element tr= factory.createElement("tr");
				//outInfo.append("<tr>");
				for(int k = 0; k < fieldList.size(); ++k)
				{
					fieldName = (String) fieldList.get(k);
					fieldValue = thisRow.getFieldValue(fieldName);
                      Element rtd= factory.createElement("td");
                      rtd.setText(fieldValue);
                      tr.add(rtd);
					//outInfo.append("<td>" + fieldValue  + "</td>");
				}
                tb.add(tr);
				//outInfo.append("</tr>");
			}
            outInfo.append(tb.asXML());
    	}
    }
	//根据表名取出存放数据的表对象
	public ThisTable getReportTable(String tableName) throws Exception {
		ThisTable thisTable = null;
		int j = -1;
		for(int i = 0; i < tableList.size(); ++i) {
			thisTable = (ThisTable) tableList.get(i);
			if(thisTable.getTableName().equals(tableName)) 
			{
				j = i;
				break;
			}
		}
		if(j == -1)
			throw new Exception("table " + tableName + " not exists!");
		
		return thisTable;
	}

	public String getTableFile() {
		return tableFile;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
