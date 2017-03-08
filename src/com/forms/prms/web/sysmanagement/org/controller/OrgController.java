package com.forms.prms.web.sysmanagement.org.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Node;
import com.forms.prms.web.sysmanagement.org.domain.Org;
import com.forms.prms.web.sysmanagement.org.service.OrgService;

@Controller
@RequestMapping("/sysmanagement/org")
public class OrgController {
	private static final String FUNCTION = "sysmanagement/org/";

	@Autowired
	private OrgService orgService;

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@RequestMapping("org.do")
	public String preSysSoftwareMgr() {
		WebUtils.setRequestAttr("environment", "true");
		return FUNCTION + "org";
	}

	/**
	 * 查询机构列表
	 * 
	 * @return
	 */
	@RequestMapping("getOrgList.do")
	@ResponseBody
	public String getOrgList(BaseBean baseBean) {
		List<BaseBean> orgList = orgService.getOrgList(baseBean);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("orgList", ConvertBeanToNode.fromBeanListToNodeList(orgList, baseBean));
		return jsonObj.writeValueAsString();
	}

	/**
	 * 机构控件查询类表
	 * 
	 * @return
	 */
	@RequestMapping("getOrgListTag.do")
	@ResponseBody
	public String getOrgListTag(BaseBean baseBean) 
	throws Exception
	{
		baseBean.setLoginUserNodeId(WebHelp.getLoginUser().getDutyCode());

		AbstractJsonObject jsonObj = new SuccessJsonObject();
		
		long startTime = new Date().getTime();
		long endTime = 0;
		if( 1 == baseBean.getCachedType() )
		{
			try {
				DutyBeanToNode dbtn = new DutyBeanToNode( orgService.getCachedZtreeTag( new BaseBean(),baseBean.getRootNodeId()), baseBean);
				if( baseBean.isQuerySearch() )
				{
					jsonObj.put("searchValue", dbtn.getQueryValue() );
				}
				else
				{
					jsonObj.put("orgList", dbtn.getNodeListFromCached() );
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonObj.put("orgList", null );
			}
			
			//System.out.println(" 获取责任中心查询结果所要时间： " + (endTime - startTime) / 1000.00);
		}
		else
		{
			// 同时加载多个节点时
			if (!Tool.CHECK.isBlank(baseBean.getRootNodeId()) && baseBean.getRootNodeId().indexOf(",") != -1) {
				baseBean.setRootNodeList(new ArrayList<String>());
				String[] rootNodeIds = baseBean.getRootNodeId().split(",");
				for (int i = 0; i < rootNodeIds.length; i++) {
					baseBean.getRootNodeList().add(rootNodeIds[i]);
				}
			}
			List<BaseBean> orgList  = orgService.getOrgListTag(baseBean);
			jsonObj.put("orgList", ConvertBeanToNode.fromBeanListToNodeList(orgList, baseBean));
		}

		startTime = new Date().getTime();
		String jsonStr = jsonObj.writeValueAsString();
		endTime = new Date().getTime();
		//System.out.println(" 将查询节点封装成json对象所用时间： " + (endTime - startTime) / 1000.00);
		return jsonStr;
	}

	/**
	 * 查询机构详细信息
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("getOrg.do")
	@ResponseBody
	public String getOrg(Org org) {
		Org orgDetail = orgService.getOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("org", orgDetail);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 修改机构信息
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("updateOrg.do")
	@ResponseBody
	public String updateOrg(Org org) {
		boolean isSuc = orgService.updateOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 删除机构信息
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("deleteOrg.do")
	@ResponseBody
	public String deleteOrg(Org org) {
		boolean isSuc = orgService.deleteOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 新增机构信息
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("addOrg.do")
	@ResponseBody
	public String addOrg(Org org) {
		boolean isSuc = orgService.addOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 拖拽修改机构信息
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("dragUpdateOrg.do")
	@ResponseBody
	public String dragUpdateOrg(Node node) {
		boolean isSuc = orgService.dragUpdateOrg(node);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 禁用
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("disableOrg.do")
	@ResponseBody
	public String disableOrg(Org org) {
		boolean isSuc = orgService.isViladOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * 启用
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("enableOrg.do")
	@ResponseBody
	public String enableOrg(Org org) {
		boolean isSuc = orgService.isViladOrg(org);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("isSuc", isSuc);
		return jsonObj.writeValueAsString();
	}

	/**
	 * HQQ Add Test
	 * 
	 * @return
	 */
	@RequestMapping("orgCheckSelectDemo.do")
	public String orgCheckSelectDemo() {
		WebUtils.setRequestAttr("environment", "true");
		return FUNCTION + "orgCheckSelectDemo";
	}
	
	
	/**
     * 字符串的压缩
     * 
     * @param str
     *            待压缩的字符串
     * @return    返回压缩后的字符串
     * @throws IOException
     */
    public String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes());
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }     
}
