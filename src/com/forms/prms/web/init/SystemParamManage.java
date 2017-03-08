package com.forms.prms.web.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.EncryptUtil;
import com.forms.prms.web.sysmanagement.parameter.dao.ParameterDao;
import com.forms.prms.web.sysmanagement.parameter.domain.Parameter;

@Service
public class SystemParamManage {

	/**
	 * 本类唯一实例
	 */
	private static SystemParamManage instance = null;
	private static String osRootPath = null;

	/**
	 * 保存所有参数以及对应的参数值，供系统需要时调用
	 */
	public static Map<String, String> paramsMap = new HashMap<String, String>();

	@Autowired
	private ParameterDao parameterDao;

	public static SystemParamManage getInstance() {
		return SpringUtil.getBean(SystemParamManage.class);
	}

	public synchronized void init() {
		// List<Parameter> paralist=parameterDao.selectPara();
		// if(paralist!=null)
		// {
		// if(paralist.size()>0)
		// {
		// for(Parameter p:paralist)
		// {
		// if(p.getIsPwdType()!=null&&p.getIsPwdType()!=""&&"1".equals(p.getIsPwdType()))
		// {
		// try {
		// paramsMap.put(p.getParamVarName(), new
		// EncryptUtil(null).decipherNumStr(p.getParamValue()));
		// } catch (Exception e) {
		// CommonLogger.error("加载系统参数，解密密码类型参数失败：" + e.getMessage());
		// }
		// }
		// else
		// {
		// paramsMap.put(p.getParamVarName(), p.getParamValue());
		// }
		// }
		//
		// //[10-08:Update] Start
		// //获取系统文件保存的根路径(输入参数以"SERVER_FILE_ROOT_PATH"结尾即可)
		// String serverRootPath =
		// WebHelp.getSysPara("OSTYPE_SERVER_FILE_ROOT_PATH");
		// //根据系统所部署服务器的OS类型拼接参数中的子文件夹路径
		// paramsMap.put("FILE_UPLOAD_BASE_URL" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")) );
		// paramsMap.put("UPFILE_MNGT_BASE_URL" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("UPFILE_MNGT_BASE_URL")) );
		// paramsMap.put("EXCEL_UPLOAD_FILE_DIR" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")) );
		// paramsMap.put("EXCEL_DOWNLOAD_FILE_DIR" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")) );
		// paramsMap.put("FMS_UPLOAD_LOCAL_FOLDER" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("FMS_UPLOAD_LOCAL_FOLDER")) );
		// paramsMap.put("FMS_DOWNLOAD_LOCAL_FOLDER" ,
		// judgeAndSpliceFolderPath(serverRootPath ,
		// WebHelp.getSysPara("FMS_DOWNLOAD_LOCAL_FOLDER")) );
		// //[10-08:Update] End
		//
		// }
		// }
		String rootPath = "";
		String osType = System.getProperties().getProperty("os.name")
				.toUpperCase();
		if (osType.startsWith("WINDOWS")) {
			CommonLogger
					.debug("SystemParameterInit ====> 当前应用所部署服务器的操作系统类型为Windows."); // OSTYPE为Windows
			rootPath = getParaValue("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH");
		} else {
			CommonLogger
					.debug("SystemParameterInit ====> 当前应用所部署服务器的操作系统类型为Unix."); // OSTYPE为Unix
			rootPath = getParaValue("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH");
		}
		this.osRootPath = rootPath;
	}

	public String getParaValue2(String paramVarName) {
		// [10-08:Update]当用户需要取 系统根目录 参数(即以"SERVER_FILE_ROOT_PATH"结尾时，
		// 则根据当前服务器OS类型(windows/unix)决定返回的系统根目录(paramVarName=**SERVER_FILE_ROOT_PATH)参数取值)
		if (paramVarName.endsWith("SERVER_FILE_ROOT_PATH")) {
			String osType = System.getProperties().getProperty("os.name")
					.toUpperCase();
			if (osType.startsWith("WINDOWS")) {
				CommonLogger
						.debug("SystemParameterInit ====> 当前应用所部署服务器的操作系统类型为Windows."); // OSTYPE为Windows
				return (String) paramsMap
						.get("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH");
			} else {
				CommonLogger
						.debug("SystemParameterInit ====> 当前应用所部署服务器的操作系统类型为Unix."); // OSTYPE为Unix
				return (String) paramsMap
						.get("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH");
			}
		} else {
			// return (String) paramsMap.get(paramVarName);
			return parameterDao.getPara(paramVarName);
		}
	}

