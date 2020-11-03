package com.asframe.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;
/**
 * 属性配置对象，自动填充到对象中去
 * @author sodaChen
 * @version 1.0
 * <br>Date:2019/10/2
 */
public class PropertiesConfig
{
    private static Logger logger = LogManager.getLogger(PropertiesConfig.class);

    public static void autoCofnig(String propertiesPath, Class clazz)
    {
        logger.info("读取Properties配置文件:" + propertiesPath);
        Properties properties = new Properties();
        try
        {
            FileInputStream in = new FileInputStream(propertiesPath);
            properties.load(in);
            in.close();

            Field[] fields = clazz.getFields();
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                String name = field.getName();
                if (properties.containsKey(name))
                {
                    String value = properties.getProperty(name);
                    if (field.getType() == Integer.TYPE) {
                        field.set(clazz, Integer.valueOf(Integer.parseInt(value)));
                    } else if (field.getType() == Boolean.TYPE) {
                        field.set(clazz, Boolean.valueOf(Boolean.parseBoolean(value)));
                    } else if (field.getType() == String.class) {
                        field.set(clazz, value);
                    } else if (field.getType() == Long.class) {
                        field.set(clazz, Long.valueOf(Long.parseLong(value)));
                    } else if (field.getType() == Double.class) {
                        field.set(clazz, Double.valueOf(Double.parseDouble(value)));
                    }
                    logger.info("GameConfig." + field.getName() + ":" + field.get(clazz));
                }
            }
        }
        catch (Exception e)
        {
            logger.error("加载" + propertiesPath + "配置文件报错");
            e.getStackTrace();
        }
    }
}
