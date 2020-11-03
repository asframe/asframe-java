package com.asframe.utils;

import com.asframe.common.ICopyValues;

import com.asframe.ref.annotation.RefCopyProperty;
import com.asframe.ref.annotation.RefCopyPublic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射相关的辅助工具类，主要是实现一些通用的反射操作
 * @author sodaChen
 * Date:2019-1-19
 */
public class RefUtils
{
    private static Logger logger = LogManager.getLogger(RefUtils.class);


    private static final String intClass = int.class.toString();
    private static final String shortClass = short.class.toString();
    private static final String longClass = long.class.toString();
    private static final String stringClass = String.class.toString();
    private static final String floatClass = float.class.toString();
    private static final String doubleClass = double.class.toString();
    private static final String booleanClass = boolean.class.toString();
    private static final String dateClass = Date.class.toString();
    /**
     * 复制数组，不需要指定是否复制public
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyObjectList(List<?> sourceList,Class<T> targetClass)
    {
        Object source = sourceList.get(0);
        RefCopyPublic refCopyPublic = source.getClass().getAnnotation(RefCopyPublic.class);
        return copyObjectList(sourceList,targetClass,refCopyPublic == null,null);
    }
    public static <T> List<T> copyObjectList(List<?> sourceList,Class<T> targetClass,boolean isPrivate)
    {
        return copyObjectList(sourceList,targetClass,isPrivate,null);
    }
    public static <T> List<T> copyObjectList(List<?> sourceList, Class<T> targetClass, boolean isPrivate, ICopyValues copyValues)
    {
        List<T> targetList = new ArrayList<>();
        sourceList.forEach(object ->
        {
            T target = ClassUtils.createObject(targetClass);
            //进行属性复制
            copyObjectValue(object,target,isPrivate);
            if(copyValues != null) {
                copyValues.forEach(object,target);
            }
            targetList.add(target);
        });
        return targetList;
    }
    public static Object copyObjectValue(Object source,Class targetClass,boolean isPrivate)
    {
        Object target = ClassUtils.createObject(targetClass);
        copyObjectValue(source,target,isPrivate);
        return target;
    }
    public static void copyObjectValue(Object source,Object target)
    {
        RefCopyPublic refCopyPublic = source.getClass().getAnnotation(RefCopyPublic.class);
        copyObjectValue(source,target,refCopyPublic == null);
    }
    /**
     * 复制source一样的属性到target中
     * @param source
     * @param target
     * @param isPrivate 是否复制私有属性
     */
    public static void copyObjectValue(Object source,Object target,boolean isPrivate)
    {
        if(target == null)
        {
            return ;
        }
        Field[] sourceFields;
        if(isPrivate)
        {
            sourceFields = source.getClass().getDeclaredFields();
        }
        else
        {
            sourceFields = source.getClass().getFields();
        }
        Field sourceField;
        Field targetField;
        //目标属性的名称
        String targetFieldName = null;
        //目标属性的类型
        String targetFieldType;
        Class targetClass = target.getClass();
        int length = sourceFields.length;
        for (int i = 0; i < length; i++) {
            sourceField = sourceFields[i];
            //目标属性的类型
            targetFieldType = null;
            //判断目标中是否有一样的属性
            try
            {
                targetFieldName = sourceField.getName();
                //判断当前是否有属性配置的注解
                RefCopyProperty refCopyProperty = sourceField.getAnnotation(RefCopyProperty.class);
                if(refCopyProperty != null)
                {
                    //看下是否改变属性名
                    if(!refCopyProperty.field().equals(""))
                    {
                        //有指定新的属性
                        targetFieldName = refCopyProperty.field();
                    }
                    if(!refCopyProperty.value().equals(""))
                    {
                        //有指定新的属性
                        targetFieldType = refCopyProperty.value();
                    }
                }
                if(isPrivate)
                {
                    targetField = targetClass.getDeclaredField(targetFieldName);
                }
                else
                {
                    targetField = targetClass.getField(targetFieldName);
                }
            }
            catch (Exception e)
            {
                targetField = null;
            }
            if(targetField != null)
            {
                if(isPrivate)
                {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                }
                //取值
                Object value = null;
                try
                {
                    value = sourceField.get(source);
                    if(value == null)
                    {
                        //空值不需要处理
                        continue ;
                    }

                    //判断一下是否类型相等
                    if(sourceField.getType() == targetField.getType())
                    {
                        if(value instanceof List)
                        {
                            setTargetField(target,targetField,value,targetFieldType);
                        }
                        else
                        {
                            //基础类型不需要目标类型，直接设置相应的值
                            setTargetField(target,targetField,value,null);
                        }

                    }
                    else
                    {
                        //类型不一样，看看有没有类型转换注解
                        if(targetFieldType != null)
                        {
                            //判断转换的是否为基础类型
                            if (targetFieldType.equals(intClass)) {
                                value = Integer.parseInt(value.toString());
                            } else if (targetFieldType.equals(shortClass)) {
                                value = Short.parseShort(value.toString());
                            } else if (targetFieldType.equals(longClass)) {
                                value = Long.parseLong(value.toString());
                            } else if (targetFieldType.equals(floatClass)) {
                                value = Float.parseFloat(value.toString());
                            } else if (targetFieldType.equals(doubleClass)) {
                                value = Double.parseDouble(value.toString());
                            } else if (targetFieldType.equals(booleanClass)) {
                                value = Boolean.parseBoolean(value.toString());
                            } else if (targetFieldType.equals(stringClass)
                                    || targetFieldType.equals("String") || targetFieldType.equals("string") ) {
                                value = String.valueOf(value);
                            }
                            else
                            {
                                //剩下的是反射不同的object了
                                setTargetField(target,targetField,value,targetFieldType);
                                continue;
                            }
                        }
                        //没有定义转换的数据类型，但是如果是Date的话，需要做自动的时间转换
                        if(sourceField.getType() == Date.class)
                        {
                            setTargetFieldDate(target,targetField,value);
                            continue ;
                        }
                        else if(sourceField.getType() == java.util.Date.class)
                        {
                            setTargetFieldUtilsDate(target,targetField,value);
                            continue ;
                        }
                        else if(targetField.getType() == java.util.Date.class)
                        {
                            setLongToDate(target,targetField,value);
                            continue ;
                        }
                        setTargetField(target,targetField,value,null);
                    }

                }
                catch (Exception e)
                {
                    logger.error(source+"->"+sourceField + "的属性反射失败",e);
                }
            }
        }
    }
    private static void setLongToDate(Object target,Field targetField,Object value) throws Exception
    {
        //todo 以后有可能是字符串之类的转成date
        java.util.Date date = new java.util.Date((long)value);
        setTargetField(target,targetField,date,null);
    }
    private static void setTargetFieldUtilsDate(Object target,Field targetField,Object value) throws Exception
    {
        java.util.Date date = (java.util.Date)value;
        Class<?> clazz = targetField.getType();
        if(clazz == int.class || clazz == long.class || clazz == Integer.class || clazz == Long.class)
        {
            setTargetField(target,targetField,date.getTime(),null);
        }
        else if(clazz == String.class)
        {
            //使用默认的，以后这里可以使用相关的注解
            setTargetField(target,targetField,DateUtil.dateToDayStr(date),null);
        }
        else
        {
            logger.error(targetField.getName() + "出现Date是没有对应类型的情况:" + target);
        }
    }

