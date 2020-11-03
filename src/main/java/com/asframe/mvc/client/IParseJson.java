/**
 * @IParseJson.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.mvc.client;

/**
 * 编码和解码json的数据接口（主要是为了不同版本的json实现，默认采用阿里巴巴的fastjson）
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/28
 */
public interface IParseJson<T>
{
    /**
     * 把对象转换成json字符串
     * @param object
     * @return
     */
    String toJson(Object object);

    /**
     * json转换成对应的bean
     * @param json
     * @param clazz
     */
    Object jsonToBean(String json, Class clazz);

    /**
     * json的对象转换成实际使用Bean对象
     * @param jsonObject
     * @param clazz
     * @return
     */
    Object jsonObjectToBean(T jsonObject, Class clazz);
}
