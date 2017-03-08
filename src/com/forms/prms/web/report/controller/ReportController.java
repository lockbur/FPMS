package com.forms.prms.web.report.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.report.domain.ReportConfig;
import com.forms.prms.web.report.domain.ReportItem;
import com.forms.prms.web.report.domain.ThisReport;
import com.forms.prms.web.report.service.ReportInterface;

@Controller
@RequestMapping("/common/report/")
public class ReportController {
	private static String fileDir = "";
	
	
	public final void init(HttpServletRequest request) throws ServletException
    {
        // get real path
        fileDir = request.getSession().getServletContext().getRealPath("/");
        if (fileDir.charAt(fileDir.length() - 1) != '/'
                && fileDir.charAt(fileDir.length() - 1) != '/') fileDir = fileDir
                + "/";

        // init, read config
        String fileName = fileDir + "WEB-INF/report-config.xml";
        ReportConfig.newInstance(fileName);
    }

	@RequestMapping("process.do")
    public void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
		init(request);
		
        request.setCharacterEncoding("GBK"); // 设置输入编码格式

        String rptID = request.getParameter("rptid");
        String cmdID = request.getParameter("cmdid");

        // 获得报表项定义
        ReportItem rptItem = ReportConfig.getInstance().getReportItem(rptID);
        if (rptItem == null)
        {
            PrintWriter out = response.getWriter();
            out.println("[ Cannot get the ReportItem(" + rptID + ") ]");
            out.close();
            return;
        }

        // 取报表定义文件
        if ("report".equals(cmdID))
        {
            response.setContentType("application/x-download;charset=GBK"); // 设置输出编码格式
            response.sendRedirect(request.getContextPath()
                    + rptItem.getReportFile());
        } else
        // 取表定义文件
        if ("table".equals(cmdID))
        {
            response.setContentType("application/x-download;charset=GBK"); // 设置输出编码格式
            String tableFile = request.getContextPath()
                    + rptItem.getTableFile();
            response.sendRedirect(tableFile);
        } else
        // 取表数据文件
        if ("data".equals(cmdID))
        {
            response.setContentType("application/x-download;charset=GBK"); // 设置输出编码格式
            PrintWriter out = response.getWriter();
            StringBuffer outInfo = new StringBuffer();

            try
            {
                response.addHeader("Content-Disposition",
                    "attachment;filename=data.xml");

                // 获取当前报表对应的prepare object，该object实现了IReportData接口
                Class cls = Class.forName(rptItem.getDataClass());
                ReportInterface reportData = null;
                ThisReport thisReport = new ThisReport(fileDir
                        + rptItem.getTableFile());
                thisReport.setRequest(request);
                thisReport.setUser(WebHelp.getLoginUser());

                if (cls == null) throw new Exception(
                        "cannot load prepare data class for "
                                + rptItem.getDataClass());
                else
                    reportData = (ReportInterface) cls.newInstance();

                outInfo.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
                outInfo.append("<root>");

                // 准备当前报表所需的数据
                reportData.prepare(thisReport);

                // 将当前报表串行化后的字符串加入outInfo中
                thisReport.addDataString(outInfo);

                outInfo.append("</root>");

                for (int i = 0; i < outInfo.length(); ++i)
                    out.write(outInfo.charAt(i));
                out.close();
            } catch (Exception e)
            {
                out.print("error:" + e.getMessage());
                out.close();
            }
        } else
        {
            PrintWriter out = response.getWriter();
            out.println("ReportServlet no support for your url.");
            out.close();
        }

    }
}
