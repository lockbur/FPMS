package com.forms.prms.web.sysmanagement.user.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.dealexceldata.exceldealtool.service.ExcelDealService;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.domain.UpFileBean;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;
import com.forms.prms.web.sysmanagement.user.service.UserInfoService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/user")
public class UserInfoController {
	private static final String PREFIX = "sysmanagement/user/";

	@Autowired
	private UserInfoService service;
	@Autowired
	private ExportService exportService;
	@Autowired
	private UpFileManagerService upfilemanagerservice;
	@Autowired
	private DBFileOperUtil dBFileOperUtil;
	@Autowired
	private FmsMgrService fmsMgrService;
	@Autowired
	private ExcelDealService excelDealService;

	@RequestMapping("/preAdd.do")
	public String preAdd() {
		ReturnLinkUtils.addReturnLink("preAdd", "返回新增");

		String withoutRoleIds = "";
		String upDeptId = "";
		String fhFlag = "0";

		WebUtils.setRequestAttr("fhFlag", fhFlag);
		WebUtils.setRequestAttr("upDeptId", upDeptId);
		List<HashMap<String, String>> list = service.getRoleList(withoutRoleIds);
		if (("A0001").equals(WebHelp.getLoginUser().getOrg1Code())) {
			list = service.getRoleList(withoutRoleIds);
			WebUtils.setRequestAttr("roleListCount", list.size());
		} else {
			list = service.getAllRoleList(withoutRoleIds);
			WebUtils.setRequestAttr("roleListCount", list.size());
		}
		List<HashMap<String, String>> pageRoleList = new ArrayList<HashMap<String,String>>();//总行-总行角色
		List<HashMap<String, String>> pageRoleListOrg = new ArrayList<HashMap<String,String>>();//总行-全局角色
		HashMap<String, String> map = null;
		if (null != list && list.size()>0) {
			if (("A0001").equals(WebHelp.getLoginUser().getOrg1Code())) {
				for (int i = 0; i < list.size(); i++) {
					map = new HashMap<String, String>();
					map.put("roleId", list.get(i).get("roleId"));
					map.put("roleName", list.get(i).get("roleName"));
					map.put("roleLevel", list.get(i).get("roleLevel"));
					if ("02".equals(list.get(i).get("roleLevel"))) {
						pageRoleListOrg.add(map);//总行-全局角色
					} else {
						pageRoleList.add(map);//总行-总行角色
					}
				}
			} else {
				WebUtils.setRequestAttr("roleList", list);
			}
			
		}
//		WebUtils.setRequestAttr("roleList", list);
		WebUtils.setRequestAttr("pageRoleList", pageRoleList);//总行-总行角色
		WebUtils.setRequestAttr("pageRoleListOrg", pageRoleListOrg);//总行-全局角色
		WebUtils.setRequestAttr("roleListCount", list.size());
		if (ReturnLinkUtils.getLinkBean("userList") == null) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/user/list.do?VISIT_FUNC_ID=010302");
		} else {
			WebHelp.setLastPageLink("uri", "userList");
		}
		// 查看用户是否为超级管理员
		String isSuperAdmin = service.isSuperAdmin(WebHelp.getLoginUser().getUserId());
		if ("0".equals(isSuperAdmin)) {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg2Code());
		} else {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg1Code());
		}
		WebUtils.setRequestAttr("orgCode", WebHelp.getLoginUser().getOrg1Code());
		return PREFIX + "add";
	}

	@RequestMapping("/saveAdd.do")
	public String saveAdd(UserInfo userInfo) {
		try {
			 service.insert(userInfo);
			 WebUtils.getMessageManager().addInfoMessage("操作成功！");
			ReturnLinkUtils.setShowLink("preAdd");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			CommonLogger.error(e.getCause().getMessage());
			WebUtils.getMessageManager().addErrorMessage(e.getCause().getMessage());
			ReturnLinkUtils.setShowLink("preAdd");
			return ForwardPageUtils.getErrorPage();
		}
	}

	@RequestMapping("/list.do")
	@AddReturnLink(id = "list")
	public String select(UserInfo userInfo) throws Exception {
		ReturnLinkUtils.addReturnLink("userList", "返回列表");
		if (userInfo == null)
			userInfo = new UserInfo();

		if (Tool.CHECK.isEmpty(userInfo.getUserType())) {
			userInfo.setUserType("0");
		}
		if (Tool.CHECK.isEmpty(userInfo.getHaveRole())) {
			userInfo.setHaveRole("0");
		}
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		userInfo.setIsSuperAdmin(isSuperAdmin);
		// 如果用户登录一级行为A0001
		userInfo.setOrg1Code(org1Code);
		// 查看用户是否为超级管理员
		if ("0".equals(isSuperAdmin)) {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg2Code());
			userInfo.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		} else {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg1Code());
			userInfo.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}
		WebUtils.setRequestAttr("userList", service.getUserList(userInfo));
		WebUtils.setRequestAttr("selectInfo", userInfo);
		return PREFIX + "list";
	}
	/**
	 * 角色查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("selectRole.do")
	public String selectRole(UserInfo userInfo) {
		userInfo.setIsSuperAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		userInfo.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		List<UserInfo> roleList = service.selectRole(userInfo);
		WebUtils.setRequestAttr("roleList", roleList);
		WebUtils.setRequestAttr("selectInfo", userInfo);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return PREFIX + "roleList";
	}

	@RequestMapping("/preEdit.do")
	public String preEdit(@RequestParam("userId") String userId) {
		UserInfo userInfo = service.getUserById(userId);

		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		WebUtils.setRequestAttr("org1Code", org1Code);
		WebUtils.setRequestAttr("isSuperAdmin", WebHelp.getLoginUser().getIsSuperAdmin());
		if (userInfo == null) {
			CommonLogger.error("获取员工信息失败！");
			ReturnLinkUtils.setShowLink("userList");
			WebUtils.getMessageManager().addErrorMessage("获取员工信息失败，请联系管理员！");
			return ForwardPageUtils.getErrorPage();
		}

		String withoutRoleIds = "";
		String upDeptId = "";
		String fhFlag = "0";
		WebUtils.setRequestAttr("fhFlag", fhFlag);
		WebUtils.setRequestAttr("upDeptId", upDeptId);
		List<HashMap<String, String>> roleList = service.getRoleList(withoutRoleIds);
		if (userInfo.getOrg1Code().equals("A0001")) {
			roleList = service.getRoleList(withoutRoleIds);
//			WebUtils.setRequestAttr("roleList", roleList);
			WebUtils.setRequestAttr("roleListCount", roleList.size());
		} else {
			roleList = service.getAllRoleList(withoutRoleIds);
//			WebUtils.setRequestAttr("roleList", roleList);
			WebUtils.setRequestAttr("roleListCount", roleList.size());
		}
		System.out.println(userInfo.getDutyCode());
		String roleIds = service.getUserById(userId).getRoleId();
		String[] haveRoles;
		if (Tool.CHECK.isEmpty(roleIds)) {
			haveRoles = null;
		}else{
			haveRoles = roleIds.split(":");
		}
		try {
			if (Integer.parseInt(userInfo.getPwdFailTimes()) > Integer.parseInt(WebHelp.getSysPara("PWD_ERROR_TIMES"))) {
				WebUtils.setRequestAttr("T", true);
			}
		} catch (Exception e) {
			WebUtils.setRequestAttr("T", false);

		}
		WebUtils.setRequestAttr("userInfo", userInfo);
//		WebUtils.setRequestAttr("haveRoleList", haveRoles);
		List<HashMap<String, String>> pageRoleList = new ArrayList<HashMap<String,String>>();//总行-总行角色
		List<HashMap<String, String>> pageRoleListOrg = new ArrayList<HashMap<String,String>>();//总行-全局角色
		List<HashMap<String, String>> pageAllRoleList = new ArrayList<HashMap<String,String>>();//全局-所有角色
		HashMap<String, String> map = null;
		if (null != roleList && roleList.size()>0) {
			for (int i = 0; i < roleList.size(); i++) {
				map = new HashMap<String, String>();
				map.put("roleId", roleList.get(i).get("roleId"));
				map.put("roleName", roleList.get(i).get("roleName"));
				map.put("roleLevel", roleList.get(i).get("roleLevel"));
				if (null != haveRoles &&  haveRoles.length>0) {
					for (int j = 0; j < haveRoles.length; j++) {
						if (roleList.get(i).get("roleId").equals(haveRoles[j])) {
							map.put("isCheck", "1");
						}
					}
				}
				if (userInfo.getOrg1Code().equals("A0001")) {
					if ("02".equals(roleList.get(i).get("roleLevel"))) {
						pageRoleListOrg.add(map);//总行-全局角色
					} else {
						pageRoleList.add(map);//总行-总行角色
					}
				} else {
					pageAllRoleList.add(map);//全局-所有角色
				}
			}
		}
		
		WebUtils.setRequestAttr("pageRoleList", pageRoleList);//总行-总行角色
		WebUtils.setRequestAttr("pageRoleListOrg", pageRoleListOrg);//总行-全局角色
		WebUtils.setRequestAttr("pageAllRoleList", pageAllRoleList);//全局-所有角色
		
		
		WebHelp.setLastPageLink("uri", "userList");
		return PREFIX + "edit";
	}

	@RequestMapping("/saveEdit.do")
	public String saveEdit(UserInfo userInfo) throws Exception {
		service.update(userInfo);
		ReturnLinkUtils.setShowLink("userList");
		WebUtils.getMessageManager().addInfoMessage("操作成功!");
		return ForwardPageUtils.getSuccessPage();
	}

	@RequestMapping("/preImp.do")
	public String preImp() {
		ReturnLinkUtils.addReturnLink("preImp", "返回继续导入");

		return PREFIX + "import";
	}

	@RequestMapping("/saveImportInfo.do")
	public String saveImportInfo(UserInfo userInfo) {
		service.toImport(userInfo);
		ReturnLinkUtils.setShowLink("preImp");
		WebUtils.getMessageManager().addInfoMessage("导入成功!");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * ajax查找二级部门
	 * 
	 * @param requestMainInfo
	 * @return
	 */
	@RequestMapping("checkUserId.do")
	@ResponseBody
	public String checkUserId(String userId) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		UserInfo user = service.getUserById(userId);
		if (user == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}

	@RequestMapping("exportTemlate.do")
	public String template(HttpServletResponse response, String width) throws IOException {
		// 生成excel
		String strFileName = "InportUsers" + System.currentTimeMillis();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName + ".xls\"");

		HSSFWorkbook wbook = new HSSFWorkbook(); // 后缀 .xls

		Sheet sheet = wbook.createSheet("导入用户模板");
		HSSFCellStyle style = wbook.createCellStyle();

		// 设置边框:
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		// 设置居中:
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		// 设置字体:
		HSSFFont font = wbook.createFont();
		font.setFontName("仿宋_GB2312");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		Row headRow = sheet.createRow(0);
		// headRow.setRowStyle(style);
		// 导入的列
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("userId", "用户名");
		map.put("userName", "用户姓名");
		map.put("deptId", "部门ID");
		map.put("phoneNumber", "联系电话");
		map.put("notesId", "NOTES邮箱帐号");
		map.put("userType", "用户分类");
		map.put("roleId", "角色ID");

		String[] columns = { "userId", "userName", "deptId", "phoneNumber", "notesId", "userType", "roleId" };
		for (int i = 0; i < columns.length; i++) {
			sheet.setColumnWidth(i, Integer.parseInt(width) / 1024 * 5000);
			Cell cell = headRow.createCell(i);
			cell.setCellValue(map.get(columns[i]));
			cell.setCellStyle(style);
		}
		// 取部门
		List<Map<String, String>> checkDept = null;
		// if(WebHelp.getLoginUser().getRoleIds().indexOf(WebHelp.getSysPara("ROLE_R01"))==-1){
		// if(WebHelp.getLoginUser().getDeptLevel().equals("2")){
		// checkDept=this.deptService.checkDept(WebHelp.getLoginUser().getUpDeptId());
		// }else if(WebHelp.getLoginUser().getDeptLevel().equals("1")){
		// checkDept=this.deptService.checkDept(WebHelp.getLoginUser().getDeptId());
		// }
		// }else{
		// checkDept=this.deptService.checkDept("");
		// }
		Map<String, String> deptMap = null;
		if (checkDept != null && !checkDept.isEmpty()) {
			deptMap = new LinkedHashMap<String, String>();
			for (Map<String, String> checkDeptMap : checkDept) {
				deptMap.put(checkDeptMap.get("deptId"), checkDeptMap.get("deptName"));
			}
		}
		// 取所有的Role
		List<HashMap<String, String>> roleList = this.service.getRoleList(null);
		Map<String, String> allRoleMap = new TreeMap<String, String>();
		for (HashMap<String, String> meteMap : roleList) {
			String roleId = meteMap.get("roleId");
			String roleName = meteMap.get("roleName");
			if (!"R01".equals(roleId)) {
				allRoleMap.put(roleId, roleName);
			}
		}
		HSSFCellStyle style2 = wbook.createCellStyle();

		// 设置边框:
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		// 设置居中:
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

		// 设置字体:
		HSSFFont font2 = wbook.createFont();
		font2.setFontName("仿宋_GB2312");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 粗体显示
		font2.setFontHeightInPoints((short) 10);

		style2.setFont(font2);

		style.setWrapText(true);// 设置自动换行
		style2.setWrapText(true);// 设置自动换行
		// 说明页
		Sheet memoSheet = wbook.createSheet("导入说明");
		for (int n = 0; n < columns.length; n++) {
			memoSheet.setColumnWidth(n, Integer.parseInt(width) / 1024 * 6000);
			if (columns[n].equals("userId")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(0);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("1、用户名必填"));
			}
			if (columns[n].equals("userName")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(n);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("2、姓名可不填"));
			}
			if (columns[n].equals("deptId")) {
				if (checkDept == null || checkDept.isEmpty()) {
					Row memoRow = memoSheet.getRow(0);
					if (memoRow == null) {
						memoRow = memoSheet.createRow(0);
					}
					Cell memoCell = memoRow.createCell(0);
					memoCell.setCellStyle(style);
					memoCell.setCellValue(new HSSFRichTextString("3、当前用户没有查询部门的权限！"));
				} else {
					Row memoRow = memoSheet.getRow(0);
					if (memoRow == null) {
						memoRow = memoSheet.createRow(0);
					}
					Cell memoCell = memoRow.createCell(n);
					memoCell.setCellStyle(style);
					memoCell.setCellValue(new HSSFRichTextString("3、部门编号和对应的部门:(导入时用部门编号)"));
					int k = 1;

					for (Map.Entry<String, String> deptEntry : deptMap.entrySet()) {
						Row deptRow = memoSheet.getRow(k);
						if (deptRow == null) {
							deptRow = memoSheet.createRow(k);
						}
						memoCell = deptRow.createCell(n);
						memoCell.setCellStyle(style2);
						memoCell.setCellValue(new HSSFRichTextString(deptEntry.getKey() + ":" + deptEntry.getValue()));
						k++;
					}
				}

			}
			if (columns[n].equals("phoneNumber")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(n);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("4、联系电话暂可不填!"));
			}
			if (columns[n].equals("notesId")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(n);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("5、NOTES邮箱帐号暂可不填!"));
			}
			if (columns[n].equals("userType")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(n);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("6、用户分类选值：(导入时用数字)"));

				Row typeRow1 = memoSheet.getRow(1);
				if (typeRow1 == null) {
					typeRow1 = memoSheet.createRow(1);
				}
				Cell cell1 = typeRow1.createCell(n);
				cell1.setCellStyle(style2);
				cell1.setCellValue(new HSSFRichTextString(" 1  ：实名 "));

				Row typeRow2 = memoSheet.getRow(2);
				if (typeRow2 == null) {
					typeRow2 = memoSheet.createRow(2);
				}
				Cell cell2 = typeRow2.createCell(n);
				cell2.setCellStyle(style2);
				cell2.setCellValue(new HSSFRichTextString("2 ： 虚拟"));
			}
			if (columns[n].equals("roleId")) {
				Row memoRow = memoSheet.getRow(0);
				if (memoRow == null) {
					memoRow = memoSheet.createRow(0);
				}
				Cell memoCell = memoRow.createCell(n);
				memoCell.setCellStyle(style);
				memoCell.setCellValue(new HSSFRichTextString("7、角色Id和对应的名称:(导入时用的是角色ID，导入多个角色ID用英文逗号分开)"));
				int j = 1;
				for (Map.Entry<String, String> roleEntry : allRoleMap.entrySet()) {
					Row roleRow = memoSheet.getRow(j);
					if (roleRow == null) {
						roleRow = memoSheet.createRow(j);
					}
					memoCell = roleRow.createCell(n);
					memoCell.setCellStyle(style2);
					memoCell.setCellValue(new HSSFRichTextString(roleEntry.getKey() + ":" + roleEntry.getValue()));
					j++;
				}
			}
		}

		OutputStream outputStream = response.getOutputStream();
		wbook.write(outputStream);
		// 关闭流
		outputStream.flush();
		outputStream.close();
		return null;
	}

	/**
	 * 在线用户列表
	 * 
	 * @return
	 */
	@RequestMapping("onLinelist.do")
	public String onLinelist(UserInfo userInfo) {
		ReturnLinkUtils.addReturnLink("onLinelist", "返回列表");
		if (userInfo == null)
			userInfo = new UserInfo();

		if (Tool.CHECK.isEmpty(userInfo.getUserType())) {
			userInfo.setUserType("1");
		}
		// 查看用户是否为超级管理员
		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		if ("0".equals(isSuperAdmin)) {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg2Code());
			userInfo.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
			userInfo.setIsSuper("0");
		} else {
			WebUtils.setRequestAttr("org21Code", WebHelp.getLoginUser().getOrg1Code());
			userInfo.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
			userInfo.setIsSuper("1");
		}
		WebUtils.setRequestAttr("onLineUserList", service.getOnLineUserList(userInfo));
		WebUtils.setRequestAttr("userId", WebHelp.getLoginUser().getUserId());
		WebUtils.setRequestAttr("queryCondition", userInfo);
		return PREFIX + "onLineList";
	}

	@RequestMapping("forceQuitList.do")
	public String forceQuitList(UserInfo userInfo) {

		service.forceQuitList(userInfo);
		// WebUtils.getMessageManager().addInfoMessage("强退成功!");
		// ReturnLinkUtils.setShowLink("typeList");
		return onLinelist(userInfo);
	}

	@RequestMapping("forceQuit.do")
	public String forceQuit(UserInfo userInfo) {
		service.forceQuit(userInfo.getQuitUserId());
		// WebUtils.getMessageManager().addInfoMessage("强退成功!");
		// ReturnLinkUtils.setShowLink("typeList");
		return onLinelist(userInfo);
	}

	public String gotoLogin() {
		return "user/login";
	}

	/**
	 * 跳转到修改密码界面
	 * 
	 * @param sm
	 * @return
	 */
	@RequestMapping("preChange.do")
	public String preChange() {
		return PREFIX + "changePwd";
	}

	/**
	 * Ajax检验密码是否匹配
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("checkPassword.do")
	@ResponseBody
	public String checkPassword(String oldPassword) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean isEqual = service.checkPassword(oldPassword);
		if (isEqual == true) {
			jsonObject.put("isEqual", true);
		} else {
			jsonObject.put("isEqual", false);
		}

		return jsonObject.writeValueAsString();
	}

	/**
	 * Ajax检验是否超过设置的次数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("checkPasswordCount.do")
	@ResponseBody
	public String checkPasswordCount(HttpServletRequest request) {
		String password = request.getParameter("password");
		String userId = request.getParameter("userId");
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean isOut = service.checkPasswordCount(password, userId);
		if (isOut == true) {
			jsonObject.put("isOut", true);
		} else {
			jsonObject.put("isOut", false);
		}

		return jsonObject.writeValueAsString();
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("changePwd.do")
	@PreventDuplicateSubmit
	public String changePwd(UserInfo userInfo) {
		service.changePwd(userInfo);
		WebUtils.getMessageManager().addInfoMessage("修改密码成功!");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 01030402 重置密码
	 */
	@RequestMapping("/initPwd.do")
	public String initPwd(UserInfo userInfo) {
		service.initPwd(userInfo);
		WebUtils.getMessageManager().addInfoMessage("操作成功!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	@RequestMapping("/download.do")
	public String gotoMain(UpFileBean upFile) {
		// List<CtrlFileBean> list = userService.getDownloadList();
		// WebUtils.setRequestAttr("upFile", upFile);
		// WebUtils.setRequestAttr("upFileList", list);

		WebUtils.setRequestAttr("upFile", upFile);
		// 初始页面时用于存放查询条件
		List<UpFileBean> upFileManageList = upfilemanagerservice.list(upFile);
		// 将查询结果放至前端数据展示页面
		WebUtils.setRequestAttr("upFileList", upFileManageList);
		return PREFIX + "download";
	}

	/**
	 * @methodName gotoMain desc 用户解锁
	 * @param upFile
	 * @return
	 */
	@RequestMapping("/lock.do")
	public String lock(UserInfo userInfo) {
		service.lock(userInfo);
		WebUtils.getMessageManager().addInfoMessage("解锁成功！");
		ReturnLinkUtils.setShowLink("userList");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * @methodName pwdProtect desc 密保设置
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/pwdProtect.do")
	public String pwdProtect(UserInfo userInfo) {
		List<UserInfo> pwdProtectInfo = service.getUserPwdProtectInfo(userInfo);
		WebUtils.setRequestAttr("pwdProtectInfo", pwdProtectInfo);
		WebUtils.setRequestAttr("baseInfo", service.getUserById(WebHelp.getLoginUser().getUserId()));
		WebUtils.setRequestAttr("userInfo", userInfo);
		WebUtils.setRequestAttr("url", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/user/preEdit.do?VISIT_FUNC_ID=010304&userId=" + userInfo.getUserId());
		return PREFIX + "pwdProtect";
	}

	/**
	 * @methodName pwdProtectSet desc 密保设置提交
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/pwdProtectSubmit.do")
	public String pwdProtectSet(UserInfo userInfo) {
		if (userInfo.getFlag().equals("0")) {
			service.pwdProtectSet(userInfo);
		} else if (userInfo.getFlag().equals("1")) {
			service.pwdProtectSetEmail(userInfo);
		}
		WebUtils.getMessageManager().addInfoMessage("设置密保成功！");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 查询用户一级行
	 * 
	 * @param dutyCode
	 * @return
	 */
	@RequestMapping("/checkOrg1Code.do")
	@ResponseBody
	public String checkOrg1Code(String dutyCode) {
		String org1Code = service.checkOrg1Code(dutyCode);
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		jsonObject.put("org1Code", org1Code);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 用户信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/userExport.do")
	@ResponseBody
	public String userExport(UserInfo userInfo){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = service.userExport(userInfo);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 用户角色信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/export.do")
	public String userRoleRlnExport(UserInfo userInfo){
		return PREFIX + "exportUserRole";
	}
	
	/**
	 * 用户角色信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportUserRoleRln.do")
	@ResponseBody
	public String userRoleExport(UserInfo userInfo) throws Exception{
		
		
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = service.userRoleExport(userInfo);
			if(taskId == null || "".equals(taskId)){				
				jsonObject.put("pass", false);				
			}else{
				jsonObject.put("pass", true);
			}
		}catch(Exception e){
			jsonObject.put("pass", false);
			throw e;
		}
		
		return jsonObject.writeValueAsString();
	}
	
	@RequestMapping("/userRoleRlnList.do")
	public String userRoleRlnList(UserInfo userInfo){
		List<ImportBean> list=service.userRoleRlnList(userInfo);
		WebUtils.setRequestAttr("list",list);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		ReturnLinkUtils.addReturnLink("userRoleRlnList", "用户职责信息导入列表查询");
		return PREFIX + "userRoleRlnList";
	}

	/**
	 * 新增导入页面
	 */
	@RequestMapping("/preAddUserRoleRln.do")
	public String shAdd(ImportBean bean){
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/user/userRoleRlnList.do?VISIT_FUNC_ID=01030302");
		
		return PREFIX+"addUserRoleRln";
	}
	
	
	@RequestMapping("/addUserRoleRln.do")
	public String addUserRoleRln(ImportBean bean){
		try {
			service.addExcel(bean);
			WebUtils.getMessageManager().addInfoMessage("导入excel已经新增，模板Excel导入线程进入排队！待校验通过后可提交生效。");
			
			return ForwardPageUtils.getReturnUrlString("导入列表",true,new String[] { "userRoleRlnList"});
		} catch (Exception e) {
			e.printStackTrace();
			return ForwardPageUtils.getReturnUrlString("导入列表",false,new String[] { "userRoleRlnList"});
		}
	}
	
	
	/**
	 * 用户数据导入明细 0103030205
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/getUserRoleRln.do")
	public String getUserRoleRln(ImportBean bean){
		ImportBean eb=service.getDetail(bean);
		List<ImportBean> err=service.getErrData(eb);
		if (null != eb ) {
			//查询log信息
			CommonExcelDealBean iBean= excelDealService.queryTaskByBatchNo(bean.getBatchNo());
			String logFilePath = "";
			String badFilePath = "";
			if (!Tool.CHECK.isEmpty(iBean)) {
				//从任务表中获取日志文件的路径,路径+configId
				logFilePath = iBean.getSourceFpath()+iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".log";
			    badFilePath = iBean.getSourceFpath()+iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".bad";
			}
			
		    if(new File(logFilePath).exists()){
		    	WebUtils.setRequestAttr("logFilePath", logFilePath);
		    	WebUtils.setRequestAttr("logFile", iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".log");
		    }
		    if(new File(badFilePath).exists()){
		    	WebUtils.setRequestAttr("badFilePath", badFilePath);
		    	WebUtils.setRequestAttr("badFile", iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".bad");
		    }
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/user/userRoleRlnList.do?VISIT_FUNC_ID=01030302");
		}
		WebUtils.setRequestAttr("bean", eb);
		WebUtils.setRequestAttr("err",err);
		return PREFIX + "detail";
	}
	/**
	 * 用户数据导入提交 0103030204
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/submitUserRoleRln.do")
	public String submitUserRoleRln(ImportBean eb){
		eb.setBatchNo(WebUtils.getParameter("batchNo"));
		try {
			service.submitUserRoleRln(eb.getBatchNo(),"IMPORT",WebHelp.getLoginUser().getUserId());
			return ForwardPageUtils.getReturnUrlString("数据提交",true,new String[] { "userRoleRlnList"});
		} catch (Exception e) {
			CommonLogger.error("数据导入导出提交失败,批次号是:"+eb.getBatchNo());
			e.printStackTrace();
			return ForwardPageUtils.getReturnUrlString("数据提交",true,new String[] { "userRoleRlnList"});
		}
	}
	/**
	 * 用户数据导入删除 0103030203
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/deleteUserRoleRln.do")
	public String deleteUserRoleRln(ImportBean eb){
		eb.setBatchNo(WebUtils.getParameter("batchNo"));
		service.deleteUserRoleRln(eb);
		return ForwardPageUtils.getReturnUrlString("数据删除",true,new String[] {"userRoleRlnList"});
	}
	
	/**
	 * @methodName checkDownFileExist desc 文件导出前进行校验，文件是否存在
	 * @param path
	 * @return
	 */
	@RequestMapping("checkDownFileExist.do")
	@ResponseBody
	public String checkDownFileExist(String batchNo) {
		AbstractJsonObject json = new SuccessJsonObject();
		boolean checkFlag = false; // 校验结果标识
		String checkMsg = ""; // 校验结果MSG(当校验结果为false时，该对象才会有值)
		try {
			String path = service.getPath(batchNo).getPath();
			File downFile = new File(path);
			if ((!downFile.exists()) || !(downFile.isFile())) {
				// 不存在文件 将数据库中的文件下载到服务器中
				dBFileOperUtil.findFileFromDB(path);
				checkMsg = "当前服务器不存在这个文件，现正在从数据库中下载到本地,请稍后再试";
				checkFlag = false;
			} else {
				checkFlag = true;
				CommonLogger.debug("【文件存在性检测】：存在   "
						+ path);
			}
		} catch (Exception e) {
			checkMsg = "需要下载的文件在当前服务器和数据库不存在，请检查！";
		}
		json.put("batchNo", batchNo); // 用于Ajax检测后的下载操作中参数传输
		json.put("checkFlag", checkFlag);
		json.put("checkMsg", checkMsg);
		return json.writeValueAsString();

	}
	
	/**
	 * @methodName upFileDownload desc 导入数据导出
	 * @param request
	 * @param response
	 * @param batchNo
	 * @return
	 */

	@RequestMapping("/exportDownFile.do")
	public String exportDownFile(HttpServletRequest request,
			HttpServletResponse response, String batchNo) {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		String path = service.getPath(batchNo).getPath();
		String fileName = service.getPath(batchNo).getSourceFilename();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			fmsMgrService.downloadFile(request, response, path);
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			// 忽略浏览器客户端刷新关闭的异常的信息打印
			if (!"ClientAbortException".equals(simplename)) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 校验是否存在待提交的数据
	 * @param 
	 * @return
	 */
	@RequestMapping("ajaxDataExist.do")
	@ResponseBody
	public String ajaxDataExist(String batchNo) {
		AbstractJsonObject json = new SuccessJsonObject();
		Map<String, Object> map = service.ajaxDataExist();
		json.put("resultValue", map);
		return json.writeValueAsString();

	}

}
