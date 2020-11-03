package com.asframe.server.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义了错误编码的注解，增加了错误描述
 * @Author: sodaChen
 * @Date: 2020-01-14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorAnnotation {
	String code() default "";
	String value() default "";
	@AliasFor("desc")
	String desc() default "";
}
