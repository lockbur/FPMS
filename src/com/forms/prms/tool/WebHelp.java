package com.forms.prms.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.StringDecoder;
import org.apache.commons.lang.StringUtils;

import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkBean;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.user.domain.User;

/**
 * author : wuqm <br>
 * date : 2013-11-8<br>
 * 
 */
public class WebHelp {
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public static User getLoginUser(){
		if (null !=WebUtils.getUserModel()) {
			return (User)WebUtils.getUserModel();
		}
		return null;
	}
	/**
	 * 获取系统参数
	 * @param key
	 * @return
	 */
	public static String getSysPara(String key){
		return SystemParamManage.getInstance().getParaValue(key);
	}
	
	
	/**
	 * 获取系统参数
	 * @param key
	 * @return
	 */
	public static boolean getSysParaBoolean(String key){
		String param =  SystemParamManage.getInstance().getParaValue(key);
		if(null != param)
		{
			return Tool.LANG.string2Boolean(param);
		}
		else
		{
			return Throw.throwException("has not find the config whose name is "+key);
		}
		
	}
	/**
	 * 获取系统参数
	 * @param key
	 * @return
	 */
	public static HashMap getParaValueList(){
		return SystemParamManage.getInstance().getParaValueList();
	}
	/**
	 * MD5散列不可逆加密算法
	 * 
	 * @author Ronald
	 * @param userId
	 * @param password
	 * @return
	 */
//	public static String encryptPassword(String userId, String password)
//	{
//		try
//		{
//			String publicKey1 = "jjejduuhjdjdHDUEHWHd3ehgfidhwh23hHJCRIOI4HDHDHFKHFD3dhdhrg2djsHSDFHFEDJGW";
//			String publicKey2 = "347djejDETahe3j%3jd*%%2DDE223fdhahfh%^@21hdhfhzhdDFQ3hH7eh32hdE#@sdqhqhde";
//			String publicKey3 = "wkjd@wjsdj2324shjsQWeh2dsGed/#21ssdEdhawehrcyzhzeje2#@SDajw2D2sjzahd3#dSa";
//			String str = publicKey1 + userId + publicKey2 + password + publicKey3;
//			MessageDigest alg = MessageDigest.getInstance("MD5");
//			byte[] b = str.getBytes();
//			alg.reset();
//			alg.update(b);
//			byte[] hash = alg.digest();
//			String d = "";
//			for (int i = 0; i < hash.length; i++)
//			{
//				int v = hash[i] & 0xFF;
//				if (v < 16)
//				{
//					d += "0";
//				}
//				d += Integer.toString(v, 16).toUpperCase();
//			}
//			return d;
//		}
//		catch (NoSuchAlgorithmException e)
//		{
//			return null;
//		}
//	}
	
	/**
	 * 设置获取返回上一页的链接地址
	 *  
	 * @param uriName(jsp页面读取uri的标识)
	 * @param linkId(添加上一页的链接Id)
	 * @throws UnsupportedEncodingException 
	 */
	public static void setLastPageLink(String uriName, String linkId)
	{
		ReturnLinkBean bean = ReturnLinkUtils.getLinkBean(linkId);
		if(bean != null ){
			String uri= WebUtils.getRoot() + dealingUri(bean.getLink());
			WebUtils.setRequestAttr(uriName, uri);
		}else{
			WebUtils.setRequestAttr(uriName, "");
		}
	}
	public static String dealingUri(String uri) {
		StringBuffer strBuffer=new StringBuffer();
		if(uri.indexOf("?") != -1){
			
			strBuffer.append(uri.subSequence(0, uri.indexOf("?"))).append("?");
			String subUri=uri.substring(uri.indexOf("?"));
			if(subUri.length() > 1){
				String str[]=subUri.substring(1).split("&");
				for(int i=0;i<str.length;i++){
					String param[]=str[i].split("=");
					if(param.length > 1 ){
						strBuffer.append(param[0]).append("=");
						try
						{
							StringBuffer bufferArray=new StringBuffer();
							if(param[1] != null && !"".equals(param[1])){
								if(param[1].contains("'")){
									for(int j=0;j<param[1].length();j++){
										String ch=param[1].charAt(j)+"";
										if(ch.equals("'")){
											ch="\\"+ch;
										}
										bufferArray.append(ch);
									}
								}else{
									bufferArray.append(param[1]);
								}
							}
							strBuffer.append(URLDecoder.decode(bufferArray.toString(),"UTF-8"));
						}
						catch (UnsupportedEncodingException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						strBuffer.append(str[i]);
					}
					if(i < str.length-1){
						strBuffer.append("&");
					}
				}
			}
		}
		return strBuffer.toString();
	}
	public static void main(String[] args) throws Exception{
//		System.out.println(encryptPassword("admin","111111"));
//		String str="a=";
//		System.out.println(str.split("=").length);
	}
}
