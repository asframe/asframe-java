package com.asframe.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

/**
 * 错误日志工具类
 * @Author: sodaChen
 * @Date: 2018-10-5
 */
public class FileUtils
{
    /**
     * 获取到文件的真实名字，去掉后缀的
     * @param file
     * @return
     */
    public static String getFileRealName(File file)
    {
        String fileName = file.getName();
        //名字进行处理
        int first = fileName.indexOf(".");
        if(first != -1)
        {
            fileName = fileName.substring(0,first);
        }
        return fileName;
    }

    static Gson gson = new Gson();
    /**
     * 全部文件覆盖
     *
     * @param file
     * @return
     */
    public static void createWriteNoAppent(String file, Map<String,String> strMap) {
        String str = gson.toJson(strMap);
        //创建BufferedWriter
        BufferedWriter out = null;
        try {
            File fileName = new File(file);

            if (fileName.exists()) {
                fileName.delete();
            }
            fileName.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file)));
            out.write(str);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的输出流
     *
     * @param file
     * @return
     */
    public static BufferedReader createReader(String file) {

        //BufferedReader
        BufferedReader out = null;
        try {
            File fileName = new File(file);
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            out = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }


}
