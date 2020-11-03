package com.asframe.utils;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类，包含了系统全局工具，自动拼装工具
 *
 * @author  sodaChen
 * @date 2019年20月20日
 */
public class LogUtils
{
    /**
     * 所有不需要单独建立日志系统的地方，都采用这个logger
     */
    public final static Logger logger = LoggerFactory.getLogger(LogUtils.class);
    public final static String SPLIT = "|";
    public final static String dateFormat = "yyyy-MM-dd HH:mm:ss";


    /**
     * 自动拼接日志
     * @param args
     * @return
     */
    public static String makeupLog(Log log, Object... args)
    {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat sdf = null;
        Object obj;
        int last = args.length - 1;
        for (int i = 0; i < args.length; i++)
        {
            obj = args[i];
            if(obj instanceof Date)
            {
                if(sdf == null)
                    sdf = new SimpleDateFormat(dateFormat);
                stringBuilder.append(sdf.format(obj));
            }
            else
            {
                stringBuilder.append(obj);
            }
            if(i < last)
            {
                stringBuilder.append(SPLIT);
            }
        }
        String str = stringBuilder.toString();
        try
        {
            log.info(stringBuilder.toString());
        }
        catch (Exception e)
        {
            logger.error("拼装日志报错:" + str,e);
        }
        return str;
    }
}
