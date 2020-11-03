package com.asframe.utils;


import com.asframe.server.annotation.ErrorAnnotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 提取错误列表的中文文字描述
 * @Author: sodaChen
 * @Date: 2020-01-15
 */
public class AnnotationUtil {

	/**
	 * 通过反射获取注解的值
	 * @return
	 */
	public static Map<Short,String> getAnnotationMap(Class clazz){
		Map<Short,String> jsonMap = new HashMap<>();
		try {
			//获取属性
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields){
				//是否被特定注解修饰
				boolean fieldHasAnno = field.isAnnotationPresent(ErrorAnnotation.class);
				if(fieldHasAnno){
					ErrorAnnotation errorAnnotation = field.getAnnotation(ErrorAnnotation.class);
					//判断用户的采用哪种方式写的错误描述
					if(!errorAnnotation.value().equals(""))
					{
						jsonMap.put(field.getShort(clazz),errorAnnotation.value());
					}
					else
					{
						jsonMap.put(field.getShort(clazz),errorAnnotation.desc());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonMap;
	}
}