    /**
     * 设置处理源对象是Date的情况
     * @param target
     * @param targetField
     * @param value
     * @throws Exception
     */
    private static void setTargetFieldDate(Object target,Field targetField,Object value) throws Exception
    {
        Date date = (Date)value;
        Class<?> clazz = targetField.getType();
        if(clazz == int.class || clazz == long.class || clazz == Integer.class || clazz == Long.class)
        {
            setTargetField(target,targetField,date.getTime(),null);
        }
        else if(clazz == String.class)
        {
            //使用默认的，以后这里可以使用相关的注解
            setTargetField(target,targetField,DateUtil.dateToDayStr(date),null);
        }
        else
        {
            logger.error(targetField.getName() + "出现Date是没有对应类型的情况:" + target);
        }
    }

    /**
     * 设置目标字段的属性
     * @param target
     * @param targetField
     * @param value
     * @param targetFieldType
     * @throws Exception
     */
    private static void setTargetField(Object target,Field targetField,Object value,String targetFieldType) throws Exception
    {
        //过滤空值
        if(value == null)
        {
            return;
        }
        //对象是否需要进行深度copy
        if(targetFieldType == null)
        {
            //进行赋值
            targetField.set(target,value);
            return ;
        }
        Class clazz = ClassUtils.getClass(targetFieldType);
        Object targetValue;
        if(value instanceof List)
        {
            //数组需要特别处理
            List list = (List)value;
            if(list.size() == 0)
            {
                targetValue = list;
            }
            else
            {
                targetValue = copyObjectList(list,clazz);
            }
        }
        else
        {
            targetValue = ClassUtils.createObject(clazz);
            //进行复制
            copyObjectValue(value,targetValue);
        }
        targetField.set(target,targetValue);
    }
    /**
     * keyvalue字符串变成有对应属性的map集合
     * @param keyValues
     * @param sign
     * @param valueSign
     * @param clazz
     * @return
     */
    public static Map<String,Object> stringToMap(String keyValues,String sign,String valueSign,Class<?> clazz)
    {
        String[] strings = keyValues.split(sign);
        Map<String, Object> map = new HashMap<>();
        //获取这个类的所有属性名对应的属性值
        Map<String,Field> fieldMap = reciveFieldMap(clazz);

        for (int i = 0; i < strings.length; i++) {
            String[] keyValue = strings[i].split(valueSign);
            String value ="";
            if(keyValue.length<2){
                System.out.println("分割字符串有问题");
            }else{
                value = keyValue[1];
            }
            String key = keyValue[0];
            if("null".equals(value)){
                value = "0";
            }
            try {
                if (clazz != null) {
                    if ("channels".equals(key)) {
                        System.out.println("添加");
                    }
                    Field field = fieldMap.get(key);
                    if (field == null) {
                        //记录错误信息
                        continue;
                    }
                    Class<?> classType = field.getType();
                    if (classType == int.class) {
                        map.put(key, Integer.parseInt(value));
                    } else if (classType == short.class) {
                        map.put(key, Short.parseShort(value));
                    } else if (classType == long.class) {
                        map.put(key, Long.parseLong(value));
                    } else if (classType == float.class) {
                        map.put(key, Float.parseFloat(value));
                    } else if (classType == double.class) {
                        map.put(key, Double.parseDouble(value));
                    } else if (classType == boolean.class) {
                        map.put(key, Boolean.parseBoolean(value));
                    } else {
                        map.put(key, value);
                    }
                } else {
                    map.put(key, value);
                }

            } catch (Exception e) {
                //有字段报错，终止填充，并且返回控制后台，提醒他他有这个错误(多个错误一起积累再发过去)
                logger.error("map反射出错", e);
            }
        }
        return map;
    }


