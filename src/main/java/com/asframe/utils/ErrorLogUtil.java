package com.asframe.utils;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * 错误日志工具类
 * @Author: sodaChen
 * @Date: 2018-10-5
 */
public class ErrorLogUtil {
    private static Logger logger = LogManager.getLogger(ErrorLogUtil.class);

    /**
     *
     * @param e
     * @return
     */
    public static String errorTrackSpaceString(Throwable e)
    {
        StringBuffer stringBuffer =  new StringBuffer();
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                stringBuffer.append("\r\n\t").append(element);
            }
        }
        return stringBuffer.length() == 0 ? null :   e+"\r\n" +stringBuffer.toString();
    }
    public static void errorTrackSpace(Logger log,Throwable e)
    {
        log.error("================开始封装打印错误日志==================");
        StringBuffer stringBuffer =  new StringBuffer();
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                stringBuffer.append("\r\n\t").append(element);
            }
        }
        log.error(stringBuffer.length() == 0 ? null :   e+"\r\n" +stringBuffer.toString());
        log.error("================结束封装打印错误日志==================");
    }
    /**
     * 输出异常信息
     * @param e
     * @return
     */
    public static void errorTrackSpace(Throwable e) {
        errorTrackSpace(logger,e);
    }
}
