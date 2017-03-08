package com.forms.prms.web.sysmanagement.projectcrinfo.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.sysmanagement.projectcrinfo.dao.IProjectcrInfoDAO;
import com.forms.prms.web.sysmanagement.projectcrinfo.domain.ProjectcrInfo;

/**
 * @author : YiXiaoYan <br>
 * @since : 2015-01-25 <br>
 * 电子审批信息service
 */
@Service
public class ProjectcrInfoService {
	@Autowired
	private IProjectcrInfoDAO dao;

	/**
	 * 查询电子审批信息列表
	 * @param projectcr
	 * @return
	 */
	public List<ProjectcrInfo> selectProjectcrInfoList(ProjectcrInfo projectcr) {
		//弹出窗展示，页面记录设置为10条
		PageUtils.setPageSize(10);
		return PageUtils.getPageDao(dao).selectProjectcrInfoList(projectcr);
	}
	
	/**
	 * 导入电子审批信息
	 * @param projectcr
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public String importProjectcrInfos(ProjectcrInfo projectcr) throws Exception {
		String res = null;
		MultipartFile file = projectcr.getProjectcrFile();
		BufferedReader reader = null;
		String readFileCharset = SystemParamManage.getInstance().getParaValue("readFileCharset");
		
		int line = 0;
		String content = null;
		String[] contentArr = null;
		ProjectcrInfo projectcrInfo = null;
		try {
			BufferedInputStream bin=new BufferedInputStream(file.getInputStream());
			int p = (bin.read() << 8) + bin.read(); 
			String code="";
			switch (p) {    
            case 0xefbb:    
                code = "UTF-8";    
                break;    
            case 0xfffe:    
                code = "Unicode";    
                break;    
            case 0xfeff:    
                code = "UTF-16BE";    
                break;    
            default:    
                code = "GBK";
			}
			
			
			reader = new BufferedReader(new InputStreamReader(file.getInputStream(), readFileCharset));		
			
			while ((content = reader.readLine()) != null) {
				line++;
				if (line == 1 ) {
					//说明是第一行
					continue;
				}
				if ("".equals(content.trim())) {
					continue;
				}
				
				contentArr = content.trim().replace("\"", "").split(",");
				if (contentArr == null || contentArr.length != 5) {
					CommonLogger.error("第" + line + "行数据格式错误！");
					throw new Exception("第" + line + "行数据格式错误！");
				}
				if (StringUtils.isEmpty(contentArr[0])) {
					CommonLogger.error("第" + line + "行数据【缩位码】为空！");
					throw new Exception("第" + line + "行数据【缩位码】为空！");
				}
				String projNum = contentArr[3];
				String projAmt = contentArr[4];
				
				if (!getRegEx(projNum)) {
					CommonLogger.error("第" + line + "行【总数量】的格式错误，必须是数字");
					throw new Exception("第" + line + "行【总数量】的格式错误，必须是数字");
				}
				if (!getRegEx(projAmt)) {
					CommonLogger.error("第" + line + "行【总金额】的格式错误，必须是数字");
					throw new Exception("第" + line + "行【总金额】的格式错误，必须是数字");
				}
				projectcrInfo = new ProjectcrInfo();
//				if (code.equals("UTF-8")||code.equals("UTF-16BE")) {
//					projectcrInfo.setAbCde(contentArr[0].substring(1));
//				}else {
//					projectcrInfo.setAbCde(contentArr[0]);
//				}
				projectcrInfo.setAbCde(contentArr[0]);
				projectcrInfo.setProjCrId(contentArr[1]);
				projectcrInfo.setCreateDate(contentArr[2]);
				projectcrInfo.setProjCrNum(new BigDecimal(StringUtils.isEmpty(contentArr[3]) ? "0" : contentArr[3]));
				projectcrInfo.setProjCrAmt(new BigDecimal(StringUtils.isEmpty(contentArr[4]) ? "0" : contentArr[4]));

				dao.saveProjectcrInfo(projectcrInfo);
			}
		} catch (Exception e) {
			CommonLogger.error(e.getMessage());
			res = e.getMessage();
			throw new Exception(res);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					CommonLogger.error(e1.getMessage());
				}
			}
		}
		return res;
	}
	public boolean getRegEx(String string) {
		String regEx ="^[0-9]+(.)?([0-9])*$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(string);
		return m.find();
	}
	public static void main(String[] args) {
//		String regEx ="^[0-9]+(.)?([0-9])*$";
//		String string = "36750.0.3";
//		Pattern pattern = Pattern.compile(regEx);
//		Matcher m = pattern.matcher(string);
//		if (!m.find()) {
//			System.out.println("00000");
//		}
	}
}