    private static Map<String, Field> reciveFieldMap(Class<?> clazz) {
       Map<String, Field> fieldMap = new HashMap<>();
        do {
                Field[] fields = clazz.getDeclaredFields();
                if(fields == null){
                        return fieldMap;
                    }
                for (int i = 0; i < fields.length; i++) {
                        fieldMap.put(fields[i].getName(), fields[i]);
                    }
                clazz = clazz.getSuperclass();
            }while (clazz!=null);
        return fieldMap;
    }


    /**
     * map对象里的值填充到应对的Bean对应的私有属性方法
     * @param map
     * @param object
     * @param isPrivate
     */
    public static void mapToObjectField(Map<String,Object> map,Object object,boolean isPrivate)
    {
        Class clazz = object.getClass();
        Map<String,Field> fieldMap = reciveFieldMap(clazz);
        //遍历map，需要遍历出key，推荐使用迭代器，因为key和value都需要使用到
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            try
            {
//                Field field = clazz.getDeclaredField(entry.getKey());
                Field field = fieldMap.get(entry.getKey());
                if(field != null)
                {
                    if(isPrivate) {
                        field.setAccessible(true);
                    }

                    field.set(object,entry.getValue());
                }
            }
            catch (Exception e)
            {
                logger.error("mapToObjectField反射出错",e);
            }
        }
    }
    /**
     * map对象里的值填充到应对的Bean对应的私有属性方法
     * @param map
     * @param object
     * @param isPrivate
     */
    public static void mapStringToObjectField(Map<String,String> map,Object object,boolean isPrivate)
    {
        Class clazz = object.getClass();
        //遍历map，需要遍历出key，推荐使用迭代器，因为key和value都需要使用到
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            try
            {
                Field field = clazz.getDeclaredField(key);
                if(field != null)
                {
                    if(isPrivate) {
                        field.setAccessible(true);
                    }
                    //需要根据类型转换对应的值
                    if(field.getType() == int.class)
                    {
                        field.set(object,Integer.parseInt(value));
                    }
                    else if(field.getType() == short.class)
                    {
                        field.set(object,Short.parseShort(value));
                    }
                    else if(field.getType() == long.class)
                    {
                        field.set(object,Long.parseLong(value));
                    }
                    else if(field.getType() == float.class)
                    {
                        field.set(object,Float.parseFloat(value));
                    }
                    else if(field.getType() == double.class)
                    {
                        field.set(object,Double.parseDouble(value));
                    }
                    else if(field.getType() == boolean.class)
                    {
                        field.set(object,Boolean.parseBoolean(value));
                    }
                    else
                    {
                        field.set(object,value);
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("mapStringToObjectField反射出错",e);
            }
        }
    }
}
