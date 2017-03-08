package com.forms.prms.tool;
 
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
 
import org.apache.commons.beanutils.BeanUtilsBean;
 
/**
 * bean属性拷贝帮助类
 * 
 * 
 */
public class BeanUtils
{
    /**
     * 对一个bean进行深度复制，所有的属性节点全部会被复制
     * 
     * @param source
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T deepCopyBean(T source)
    {
        if (source == null)
        {
            return null;
        }
        try
        {
            if (source instanceof Collection)
            {
                return (T)deepCopyCollection((Collection)source);
            }
            if (source.getClass().isArray())
            {
                return (T)deepCopyArray(source);
            }
            if (source instanceof Map)
            {
                return (T)deepCopyMap((Map)source);
            }
            if (source instanceof Date)
            {
                return (T)new Date(((Date)source).getTime());
            }
            if (source instanceof Timestamp)
            {
                return (T)new Timestamp(((Timestamp)source).getTime());
            }
            // 基本类型直接返回原值
            if (source.getClass().isPrimitive() || source instanceof String || source instanceof Boolean
                || Number.class.isAssignableFrom(source.getClass()))
            {
                return source;
            }
            if (source instanceof StringBuilder)
            {
                return (T)new StringBuilder(source.toString());
            }
            if (source instanceof StringBuffer)
            {
                return (T)new StringBuffer(source.toString());
            }
            Object dest = source.getClass().newInstance();
            BeanUtilsBean bean = BeanUtilsBean.getInstance();
            PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(source);
            for (int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if ("class".equals(name))
                {
                    continue;
                }
                 
                if (bean.getPropertyUtils().isReadable(source, name) && bean.getPropertyUtils().isWriteable(dest, name))
                {
                    try
                    {
                        Object value = deepCopyBean(bean.getPropertyUtils().getSimpleProperty(source, name));
                        bean.getPropertyUtils().setSimpleProperty(dest, name, value);
                    }
                    catch (NoSuchMethodException e)
                    {
                    }
                }
            }
            return (T)dest;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
     
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Collection deepCopyCollection(Collection source)
        throws InstantiationException, IllegalAccessException
    {
        Collection dest = source.getClass().newInstance();
        for (Object o : source)
        {
            dest.add(deepCopyBean(o));
        }
        return dest;
    }
     
    private static Object deepCopyArray(Object source)
        throws InstantiationException, IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException
    {
        int length = Array.getLength(source);
        Object dest = Array.newInstance(source.getClass().getComponentType(), length);
        if (length == 0)
        {
            return dest;
        }
        for (int i = 0; i < length; i++)
        {
            Array.set(dest, i, deepCopyBean(Array.get(source, i)));
        }
        return dest;
    }
     
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Map deepCopyMap(Map source)
        throws InstantiationException, IllegalAccessException
    {
        Map dest = source.getClass().newInstance();
        for (Object o : source.entrySet())
        {
            Entry e = (Entry)o;
            dest.put(deepCopyBean(e.getKey()), deepCopyBean(e.getValue()));
        }
        return dest;
    }
     
}