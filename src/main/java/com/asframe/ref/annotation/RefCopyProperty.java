/**
 * @RefChangeProperty.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.ref.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 反射复制的时候把原来的值变成新的类型值（同时也可以指定新的属性名）
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefCopyProperty {
    /**
     * Java的基础数据类型
     * @return
     */
    String value() default "";
    /**
     * 对应的新的字段名称名称
     * @return
     */
    String field() default "";
}