	public String getParaValue(String paramVarName) {
		// [10-08:Update]当用户需要取 系统根目录 参数(即以"SERVER_FILE_ROOT_PATH"结尾时，
		// 则根据当前服务器OS类型(windows/unix)决定返回的系统根目录(paramVarName=**SERVER_FILE_ROOT_PATH)参数取值)

		Parameter parameter = parameterDao.getparaBean(paramVarName);
		String valueString = parameter.getParamValue();
		if (parameter.getIsPwdType() != null && parameter.getIsPwdType() != ""
				&& "1".equals(parameter.getIsPwdType())) {
			try {
				valueString = new EncryptUtil(null).decipherNumStr(valueString);
			} catch (Exception e) {
				CommonLogger.error("加载系统参数，解密密码类型参数失败：" + e.getMessage());
			}
		}
		if ("FILE_UPLOAD_BASE_URL".equals(paramVarName)
				|| "UPFILE_MNGT_BASE_URL".equals(paramVarName)
				|| "EXCEL_UPLOAD_FILE_DIR".equals(paramVarName)
				|| "EXCEL_DOWNLOAD_FILE_DIR".equals(paramVarName)
				|| "FMS_UPLOAD_LOCAL_FOLDER".equals(paramVarName)
				|| "FMS_DOWNLOAD_LOCAL_FOLDER".equals(paramVarName)) {
			valueString = judgeAndSpliceFolderPath(osRootPath, valueString);
		}
		return valueString;
	}

	/*
	 * public String getParamValue(String paramVarName){ String paraValue =
	 * null; Cache cache = EhcacheUtil.getBaseCache(); Element ele =
	 * cache.get(EhcacheUtil.prefix_key_sys+paramVarName); if(null != ele) {
	 * paraValue = (String) ele.getObjectValue(); if(null == paraValue) {
	 * Parameter para=new Parameter(); para.setParamVarName(paramVarName);
	 * paraValue = parameterDao.findPara(para).getParamValue(); if(null !=
	 * paraValue || !"".equals(paraValue)) { Element paraEle = new
	 * Element(EhcacheUtil.prefix_key_sys+paramVarName,paraValue);
	 * cache.put(paraEle); } } } else { Parameter para=new Parameter();
	 * para.setParamVarName(paramVarName); paraValue =
	 * parameterDao.findPara(para).getParamValue(); if(null != paraValue ||
	 * !"".equals(paraValue)) { Element paraEle = new
	 * Element(EhcacheUtil.prefix_key_sys+paramVarName,paraValue);
	 * cache.put(paraEle); } } return paraValue; }
	 */

	public HashMap getParaValueList() {
		HashMap parasMap = new HashMap();
		List<Parameter> paralist = parameterDao.selectPara();
		if (paralist != null && paralist.size() > 0) {
			for (Parameter p : paralist) {
				parasMap.put(p.getParamVarName(), p.getParamValue());
			}
		}
		return parasMap;
	}

	/**
	 * @methodName judgeAndSpliceFolderPath
	 *             描述：判断根路径与子文件夹路径之间是否有文件夹分隔符，并拼接参数的完整文件夹路径返回
	 *             (如果传入参数根路径和子路径之间没有"/"，则拼接上"/"并返回，如果有"/"则直接返回传入参数组合路径)
	 * @param serverRootPath
	 *            应用服务器中保存系统文件根路径
	 * @param subPath
	 *            需要拼接的子文件路径
	 * @return
	 */
	public String judgeAndSpliceFolderPath(String serverRootPath, String subPath) {
		if (serverRootPath.endsWith("/") || serverRootPath.endsWith("\\")) {
			// 判断服务器根路径参数是否有配文件夹分隔符，如果有配，则拼接根路径+子路径后直接返回
			return serverRootPath + subPath;
		} else {
			if (subPath.startsWith("/") || subPath.startsWith("\\")) {
				// 判断子路径参数是否以文件分隔符开头，若是则直接返回传入参数
				return serverRootPath + subPath;
			} else {
				// 若否，则拼接完文件分隔符后再返回参数组合路径
				return serverRootPath + "/" + subPath;
			}
		}
	}
}
