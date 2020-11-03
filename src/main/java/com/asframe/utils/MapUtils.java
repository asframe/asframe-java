package com.asframe.utils;

import com.google.common.collect.ArrayListMultimap;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 反射相关的辅助工具类，主要是实现一些通用的反射操作
 * @author sodaChen
 * Date:2020-10-19
 */
public class MapUtils
{
    /**
     * map转换成对应的keyvalue字符串
     * @param map map集合
     * @param sgin 分割字符
     * @return
     */
    public static String mapToKeyValues(Map<String,Object> map,String sgin,String sgin2)
    {
        StringBuilder stringBuilder = new StringBuilder();
        boolean noFirst = false;
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if(noFirst)
            {
                stringBuilder.append(sgin2);
            }
            else
            {
                noFirst = true;
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append(sgin);
            stringBuilder.append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    /**
     * 转换成 ArrayListMultimap
     *
     * @param list
     * @param keyFunc   key
     * @param valueFunc value
     * @param <T>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <T, K, V> ArrayListMultimap<K, V> toMultimap(List<T> list,
                                                               Function<? super T, ? extends K> keyFunc,
                                                               Function<? super T, ? extends V> valueFunc) {
        if (CollectionUtils.isEmpty(list)) {
            return ArrayListMultimap.create();
        }
        ArrayListMultimap<K, V> result = ArrayListMultimap.create();
        list.forEach(data -> result.put(keyFunc.apply(data), valueFunc.apply(data)));
        return result;
    }

    /**
     * 转换成 ArrayListMultimap
     * value是传进来的list泛型对象
     *
     * @param list
     * @param keyFunc key
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> ArrayListMultimap<K, T> toMultimap(List<T> list,
                                                            Function<? super T, ? extends K> keyFunc) {
        return toMultimap(list, keyFunc, Function.identity());
    }
}
