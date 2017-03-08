package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntRelPayInfo;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntVerifyBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayAdvanceBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.UploadDataControlInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.service.DataMigrateService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/reportmgr/dataMgr/datamigrate")
public class DataMigrateController {

	private final String BASE_URL = "reportmgr/dataMgr/datamigrate/";
	
	@Autowired
	private DataMigrateService dmService;				//存量业务数据Service

	@Autowired
	private UpFileManagerService upFileMgrService;		//上传文件管理Service
	
	/**
	 * @methodName 	dataImportPage
	 * 		描述：	进入存量业务数据导入的主页面
	 * 		FuncId:	0602
	 * @return
	 */
	@RequestMapping("/todataimportpage.do")
	@AddReturnLink(id="dataImportPage" , displayValue="回到存量业务数据主页")
	public String dataImportPage(){
		String org1Name = WebHelp.getLoginUser().getOrg1Name();
		//1.查找当前登录用户所在机构的导入udcBean对象
		UploadDataControlInfoBean udcBean = dmService.mGetUploadControlBean();
		//2.将需要展示的信息返回到前端JSP做展示
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());		//currentUri：当前访问的uri(无根路径),用于从当前页面跳转到[导入导出模块]时，"返回"按钮的前一页链接		
		WebUtils.setRequestAttr("org1Name", org1Name);
		WebUtils.setRequestAttr("udcBean", udcBean);
		return BASE_URL + "dataimportpage";
	}
	
	/**
	 * @methodName 	downloadDMTemp
	 * 		描述：	存量业务数据主页面，点击上方的【下载】按钮进行数据迁移(合同/付款)模板的下载
	 * 				根据request取得用户需要下载的Excel模板，进行模板的下载
	 * 		FuncId:	060200
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/downloadDMTemp.do")
	public String downloadDMTemp(HttpServletRequest request , HttpServletResponse response ){
		//执行模板下载操作；获取模板下载操作处理返回结果；决定跳转的页面以及页面中的展示信息
		Map<String ,Object> resultMap = dmService.downloadTemp(request , response);
		String operResult = (String)resultMap.get("errorCode");		//errorCode	(00：下载操作正常结束;	01：无配置文件;	02：下载发生异常)			
		if("01".equals(operResult)){
			WebUtils.getMessageManager().addErrorMessage("无该模板配置文件，请联系管理员检查该Excel模板文件。");
			ReturnLinkUtils.setShowLink("dataImportPage");
			return ForwardPageUtils.getErrorPage();
		}else if("02".equals(operResult)){
			WebUtils.getMessageManager().addErrorMessage("模板文件下载时发生异常(Exception)，请联系管理员！");
			ReturnLinkUtils.setShowLink("dataImportPage");
			return ForwardPageUtils.getErrorPage();
		}else{
			return null;
		}
	}
	
	/**
	 * @methodName toUploadDataPage
	 * 		描述：	存量业务数据主页面，点击"导入"按钮操作，跳转至数据导入界面
	 * 		FuncId:	060202
	 * @return
	 */
	@RequestMapping("/toUploadDataPage.do")
	public String toUploadDataPage(int testType){
		if(testType == 0){
			//【正式】系统原意进行存量业务数据的导入界面
			return BASE_URL + "uploadFileData";
		}else{
			//【测试用】用于测试文件数据上传导入，10-12测试于SQLLDR导入
			return BASE_URL + "importExcelDataTest";
		}
		
	}
	
	/**
	 * @methodName checkUpFileSizeByAjax
	 * 		描述：	Ajax校验上传文件的大小(IE浏览器时使用该方法校验)
	 * 		FuncId:	060209
	 * @param request：			用于取值前端传递文件路径的数组型参数filePath
	 * @param allowFileSize：	允许上传的大小
	 * @return
	 */
	@RequestMapping("/checkUpFileSizeByAjax.do")
	@ResponseBody
	//前端JS传过来的数组参数，需要使用request来接收，在后台中使用[request.getParameterValues("filePath[]")]的方式取值
	public String checkUpFileSizeByAjax(HttpServletRequest request , int allowFileSize){
		if(allowFileSize <= 0 || Tool.CHECK.isEmpty(allowFileSize)){
			allowFileSize = 50;													//如果该传递参数为空，则使用默认的允许上传大小参数(前端调用时传过来)
		}
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String[] filePath = request.getParameterValues("filePath[]");			//取得前端传递的数组类型参数
		//调用dmService.getFileSizeCheckResult()校验上传文件的大小是否符合要求，并返回校验结果
		Map<String , Object> resultMap = dmService.getFileSizeCheckResult(filePath , allowFileSize);
		jsonObject.put("fileSizes", (String[])resultMap.get("fileSizes"));
		jsonObject.put("validateResult", ((Boolean)resultMap.get("validateResult")).booleanValue());
		jsonObject.put("errMsg", (String)resultMap.get("errMsg"));
		return jsonObject.writeValueAsString();
	}
	
	//【HQQ-TEST】:用于测试导入Excel数据
