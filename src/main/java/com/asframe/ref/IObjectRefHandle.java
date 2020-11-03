/**
 * @IObjectRefHandle.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/22
 */
package com.asframe.ref;

/**
 * Object反射属性的操作
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/22
 */
public interface IObjectRefHandle<T>
{
    /**
     * 对属性进行操作
     * @param value
     */
    void handle(T value);
}
