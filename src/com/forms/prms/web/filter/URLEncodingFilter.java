package com.forms.prms.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLEncodingFilter implements Filter
{

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest  req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) req;  
		Map<String,String[]> paramMap=  processParamsters(request.getParameterMap());
//		String fullURL = request.getRequestURI()+"?"+request.getQueryString();  
		 
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request,paramMap);         
		chain.doFilter(wrapRequest, res);  
		
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // prevent caching at the

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub
		
	}
	
	public Map<String,String[]> processParamsters(Map paraMap) throws UnsupportedEncodingException{
		Map<String,String[]> newMap=new HashMap<String,String[]>();
		for(Object key : paraMap.keySet()){
			String[] result = null;           
			Object v=paraMap.get(key);
			if (v == null) {  
	            result =  null;  
	        } else if (v instanceof String[]) {  
	            result =  (String[]) v;  
	            String[] newSTr=new String[result.length];
	            for(int i=0;i<result.length;i++){
	            	newSTr[i]=decode(result[i], "UTF-8");
	            }
	            result=newSTr;
	        } else if (v instanceof String) {  
	            result =  new String[] { decode((String) v, "UTF-8") };  
	        } else {  
	            result =  new String[] { decode(v.toString(), "UTF-8") };  
	        }  
			newMap.put(key.toString(), result);
		}
		return newMap;
	}

	private static String decode(String str, String charset) throws UnsupportedEncodingException {
		try {
			return URLDecoder.decode(str, charset);
		} catch (IllegalArgumentException e) {
			return str;
		}
	}
}
