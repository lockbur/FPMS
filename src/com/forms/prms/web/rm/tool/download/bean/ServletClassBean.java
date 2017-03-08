package com.forms.prms.web.rm.tool.download.bean;

/**
 * 公用servlet内class的Bean类
 * 存储servlet-config.xml文件中的servlet节点的一个class子节点信息
 * @author ahnan
 * 建立日期：2012-10-31
 * 最后修改日期:2012-10-31
 */
public class ServletClassBean
{
    private String returnInnerEmType;//返回类型的内部元素类型，当返回类型为数组或list时起作用
    private String className;

    public String getClassName ( )
    {
        return className;
    }

    public void setClassName ( String className )
    {
        this.className = className;
    }

    public String getReturnInnerEmType ( )
    {
        return returnInnerEmType;
    }

    public void setReturnInnerEmType ( String returnInnerEmType )
    {
        this.returnInnerEmType = returnInnerEmType;
    }

  
}
