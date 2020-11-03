package com.asframe.utils;

import java.util.List;

/**
 * 数据的保存接口
 *
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/17
 */
public class ListUtils
{
    public static void removeInts(List<Integer> list1,List<Integer> list2)
    {
        int value;
        for (int i = 0; i < list2.size(); i++)
        {
            //取出比较值
            value = list2.get(i);
            //找到并且进行删除
            for (int j = 0; j < list1.size(); j++)
            {
                if(value == list1.get(j))
                {
                    list1.remove(j);
                    break;
                }
            }
        }
    }
    /**
     * 从list中删除对应的数值
     * @param list 数值列表
     * @param value 需要删除的数值
     */
    public static void addLong(List<Long> list,long value)
    {
        int length = list.size();
        for (int i = 0; i < length; i++)
        {
            if(list.get(i) == value)
            {
                return;
            }
        }
        list.add(value);
    }
    /**
     * 从list中删除对应的数值
     * @param list 数值列表
     * @param value 需要删除的数值
     */
    public static void removeLong(List<Long> list,long value)
    {
        int length = list.size();
        for (int i = 0; i < length; i++)
        {
            if(list.get(i) == value)
            {
                list.remove(i);
                return ;
            }
        }
    }

    /**
     * 从list中删除对应的数值
     * @param list 数值列表
     * @param value 需要删除的数值
     */
    public static void removeInt(List<Integer> list,int value)
    {
        int length = list.size();
        for (int i = 0; i < length; i++)
        {
            if(list.get(i) == value)
            {
                list.remove(i);
                return ;
            }
        }
    }
    public static void remove(List<?> list ,Object item)
    {
        if(list.indexOf(item) != -1)
        {
            list.remove(item);
        }

    }
}
