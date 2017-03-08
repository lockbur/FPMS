package com.forms.prms.web.sysmanagement.projectcrinfo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.constantValues.FileValues;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.sysmanagement.projectcrinfo.domain.ProjectcrInfo;
import com.forms.prms.web.sysmanagement.projectcrinfo.service.ProjectcrInfoService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 * @author YiXiaoYan 
 * 
 * 电子审批信息导入：<br/>
 * 导入文件必须为txt文本，编码为ANSI。文本格式为：缩位码,审批编号,日期,总数量,总金额。<br/>
 * 可在导入页面点击"导入样式下载"超链接，下载模板仅供参考，导入的内容不能包含表头"缩位码,……"等字样。<br/>
 * 缩位码为空的记录会跳过不进行保存。<br/>
 * 导入信息时会和数据库中的缩位码进行比较，如果已经存在则将审批编号等信息更新为文本中的内容，如果不存
 * 在则新增一条审批记录。
 * 
 * 
 * 电子审批信息列表查询：<br/>
 * 查询条件有：缩位码、审批编号、日期范围，其中审批编号提供模糊查询。
 * 该列表页面为模态窗口展示，列表信息有：缩位码，审批编号，日期，审批数量，已执行数量，审批金额，已执行金额。
 * 单击列表可选中该条记录，点击页面下方的提交按钮将关闭页面，并将选中的记录封装为JS对象返回，对象属性同JavaBean。
 * 双击列表功能同单击并提交一致。
 * 
 * 
 */
@Controller
@RequestMapping("/sysmanagement/projectcrinfo")
public class ProjectcrInfoController
{
	private static final String PREFIX = "sysmanagement/projectcrinfo/";
	
	@Autowired
	private ProjectcrInfoService service;
	
	/**
	 * 电子审批信息导入页面 --0110
	 * @return
	 */
	@RequestMapping("/preImport.do")
	public String preImport()
	{
		ReturnLinkUtils.addReturnLink("preImport", "返回继续导入");
		return PREFIX + "import";
	}
	
	/**
	 * 电子审批信息列表页面 --011003
	 * @param projectcr
	 * @return
	 */
	@RequestMapping("/list.do")
	public String list(ProjectcrInfo projectcr)
	{
		if(projectcr == null){
			projectcr = new ProjectcrInfo();
		}
		WebUtils.setRequestAttr("ProjectcrInfo", projectcr);
		WebUtils.setRequestAttr("ProjectcrInfoList", service.selectProjectcrInfoList(projectcr));
        return PREFIX + "list";
	}
	
	/**
	 * 电子审批信息导入 --011002
	 * @param projectcr
	 * @return
	 */
	@RequestMapping("/import.do")
	public String importFile(ProjectcrInfo projectcr)
	{
		ReturnLinkUtils.setShowLink("preImport");
		try {
			service.importProjectcrInfos(projectcr);
			WebUtils.getMessageManager().addInfoMessage("导入成功！");
	        return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			WebUtils.getMessageManager().addErrorMessage("导入失败!"+e.getCause().getMessage());
            return ForwardPageUtils.getErrorPage();
		}
	
	}
	
	/**
	 * 电子审批信息导入模板文件下载
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txtDownload.do")
	public String txtDownload(HttpServletResponse response) throws Exception
	{
		String downloadContentTypeCharset = SystemParamManage.getInstance().getParaValue("downloadContentTypeCharset");
		String downloadHeaderCharset = SystemParamManage.getInstance().getParaValue("downloadHeaderCharset");
		String fileName = "projectcrImportTemplate.txt";
		response.setContentType("application/x-msdownload;charset="+downloadContentTypeCharset);
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("导入标准样式.txt",downloadHeaderCharset));
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try
		{	
			File file = FileValues.getTemplatesFle(fileName);
			if(!file.exists())
			{
				throw new Exception("找不到指定文件："+fileName);
			}
			// 开始下载
			fis = new FileInputStream(file);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[8192];
			int data = 0;
			while ((data = fis.read(b)) != -1)
			{
				bos.write(b, 0, data);
			}
			// 刷新流
			bos.flush();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (bos != null)
				bos.close();
			if (fis != null)
				fis.close();
		}
		return null;
	}
	
}
