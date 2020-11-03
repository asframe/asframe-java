package com.asframe.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class对象的相关操作方法，主要是一些反射操作
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/23
 */
public class ClassUtils
{
    private static Log logger	= LogFactory.getLog(ClassUtils.class);

    /**
     * 根据类的路径获得到Class对象
     * @param classPath
     * @param <T>
     * @return
     */
    public static <T> Class<T> getClass(String classPath)
    {
        try {
            return (Class<T>)Class.forName(classPath);
        }
        catch (Exception e)
        {
            logger.info("Class.forName反射异常:" + classPath);
        }
        return null;
    }

    /**
     * 根据Class路径创建对象
     * @param classPath
     * @param <T>
     * @return
     */
    public static <T> T createObject(String classPath)
    {
        Class<T> clazz = getClass(classPath);
        if(clazz == null)
        {
            return null;
        }
        return createObject(clazz);
    }

    /**
     * 根据Class对象创建对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createObject(Class<T> clazz)
    {
        try {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            logger.info("createObject反射异常:" + clazz);
        }
        return null;
    }

    public static Object getOMethodValue(Object object,String methodStr)
    {
        try
        {
            Method method = object.getClass().getMethod(methodStr);
            if(method != null)
            {
                return method.invoke(object);
            }
        }
        catch (Exception e)
        {

        }
        return null;
    }



    public static int getObjectMethodValue(Object object,String methodStr)
    {
        try
        {
            Method method = object.getClass().getMethod(methodStr);
            if(method != null)
            {
                return (int)method.invoke(object);
            }
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    public static int getObjectFieldValue(Object object,String fieldStr)
    {
        try
        {
            Field field = object.getClass().getField(fieldStr);
            if(field != null)
            {
                return field.getInt(object);
            }
        }
        catch (Exception e)
        {

        }
        return 0;
    }
    public static void setPrivetFieldValue(Object object,String fieldStr,Object value)
    {
        try
        {
            Field field = object.getClass().getDeclaredField(fieldStr);
            if(field != null)
            {
                field.setAccessible(true);
                field.set(object,value);
            }
        }
        catch (Exception e)
        {
            logger.error(object + "反射出现错误:" + fieldStr);
        }
    }
    public static void setMethodValue(Object object,String methodStr,Object value)
    {
        try
        {
            Method method = object.getClass().getMethod(methodStr);
            if(method != null)
            {
                method.invoke(object,methodStr,value);
            }
        }
        catch (Exception e)
        {
            logger.error(object + "反射出现错误:" + methodStr);
        }
    }
}
