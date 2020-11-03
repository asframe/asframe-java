package com.asframe.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 字符串工具
 * @author: sodaChen
 * @date: 2020/10/10
 */
public class StringUtil {
    public static String strArray2Str(String[] strs){
        String res = "[";
        for(String s:strs){
            res += "\"" + s + "\",";
        }
        res = res.substring(0 , res.length() - 1);
        res += "]";
        return res;
    }

    public static String strArray2Str(ArrayList<String> strs){
        String res = "[";
        for(String s:strs){
            res += "\"" + s + "\",";
        }
        res = res.substring(0 , res.length() - 1);
        res += "]";
        return res;
    }

    public static String str2Str(String str){
        if(null == str ) return "null";
        return "\"" + str + "\"";
    }

    /**
     * 补全0x 数字
     * @param str
     * @param len 一共的位数
     * @return
     */
    public static String padLeft(String str,int len){
        String pad="0x00000000000000";
        return len>str.length()&&len<=16&&len>=0?pad.substring(0,len-str.length())+str:str;
    }


    public static String IpNum2IpStr(int ipInt) {
        return String.valueOf(((ipInt >> 24) & 0xff)) + '.' +
                ((ipInt >> 16) & 0xff) + '.' +
                ((ipInt >> 8) & 0xff) + '.' + (ipInt & 0xff);
    }

	public static String delLine(String way) {

        while(way.endsWith("/")|| way.endsWith("\\")){
            way = way.substring(0,way.length()-1);
        }
        return way;

	}
    public static String joinArray(List<String> strList,String c){
        if(strList.size() <= 0){
            return "";
        }
        StringBuffer strJoin = new StringBuffer();
        strJoin.append("'");
        for (String str : strList) {
            strJoin.append(str);
            strJoin.append("'");
            strJoin.append(c);
            strJoin.append("'");
        }
        return strJoin.substring(0,strJoin.length() - 2);
    }
}