//	@RequestMapping("/importDataToDBTB.do")
	public String forTestImportDataToDBTB( UploadDataControlInfoBean uploadFiles ) throws IOException{
//		MultipartFile mFile =  uploadFiles.getImpFile1();
//		System.out.println("Into The Controller");
		String file1Path = uploadFiles.getImpFile1Path();
		String file1OriginalName = uploadFiles.getImpFile1OriginalName();

//		//2.Copy文件到本地路径
//		List<MultipartFile> impFiles = new ArrayList<MultipartFile>();
//		impFiles.add(uploadFiles.getImpFile1());
//		String[] serverFiles = new String[1];
//		String uploadTempUrl = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL");
//		//判断上传缓存路径是否存在，不存在则首先创建该路径
//		CommonFileUtils.createFileFolder(uploadTempUrl,null);
//		serverFiles[0] = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/"+DateUtil.getDateTimeStrNo()+"_"+impFiles.get(0).getOriginalFilename();
//		dmService.copyFile(impFiles , serverFiles);						//可能发生异常，将该异常抛出Controller层进行处理
//		System.out.println("文件保存至服务器的地址："+serverFiles[0]);
		
		//3.调用Excel导入组件
		dmService.importToZZZSYSROLE(file1Path);
		
		//4.成功调用，返回页面
		WebUtils.getMessageManager().addInfoMessage("【测试信息】：已成功启用Excel数据导入任务");
		ReturnLinkUtils.setShowLink(new String[]{"dataImportPage"});
		return ForwardPageUtils.getSuccessPage();
	}
	
	
	/**
	 * @methodName 	importDataToDBTB
	 * 		描述：	数据迁移操作，调用Excel导入导出组件进行"数据导入"功能操作(调用Excel导入组件，进行Excel数据往DB的导入)
	 * 		FuncId:	060201
	 * @param  uploadFiles(包含上传文件的信息：路径等...)
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/importDataToDBTB.do")
	public String importDataToDBTB( UploadDataControlInfoBean uploadFiles ){
		//1.拿到上传控件保存上传文件至服务器中的地址(采用平台控件进行文件上传)
		String file1Path = uploadFiles.getImpFile1Path();
		String file1OriginalName = uploadFiles.getImpFile1OriginalName();					//合同数据Excel模板的源文件名称
		String file2Path = uploadFiles.getImpFile2Path();
		String file2OriginalName = uploadFiles.getImpFile2OriginalName();					//付款数据Excel模板的源文件名称
		//2.创建导入批次号，调用导入Excel数据任务(参数为上传保存的Excel文件路径+批次号)
		String impBatchNo = dmService.createImportBatchSeq();								//创建并返回导入批次序列号
		String cntTaskId  = dmService.importCntDataToCNTDATA(file1Path , impBatchNo);		//调用导入合同Excel数据方法
		String payTaskId  = dmService.importPayDataToPAYDATA(file2Path , impBatchNo);		//调用导入付款Excel数据方法
		//3.检验导入模块key值，判断是否导入了标准的模板，已决定是否需要解析Excel数据
		if( null == cntTaskId || null == payTaskId){
			if(null == cntTaskId){
				WebUtils.getMessageManager().addInfoMessage("CNT合同数据模板校验为非系统标准模板，将不执行导入操作，请重新导入系统标准的合同数据模板");
			}else{	
				//null == payTaskId
				WebUtils.getMessageManager().addInfoMessage("PAY付款数据模板校验为非系统标准模板，将不执行导入操作，请重新导入系统标准的付款数据模板");
			}
			ReturnLinkUtils.setShowLink(new String[]{"dataImportPage"});
			return ForwardPageUtils.getErrorPage();
		}
		//4.插入导入数据记录至表UPLOAD_DATA_CONTROL_INFO(参数：1.导入批次号  2.导入操作者  3.导入者的一级行号  4.合同导入任务Id 5.付款导入任务Id)
		dmService.addUpDataControlInfo(impBatchNo , WebHelp.getLoginUser().getUserId() , WebHelp.getLoginUser().getOrg1Code() , cntTaskId , payTaskId);
		//5.返回页面展示信息以及正确页面
		WebUtils.getMessageManager().addInfoMessage("已成功提交数据迁移导入任务！当前批次号为["+impBatchNo+"],</br>启动的导入任务ID为："+cntTaskId+","+payTaskId+";");
		ReturnLinkUtils.setShowLink(new String[]{"dataImportPage"});
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * @methodName dataDetail
	 * 		描述：	  【明细】操作按钮，根据当前批次号查询出该批次中导入的合同数据列表信息
	 * 		FuncId:  060207
	 * @param selectInfo
	 * @return
	 */
	@RequestMapping("/dataDetail.do")
	public String dataDetail(CntVerifyBean selectInfo)
	{
		List<CntVerifyBean> cntList = dmService.mGetCntList(selectInfo);		//根据导入批次号进行过滤查询，当前批次中导入的合同数据信息
		WebUtils.setRequestAttr("cntList", cntList);
		WebUtils.setRequestAttr("con", selectInfo);
		return BASE_URL + "impCntInfoList";
	}
	
	/**
	 * @methodName importCntDetail
	 * 		描述：	【明细操作子功能】，从dataDetail方法中查询出来的合同列表中，
	 * 					进行指定合同明细信息查看功能[包括执行合同的主体信息(包含关联的物料、分期付款、房屋租赁等)+关联的付款相关信息]
	 * 		FuncId:	06020701
	 * @param cntVerBean
	 * @return
	 */
	@RequestMapping("/importCntDetail.do")
	public String importCntDetail(CntVerifyBean cntVerBean){
		CntVerifyBean cntRelateBean = dmService.getImpCntRelateInfoByCntNum(cntVerBean);		//根据合同号进行关联查询
		WebUtils.setRequestAttr("cntRelateBean", cntRelateBean);
		return BASE_URL + "importCntDtl";
	}
	
	/**
	 * @methodName importPayDetail
	 * 		描述：	【明细操作子功能】：合同关联的付款明细信息
	 *				根据batchNo、payId、payType(正常/预付款)查询导入付款数据的关联详情
	 *					正常付款时：	包括正常付款主信息、预付款核销信息、正常付款物料信息、预付款核销物料信息
	 *					预付款时：		包括预付款主信息、预付款核销信息
	 *					payType付款类型(01:正常付款、02:预付款)
	 *		FuncId:	06020702
	 * @param cntPayBean
	 * @return
	 */
	@RequestMapping("/importPayDetail.do")
	public String importPayDetail(CntRelPayInfo cntPayBean){
		//根据付款的类型(正常/预付)进行查询
		if(null != cntPayBean.getPayType() || !"".equals(cntPayBean.getPayType())){
			if("01".equals(cntPayBean.getPayType())){		
				//需要查询正常付款类型的Pay导入数据
				TdPayBean normalPayInfo = (TdPayBean)dmService.getPayDetailInfo(cntPayBean);
				WebUtils.setRequestAttr("normalPayInfo", normalPayInfo);
			}else if("02".equals(cntPayBean.getPayType())){
				//需要查询预付款类型的Pay导入数据
				TdPayAdvanceBean advPayInfo = (TdPayAdvanceBean)dmService.getPayDetailInfo(cntPayBean);
				WebUtils.setRequestAttr("advPayInfo", advPayInfo);
			}
		}
		return BASE_URL + "importPayDtl";
	}
	
	/**
	 * @methodName dataVerify
	 * 		描述：	【确认】操作按钮，用户在进行导入数据"明细"查看后，进行的数据确认操作
	 * 		处理逻辑：	Service层将调用主存储过程DATA_INIT_MAIN_FLASH(内部将调用子存储过程DATA_INIT_CNT_FLASH和子存储过程DATA_INIT_PAY_FLASH)
	 * 					将导入的合同数据、以及付款数据，从缓存表转移至生产表中；
	 * 		FuncId:	060205
	 * @param selectInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataVerify.do")
	public String dataVerify(UploadDataControlInfoBean selectInfo) throws Exception
	{
		//1.设置选中udcBean对象的确认操作机构
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		selectInfo.setOrg1Code(org1Code);
		//2.执行数据确认操作(该操作将会根据该批次把导入的合同数据+付款数据信息，从导入缓存表中将数据转移至生产表中)
		try {
			dmService.mGetDataVerify(selectInfo);
		} catch (Exception e) {
			WebUtils.getMessageManager().addErrorMessage("存量数据【确认】错误发生异常 ,当前操作批次为["+selectInfo.getBatchNo()+"]!");
			e.printStackTrace();
			ReturnLinkUtils.setShowLink("dataImportPage");
			return ForwardPageUtils.getErrorPage();
		}
		WebUtils.getMessageManager().addInfoMessage("存量数据【确认】操作已成功！");
		ReturnLinkUtils.setShowLink("dataImportPage");
		return ForwardPageUtils.getSuccessPage();
	}
	
	
	/**
	 * @methodName dataDestroy
	 * 
	 * 		描述：	【删除】操作按钮
	 *				功能逻辑：[7-10需求:保留该导入批次的相关数据，只将其状态置为03"删除"，该导入批次号数据后续不再使用]
	 *				1.用户点击【删除】按钮后，弹框提示用户是否确认删除；
	 *				2.用户确认删除后执行删除相关多个操作；
	 *				3.删除操作根据导入批次号batchNo在表UPLOAD_DATA_CONTROL_INFO查找记录：
	 *					3-1.将改记录的DATA_FLAG修改为03(删除)；
	 *					3-2.添加字段UPDT_OPER(更新操作用户)和UPDT_TIME(更新操作时间)属性值；
	 *		FuncId：	060206
	 * @param batchNo	导入批次号
	 * @return
	 */
	@RequestMapping("/dataDestroy.do")
	public String dataDestroy(String batchNo){
		//1.将导入批次Bean的状态置为03"删除"状态，并更新操作者和操作时间
		dmService.dmExecDelOperByBatchNo(batchNo);					
		//2.成功删除则返回删除成功提示页面
		WebUtils.getMessageManager().addInfoMessage("已删除导入批次号为:["+batchNo+"]的导入数据，请重新导入合同数据和付款数据；");
		ReturnLinkUtils.setShowLink(new String[]{"dataImportPage"});
		return ForwardPageUtils.getSuccessPage();
	}

	
	/**
	 * @methodName dataVerifyCheckAjax
	 * 		描述：	【确认】操作按钮前，Ajax检查是否还存在校验错误信息，以决定是否允许用户执行[确认]操作
	 * 		FuncId:	060208
	 * 		Author:	Sunkai
	 * @return		result
	 */
	@RequestMapping("dataVerifyCheckAjax.do")
	@ResponseBody
	public String dataVerifyCheckAjax() {   
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		//Ajax校验该导入批次是否存在校验错误数据，以决定是否允许执行【确认】操作
		String result = dmService.mGetDataVerifyCheckResult(org1Code);
		AbstractJsonObject jsonObj = new SuccessJsonObject();
		jsonObj.put("result", result);
		return jsonObj.writeValueAsString();
	}

}
