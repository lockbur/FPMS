
package com.forms.prms.web.report.domain;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : Forms Platform <br>
 * JDK version used:jdk1.4.2_06 <br>
 * Description : 用于报表定义文件的解析 <br>
 * Comments Name : ReportConfig <br>
 * author ：ronald zhang <br>
 * date ：2006-11-21 <br>
 * Version : 1.00 <br>
 * Modification history : Modified By Why & What is modified Date 2004-11-22 1.
 * 2006-5-10 chenxiaolong add unauthorized forward
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ReportConfig
        extends Object
{
    private static ReportConfig instance;

    // 配置文件路径
    private String              fileName       = null;

    Class                       cls            = null;

    private HashMap             reportItemList = new HashMap();

    public static ReportConfig newInstance(String fileName)
    {
        if (instance == null)
        {
            instance = new ReportConfig(fileName);
        }
        return instance;
    }

    public static ReportConfig getInstance()
    {
        return instance;
    }

    private ReportConfig (String fileName)
    {
        this.fileName = fileName;
        if (!fileName.equals(""))
        {
            try
            {
                readXMLFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void readXMLFile() throws Exception
    {

        File file = new File(this.fileName);
        String curPath = file.getParent();
        if (curPath.charAt(curPath.length() - 1) != '/'
                && curPath.charAt(curPath.length() - 1) != '\\')
            curPath = curPath + "/";

        SAXReader reader = new SAXReader();
        FileInputStream fis = new FileInputStream(this.fileName);
        try
        {
            org.dom4j.Document document = reader.read(fis);

            // 读取report-list中的所有report参数
            // 取所有report列表
            Node listNode = document.selectSingleNode("//report-list");
            if (listNode != null)
            {
                List reportList = listNode.selectNodes("//report");
                if (reportList != null)
                {
                    Node reportNode = null;
                    Node propNode = null;
                    ReportItem reportItem = null;

                    for (int i = 0; i < reportList.size(); ++i)
                    {
                        reportItem = new ReportItem();
                        reportNode = (Node) reportList.get(i);

                        propNode = reportNode.selectSingleNode("report-id");
                        reportItem.setReportID(propNode.getText());

                        propNode = reportNode.selectSingleNode("report-name");
                        reportItem.setReportName(propNode.getText());

                        propNode = reportNode.selectSingleNode("report-file");
                        reportItem.setReportFile(propNode.getText());

                        propNode = reportNode.selectSingleNode("table-file");
                        reportItem.setTableFile(propNode.getText());

                        propNode = reportNode.selectSingleNode("data-class");
                        reportItem.setDataClass(propNode.getText());

                        reportItemList
                                .put(reportItem.getReportID(), reportItem);
                    }
                }
            }
        }
        finally
        {
            if (fis != null)
                fis.close();
        }
    }

    public ReportItem getReportItem(String reportID)
    {
        Object ai = reportItemList.get(reportID);
        if (ai == null)
            return null;
        else
            return (ReportItem) ai;
    }

    // 根据thisReport中的reportID读取对应的表定义文件，并将表及表中的字段全部放入thisReport
    public void readReportTable(ThisReport thisReport) throws Exception
    {
        File file = new File(this.fileName);
        String curPath = file.getParent();
        if (curPath.charAt(curPath.length() - 1) != '/'
                && curPath.charAt(curPath.length() - 1) != '\\')
            curPath = curPath + "/";

        SAXReader reader = new SAXReader();
        FileInputStream fis = new FileInputStream(thisReport.getTableFile());
        try
        {
            org.dom4j.Document document = reader.read(fis);

            // 读取所有tb节点
            List tbList = document.selectNodes("//tb");
            if (tbList != null)
            {
                Node tbNode = null;

                for (int i = 0; i < tbList.size(); ++i)
                {
                    tbNode = (Node) tbList.get(i);
                    // table name
                    String tableName = tbNode.valueOf("@name");
                    String tableTitle = tbNode.valueOf("@title");
                    ThisTable thisTable = new ThisTable(tableName);
                    thisTable.setTableTitle(tableTitle);

                    // 读取所有field节点
                    Node fdNode = null;
                    List fdList = tbNode.selectNodes("fd");
                    for (int j = 0; j < fdList.size(); ++j)
                    {
                        fdNode = (Node) fdList.get(j);
                        // 将字段名加入到字段数组中
                        thisTable.addField(fdNode.valueOf("@name"));

                    }

                    // 将当前表加入到当前报表中
                    thisReport.addTable(thisTable);

                }
            }
        }
        finally
        {
            if (fis != null)
                fis.close();
        }

    }
}