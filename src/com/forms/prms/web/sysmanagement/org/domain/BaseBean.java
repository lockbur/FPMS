package com.forms.prms.web.sysmanagement.org.domain;

import java.io.Serializable;
import java.util.List;

public class BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int cachedType = 1;
	
	public int getCachedType() {
		return cachedType;
	}

	public void setCachedType(int cachedType) {
		this.cachedType = cachedType;
	}

	private String nodeId;
	
	private List<String> rootNodeList;
	
	private String rootNodeId;
	
	private String rootLevel;
	
	private String leafLevel;
	
	private String treeType;
	
	private String loginUserNodeId;
	
	private boolean async = false;
	
	private boolean asyncLoad = false;
	
	private boolean querySearch = false;
	
	private boolean queryById = true; 
	
	private String queryByValue;
	
	private boolean radioFlag;

	public boolean isRadioFlag() {
		return radioFlag;
	}

	public void setRadioFlag(boolean radioFlag) {
		this.radioFlag = radioFlag;
	}

	public boolean isQuerySearch() {
		return querySearch;
	}

	public void setQuerySearch(boolean querySearch) {
		this.querySearch = querySearch;
	}

	public boolean isQueryById() {
		return queryById;
	}

	public void setQueryById(boolean queryById) {
		this.queryById = queryById;
	}

	public String getQueryByValue() {
		return queryByValue;
	}

	public void setQueryByValue(String queryByValue) {
		this.queryByValue = queryByValue;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}
	
	public boolean isAsyncLoad() {
		return asyncLoad;
	}

	public void setAsyncLoad(boolean asyncLoad) {
		this.asyncLoad = asyncLoad;
	}

	public List<String> getRootNodeList() {
		return rootNodeList;
	}


	public void setRootNodeList(List<String> rootNodeList) {
		this.rootNodeList = rootNodeList;
	}



	public String getNodeId() {
		return nodeId;
	}



	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}



	public String getRootNodeId() {
		return rootNodeId;
	}



	public void setRootNodeId(String rootNodeId) {
		this.rootNodeId = rootNodeId;
	}



	public String getRootLevel() {
		return rootLevel;
	}



	public void setRootLevel(String rootLevel) {
		this.rootLevel = rootLevel;
	}



	public String getLeafLevel() {
		return leafLevel;
	}



	public void setLeafLevel(String leafLevel) {
		this.leafLevel = leafLevel;
	}



	public String getTreeType() {
		return treeType;
	}



	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}



	public String getLoginUserNodeId() {
		return loginUserNodeId;
	}



	public void setLoginUserNodeId(String loginUserNodeId) {
		this.loginUserNodeId = loginUserNodeId;
	}



	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cachedType;
		result = prime * result
				+ ((leafLevel == null) ? 0 : leafLevel.hashCode());
		result = prime * result
				+ ((loginUserNodeId == null) ? 0 : loginUserNodeId.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((rootLevel == null) ? 0 : rootLevel.hashCode());
		result = prime * result
				+ ((rootNodeId == null) ? 0 : rootNodeId.hashCode());
		result = prime * result
				+ ((rootNodeList == null) ? 0 : rootNodeList.hashCode());
		result = prime * result
				+ ((treeType == null) ? 0 : treeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseBean other = (BaseBean) obj;
		if (cachedType != other.cachedType)
			return false;
		if (leafLevel == null) {
			if (other.leafLevel != null)
				return false;
		} else if (!leafLevel.equals(other.leafLevel))
			return false;
		if (loginUserNodeId == null) {
			if (other.loginUserNodeId != null)
				return false;
		} else if (!loginUserNodeId.equals(other.loginUserNodeId))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (rootLevel == null) {
			if (other.rootLevel != null)
				return false;
		} else if (!rootLevel.equals(other.rootLevel))
			return false;
		if (rootNodeId == null) {
			if (other.rootNodeId != null)
				return false;
		} else if (!rootNodeId.equals(other.rootNodeId))
			return false;
		if (rootNodeList == null) {
			if (other.rootNodeList != null)
				return false;
		} else if (!rootNodeList.equals(other.rootNodeList))
			return false;
		if (treeType == null) {
			if (other.treeType != null)
				return false;
		} else if (!treeType.equals(other.treeType))
			return false;
		return true;
	}

	public Node changeSelfToNode(){return null;};
}
