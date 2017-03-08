package com.forms.prms.web.task.xmlparse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.forms.prms.web.task.model.AbstratTask;
import com.forms.prms.web.task.model.DbBean;
import com.forms.prms.web.task.model.LoadTask;
import com.forms.prms.web.task.model.ServerConfig;
import com.forms.prms.web.task.model.UpLoadTask;

@SuppressWarnings("unchecked")
public class ConfigReader {
	private Document document;
	private String realPath;
	private final String xmlPath = "/ServerConfig.xml";

	/**
	 * 读取xml
	 * 
	 * @throws DocumentException
	 */
	private void parse() throws DocumentException {
		InputStream in = getClass().getResourceAsStream(xmlPath);
		SAXReader reader = new SAXReader();
		document = reader.read(in);
	}

	/**
	 * 解析xml配置并将其装载至<code>ServerConfig</code>
	 * 
	 * @see ServerConfig
	 * @throws DocumentException
	 */
	public void execute(String realPath) throws DocumentException {
		this.realPath = realPath;
		try {
			parse();
			ServerConfig.setDbBean(readDbConfig());
			ServerConfig.setReplacedParams(readReplacedParam());
			ServerConfig.setLoadTasks(readTask(LoadTask.class));
			//ServerConfig.setUploadTasks(readTask(UpLoadTask.class));
		} catch (Exception e) {
			// TODO: handle exception
			throw new DocumentException(e);
		}
	}

	/**
	 * 解析数据库配置
	 * 
	 * @return
	 * @throws Exception
	 */
	private DbBean readDbConfig() throws Exception {
		DbBean config = new DbBean();
		Node connNode = document.selectSingleNode("//root/Conn");
		if (connNode == null) {
			throw new Exception(" [ config file is empty!(no //root/Conn) ] ");
		}
		config.setDbName(getValue(connNode, "Db_Name"));
		config.setDbUser(getValue(connNode, "Db_User"));
		config.setDbPassword(getValue(connNode, "Db_Password"));
		return config;
	}

	/**
	 * 解析替换参数
	 * 
	 * @return
	 */
	private Map<String, String> readReplacedParam() {
		Map<String, String> map = new HashMap<String, String>();
		List list = document.selectNodes("//root/ReplacedParam/param");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			map.put(e.attributeValue("Name"), "");
		}
		return map;
	}

	/**
	 * 解析任务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	  private <E> List<E> readTask(Class<E> clazz) throws Exception {
		List<E> result = new ArrayList<E>();
		List list = document.selectNodes("//root/"+clazz.getSimpleName()+"/task");
		if (list == null || list.size() == 0) {
			throw new Exception("没有配置task。");
		}
		for (int i = 0; i < list.size(); i++) {
			Node taskNode = (Node) list.get(i);
			result.add(getOneTask(taskNode,clazz));
		}
		return result;
	}

	/**
	 * 读取指定节点下的任务配置
	 * 
	 * @param taskNode
	 * @return
	 * @throws Exception
	 */
	private <E> E getOneTask(Node taskNode, Class<E> clazz) throws Exception {
	  AbstratTask task = null;
		String taskId = taskNode.valueOf("@Id");
		String taskType = taskNode.valueOf("@type");
		try {
			if ("".equals(taskId) || taskId == null) {
				throw new Exception(" task id can not be null");
			}
			
			if (StringUtils.isEmpty(taskType)) {
        throw new Exception(" task type can not be null");
      } 
			
			
			if(clazz == UpLoadTask.class){
			  UpLoadTask task0 = new UpLoadTask();
			  task0.setType(taskType);
			  task = task0;
			}else{
			  LoadTask task0 = new LoadTask();
			  task0.setType(taskType);
			  task0.setFileName(getValue(taskNode, "FileName"));
			  task0.setUnZip("1".equals(getValue(taskNode, "isUnZip")) ? true
			    : false);
			  task0.setRequired("1".equals(getValue(taskNode, "isRequired")) ? true
          : false);
			  //task.setTableName(getValue(taskNode, "TableName"));
			  String LoadCmd = getValue(taskNode, "LoadCmd");
//			  File file = new File(this.realPath + "/WEB-INF/classes/" + LoadCmd);
//			  if (!file.exists() || !file.canRead()) {
//			    throw new Exception("file '" + LoadCmd + "' not exists");
//			  }
			  task0.setLoadCmd(LoadCmd);
			  task0.setTaskId(taskId);
			  task = task0;
			}
			
			task.setHostAddr(getValue(taskNode, "HostAddr"));
			task.setPort(Integer.parseInt(getValue(taskNode, "Port")));
			task.setUesrName(getValue(taskNode, "UserName"));
			task.setPassword(getValue(taskNode, "Password"));
			task.setFtpFileName(getValue(taskNode, "FtpFileName"));
			return (E)task;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[ config file is error(" + e.getMessage()
					+ ")]");
		}
	}

	/**
	 * 获取并检查某节点下的某子节点是否可用，主要包括：子节点是否存在、子节点是否有值
	 * 
	 * @param node:节点
	 * @param nodeName：
	 *            子节点
	 * @throws Exception
	 */
	private String getValue(Node node, String nodeName) throws Exception {
		Node taskChildNode = node.selectSingleNode(nodeName);
		if (taskChildNode == null) {
			throw new Exception(" no //" + nodeName);
		}
		String value = taskChildNode.getText().trim();
		if ("".equals(value) || value == null) {
			throw new Exception(nodeName + " must have value ");
		}
		return value;
	}
}
