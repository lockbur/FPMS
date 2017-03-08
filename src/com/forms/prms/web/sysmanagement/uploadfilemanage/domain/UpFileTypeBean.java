package com.forms.prms.web.sysmanagement.uploadfilemanage.domain;

/**
 * 上传文件类型Bean类
 * Title:UpFileTypeBean
 * Description:上传文件类型bean
 *
 * Coryright: formssi
 * @author HQQ
 * @project ERP
 * @date 日期
 * @version 1.0
 */
public class UpFileTypeBean {

	private String fileType;			//文件类型(01、02、03、04)
	private String fileTypeDesc;		//文件类型描述(控件类、Excel模板类、ICNS类、报表封面类)
	private String reFileTypeDesc;		//重命名-缓存的文件类型描述(该值将替换掉fileTypeDesc的值)
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileTypeDesc() {
		return fileTypeDesc;
	}
	public void setFileTypeDesc(String fileTypeDesc) {
		this.fileTypeDesc = fileTypeDesc;
	}
	public String getReFileTypeDesc() {
		return reFileTypeDesc;
	}
	public void setReFileTypeDesc(String reFileTypeDesc) {
		this.reFileTypeDesc = reFileTypeDesc;
	}
	
	
	
}
