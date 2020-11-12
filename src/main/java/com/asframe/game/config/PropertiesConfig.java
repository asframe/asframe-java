package com.asframe.game.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 属性配置文件处理类
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class PropertiesConfig
{
    private static Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);
    public static void autoCofnig(String propertiesPath, Class clazz)
    {
        logger.info("开始读取Properties配置文件:" + propertiesPath);
        Properties properties = new Properties();
        try
        {
            FileInputStream in = new FileInputStream(propertiesPath);
            properties.load(in);
            in.close();
            //进行反射赋值
            Field[] fields = clazz.getFields();
            for (int i = 0; i < fields.length; i++)
                {
                Field field = fields[i];
                String name = field.getName();

                if(properties.containsKey(name))
                {
                    String value = properties.getProperty(name);
                    //进行反射赋值
                    if(field.getType() == int.class)
                    {
                        field.set(clazz,Integer.parseInt(value));
                    }
                    else if(field.getType() == boolean.class)
                    {
                        field.set(clazz,Boolean.parseBoolean(value));
                    }
                    else if(field.getType() == String.class)
                    {
                        field.set(clazz,value);
                    }
                    else if(field.getType() == Long.class)
                    {
                        field.set(clazz,Long.parseLong(value));
                    }
                    else if(field.getType() == Double.class)
                    {
                        field.set(clazz,Double.parseDouble(value));
                    }
                    //打印实际配置值
                    logger.info("GameConfig." + field.getName() + ":" + field.get(clazz));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("加载" + propertiesPath + "配置文件报错");
            e.getStackTrace();
        }
    }
}
