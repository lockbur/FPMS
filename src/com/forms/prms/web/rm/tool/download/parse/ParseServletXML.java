package com.forms.prms.web.rm.tool.download.parse;

import org.dom4j.Node;

import com.forms.prms.web.rm.tool.download.bean.ServletBean;
import com.forms.prms.web.rm.tool.download.bean.ServletClassBean;
import com.forms.prms.web.rm.tool.download.bean.ServletClassMethodBean;
import com.forms.prms.web.rm.tool.download.bean.ServletClassMethodParaBean;
import com.forms.prms.web.rm.tool.download.bean.ServletClassVarBean;

/**
 * 解析公用Servlet的XML文件
 * 
 * @author ahnan
 * @version 1.0 建立时间：2012-10-31 最后修改时间：2012-10-31
 */
public class ParseServletXML {

	/**
	 * 读取一个servlet节点的信息，存储于ServletBean
	 * 
	 * @param servlet
	 * @return servletBean
	 */
	public ServletBean readServletNode(Node servlet) {
		ServletBean servletBean = new ServletBean();
		if (servlet == null) {
			return null;
		} else {
			int size = servlet.selectNodes("class").size();
			ServletClassBean[] ClassNames = new ServletClassBean[size];// class数组

			for (int i = 0; i < size; i++)// 循环读取每一个class信息
			{
				Node classNm = (Node) servlet.selectNodes("class").get(i);
				ClassNames[i] = readClassNode(classNm);
			}

			servletBean.setClasses(ClassNames);
		}

		return servletBean;
	}

	/**
	 * 读取一个class节点的信息，存储于ServletClassBean
	 * 
	 * @param classNm
	 * @return classBean
	 */
	public ServletClassBean readClassNode(Node classNm) {
		ServletClassBean classBean = new ServletClassBean();

		if (classNm == null) {
			return null;
		} else {

			String innerEmtype = classNm.selectSingleNode("innerEmType")
					.getText().trim();// 类方法根节点
			classBean.setReturnInnerEmType(innerEmtype);

		}
		return classBean;
	}

	/**
	 * 读取一个var节点的信息，存储于ServletClassVarBean
	 * 
	 * @param var
	 * @return varBean
	 */
	public ServletClassVarBean readClassVarNode(Node var) {
		ServletClassVarBean varBean = null;

		if (var != null) {
			varBean = new ServletClassVarBean();

			boolean isSmpObj = true;// 变量是否为简单类型
			String tmp = var.valueOf("@smpObj").trim();
			if (tmp != null && tmp.equals("false")) {
				isSmpObj = false;
			}
			varBean.setIsSmpObj(isSmpObj);

			Node varName = (Node) var.selectSingleNode("varName");// 变量名称
			if (varName == null) {
				return null;
			}
			varBean.setVarName(varName.getText().trim());

			Node varType = (Node) var.selectSingleNode("varType");// 变量类型
			if (varType == null) {
				return null;
			}
			varBean.setVarType(varType.getText().trim());
		}

		return varBean;
	}

	/**
	 * 读取一个method节点的信息，存储于ServletClassMethodBean
	 * 
	 * @param method
	 * @return methodBean
	 */
	public ServletClassMethodBean readClassMethodNode(Node method) {
		ServletClassMethodBean methodBean = null;

		if (method != null) {
			methodBean = new ServletClassMethodBean();

			Node returnType = (Node) method.selectSingleNode("returnType");// 方法的返回值
			if (returnType == null) {
				return null;
			}

			Node innerEmType = (Node) returnType
					.selectSingleNode("innerEmType");// 返回类型内部元素的类型
			if (innerEmType != null) {
				methodBean.setReturnInnerEmType(innerEmType.getText().trim());
			}

		}
		return methodBean;
	}

	/**
	 * 读取一个method/para节点的信息，存储于ServletClassMethodParaBean
	 * 
	 * @param para
	 * @return methodParaBean
	 */
	public ServletClassMethodParaBean readClassMethodParaNode(Node para) {
		ServletClassMethodParaBean methodParaBean = null;

		if (para != null) {
			methodParaBean = new ServletClassMethodParaBean();

			boolean isSmpObj = true;// 参数是否为简单类型
			String tmp = para.valueOf("@smpObj").trim();
			if (tmp != null && tmp.equals("false")) {
				isSmpObj = false;
			}
			methodParaBean.setIsSmpObj(isSmpObj);

			Node paraName = (Node) para.selectSingleNode("paraName");// 参数名称
			if (isSmpObj == true) // 参数为简单类型时（含String）名称不能为空，参数为对象时名称可以为空
			{
				if (paraName == null) {
					return null;
				}
				methodParaBean.setParaName(paraName.getText().trim());
			} else {
				if (paraName != null) {
					methodParaBean.setParaName(paraName.getText().trim());
				}
			}

			Node paraType = (Node) para.selectSingleNode("paraType");// 参数类型
			if (paraType == null) {
				return null;
			}
			methodParaBean.setParaType(paraType.getText().trim());
		}
		return methodParaBean;
	}

}
