package com.asframe.common;

/**
 * 复制对象的所有数值，主要是这对列表和数组之类的接口
 * @author sodaChen
 * @version 1.0
 * <br>Date:2019/10/2
 */
public interface ICopyValues<S,T> {
    /**
     * 遍历方法
     * @param source 原对象（被复制的对象）
     * @param target 新的对象，接受原对象的相关数据
     */
    void forEach(S source, T target);
}
