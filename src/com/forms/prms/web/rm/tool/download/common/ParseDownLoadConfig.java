package com.forms.prms.web.rm.tool.download.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.forms.prms.web.rm.tool.download.bean.ServletBean;
import com.forms.prms.web.rm.tool.download.model.DataColsBean;
import com.forms.prms.web.rm.tool.download.model.DownLoadBean;
import com.forms.prms.web.rm.tool.download.model.ExcelBean;
import com.forms.prms.web.rm.tool.download.model.HeaderBean;
import com.forms.prms.web.rm.tool.download.model.HeaderColsBean;
import com.forms.prms.web.rm.tool.download.model.HeaderRowBean;
import com.forms.prms.web.rm.tool.download.model.LayerBean;
import com.forms.prms.web.rm.tool.download.parse.ParseServletXML;

/**
 * 解析download-config.xml文件，取得下载配置信息
 * @see DownLoadBean
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class ParseDownLoadConfig
{
	/**
	 * 解析download-config2.xml文件的主函数
	 * @param realPath
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, DownLoadBean> readXML(String realPath)
	{
		HashMap<String, DownLoadBean> downLoadMap = new HashMap<String, DownLoadBean>();

		String path = realPath + "/common/xml/download-config.xml";
		SAXReader reader = null;
		Document doc = null;
		try
		{
			reader = new SAXReader();
			doc = reader.read(new FileInputStream(path));

			Node root = doc.getRootElement();//根结点
			List<?> tl = root.selectNodes("download");//所有的在XML中定义的download结点
			
			Node res = root.selectSingleNode("resource");
			if(null != res)
			{
				String[] xml = res.getText().split(",");
				for(int i=0; i<xml.length; i++)
				{
					try{
						if(null != xml[i] && !"".equals(xml[i].trim()))
						{
							tl.addAll(reader.read(new File(realPath+xml[i].trim())).getRootElement().selectNodes("download"));
						}
					}catch(Exception e){
					}
				}
			}

			int size = tl.size();

			for (int i = 0; i < size; i++)//循环读取每一个download结点信息
			{
				Node downLoadNode = (Node) tl.get(i);
				String id = downLoadNode.valueOf("@id").trim();				
				DownLoadBean obj = readDownLoad(downLoadNode);
				downLoadMap.put(id, obj);				
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return downLoadMap;
	}

	/**
	 * 读取一个download节点
	 */
	private DownLoadBean readDownLoad(Node downLoadNode)
	{
		DownLoadBean downLoadBean = new DownLoadBean();
		ExcelBean excelBean = new ExcelBean();

		//读取报表头格式信息
		Node excelNode = downLoadNode.selectSingleNode("excel");
		
		List<?> list = excelNode.selectNodes("header");
		HeaderBean headerBean = new HeaderBean();
		for(int i=0; i<list.size(); i++)
		{
			Node headList = (Node) list.get(i);			
			HeaderRowBean obj = parseHeadList(headList);
			headerBean.getHeadList().add(obj);
		}
		excelBean.setHeaderBean(headerBean);

		//读取报表数据信息
		Node dataNode = excelNode.selectSingleNode("data");
		DataColsBean dataColsBean = parseDataCols(dataNode);
		excelBean.setDataColsBean(dataColsBean);

		//把报表信息bean装入downLoadBean
		downLoadBean.setExcelBean(excelBean);

		//读取需要调用的函数配置信息
		ParseServletXML parseServlet = new ParseServletXML();
		ServletBean servletBean = parseServlet.readServletNode(downLoadNode);
		downLoadBean.setServletBean(servletBean);
		
//		Node fieldNode = downLoadNode.selectSingleNode("field");
//		if (fieldNode != null)
//		{
//			downLoadBean.setField(fieldNode.getText().trim());
//		}
//		
//		Node maxNumNode = downLoadNode.selectSingleNode("maxNum");
//		if (maxNumNode != null)
//		{
//			downLoadBean.setMaxNum(Integer.parseInt(maxNumNode.getText().trim()));
//		}
//		
//		Node nmNode = downLoadNode.selectSingleNode("fileNm");
//		if (nmNode != null)
//		{
//			downLoadBean.setNm(nmNode.getText().trim());
//		}
//		
//		Node form = downLoadNode.selectSingleNode("form");
//		if (form != null)
//		{
//			downLoadBean.setFormString(form.getText().trim());
//		}

		return downLoadBean;
	}
	
	private HeaderRowBean parseHeadList(Node headList)
	{
		HeaderRowBean headerRowBean = new HeaderRowBean();
		List<?> list = headList.selectNodes("col");
		
		for(int i=0; i<list.size(); i++)
		{
			Node col = (Node) list.get(i);			
			HeaderColsBean obj = parseHeadRowList(col);
			headerRowBean.getHeadColsList().add(obj);
		}
		
		return headerRowBean;
	}
	
	private HeaderColsBean parseHeadRowList(Node col)
	{
		HeaderColsBean headerColsBean = new HeaderColsBean();
		String rowspan = col.valueOf("@rowspan").trim();
		if(!"".equals(rowspan))
		{
			headerColsBean.setRowspan(rowspan);
		}
		String colspan = col.valueOf("@colspan").trim();
		if(!"".equals(colspan))
		{
			headerColsBean.setColspan(colspan);
		}
		headerColsBean.setCapName(col.getText().trim());
		String paramsIndex = col.valueOf("@paramsIndex").trim();
		if(!(null == paramsIndex || "".equals(paramsIndex)))
		{
			String[] loc_paramsIndex = paramsIndex.split(",");
			int[] loc_indexes = new int[loc_paramsIndex.length];
			for (int j = 0; j < loc_paramsIndex.length; j++)
			{
				loc_indexes[j] = Integer.parseInt(loc_paramsIndex[j]);
			}
			headerColsBean.setParamsIndex(loc_indexes);
		}
		return headerColsBean;
	}

	/**
	 * 解析excel中展示数据的配置信息结点 <data/>
	 * @param dataNode
	 * @return DataColsBean
	 */
	private DataColsBean parseDataCols(Node dataNode)
	{
		DataColsBean dataColsBean = null;
		Node node = null;
		
		List<?> colList = dataNode.selectNodes("col");
		String[] colName = new String[colList.size()];
		int[] colIndex = new int[colList.size()];
		boolean[] colCanSpan = new boolean[colList.size()];
		String[] colType = new String[colList.size()];
		String[] colFormat = new String[colList.size()];
		String[] colTextValue = new String[colList.size()];
		if (colList.size() > 0)
		{
			dataColsBean = new DataColsBean();
		} else
		{
			return null;
		}

		boolean inList = "true".equalsIgnoreCase(dataNode.valueOf("@inList").trim());
		
		for (int i = 0; i < colList.size(); i++)
		{
			node = (Node)colList.get(i);
			
			String property = node.valueOf("@property").trim();
			if(!"".equals(property))
			{
				colName[i]= property;
			}
			
			if(inList)
			{
				String index = node.valueOf("@index").trim();
				try
				{
					colIndex[i] = Integer.parseInt(index) >= 0 ? Integer.parseInt(index) : 0;
				}
				catch (NumberFormatException e)
				{
					colIndex[i] = 0;
				}
			}
			
			colCanSpan[i] = "true".equalsIgnoreCase(node.valueOf("@canspan").trim());
			
			String type = node.valueOf("@type").trim();
			if(!"".equals(type))
			{
				colType[i]= type;
			}
			
			String format = node.valueOf("@format").trim();
			if(!"".equals(format))
			{
				colFormat[i]= format;
			}
			
			String textValue = node.getText().trim();
			if(!"".equals(textValue))
			{
				colTextValue[i]= textValue;
			}
		}
		dataColsBean.setInList(inList);
		dataColsBean.setColName(colName);
		dataColsBean.setListIndex(inList ? colIndex : null);
		dataColsBean.setColCanSpan(colCanSpan);
		dataColsBean.setColType(colType);
		dataColsBean.setFormat(colFormat);
		dataColsBean.setColTextValue(colTextValue);
		LayerBean layerBean = null;
		Node layerNode = dataNode.selectSingleNode("layer");
		if (layerNode != null)
		{
			layerBean = new LayerBean();
			layerBean.setLayerName(layerNode.selectSingleNode("layer-name").getText());
			layerBean.setLayerNumber(Integer.parseInt(layerNode.selectSingleNode("layer-number").getText()));
			Node colTypeNode = layerNode.selectSingleNode("col-type");
			if (colTypeNode != null)
			{
				layerBean.setLayerType(colTypeNode.getText());
			}
			List<?> colorList = layerNode.selectNodes("layer-color");
			String[] layerColor = new String[colorList.size()];
			for (int j = 0; j < colorList.size(); j++)
			{
				Node colorNode = ((Node) colorList.get(j));
				if (colorNode != null)
				{
					layerColor[j] = colorNode.getText();
				}
			}
			layerBean.setLayerColor(layerColor);
		}
		dataColsBean.setLayerBean(layerBean);
		return dataColsBean;
	}	
}
