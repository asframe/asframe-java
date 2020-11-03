/**
 * @ObjectRefHandleCenter.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/22
 */
package com.asframe.ref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 对象反射属性操作中心
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/22
 */
public class ObjectRefHandleCenter
{
    private static Logger logger = LoggerFactory.getLogger(ObjectRefHandleCenter.class);
    public static void refHandleCenter(Object object, IObjectRefHandle objectRefHandle)
    {
        refHandleCenter(object,objectRefHandle,false);
    }
    public static void refHandleCenter(Object object, IObjectRefHandle objectRefHandle,boolean isPrivate)
    {
        //通过反射遍历出所有的属性，然后进行通用的绑定
        Field[] fields = object.getClass().getFields();
        if(isPrivate)
        {
            fields = object.getClass().getDeclaredFields();
        }
        Field field;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            try
            {
                if(isPrivate)
                {
                    field.setAccessible(true);
                }
                objectRefHandle.handle(field.get(object));
            }
            catch (Exception e)
            {
                logger.info("refHandleCenter回调报错",e);
            }
        }
    }
}
