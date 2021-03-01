package com.asframe.game.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.Map;

/**
 * mongo的工具类
 */
public class MongoDaoUtils
{
    /**
     * 创建带key和value
     * @param key
     * @param value
     * @return
     */
    public static Query createQuery(String key, Object value)
    {
        Query query = new Query();
        Criteria criteria  = Criteria.where(key).is(value);
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 创建 and和is的查询语句
     * @param key
     * @param value
     * @return
     */
    public static Query createAndIsQuery(String key, Object value)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(key).is(value);
        query.addCriteria(criteria);
        return query;
    }

    /**
     * map集合对象转换对应的Query对象
     * @param paramsMap
     * @return
     */
    public static Query mapChangeQuery(Map<String,Object> paramsMap)
    {
        Query query = new Query();
        Criteria criteria = null;
        if(paramsMap != null){
            int count  = 0 ;
            for (Map.Entry<String, Object> entry:paramsMap.entrySet()) {
                if(count == 0){
                    count ++;
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                }else{
                    criteria.and(entry.getKey()).is(entry.getValue());
                }
            }
        }
        query.addCriteria(criteria);
        return query;
    }



    public static Query setQuery(Map<String,Object> params)
    {
        Query query = new Query();
        if(params.size()>0){

            Criteria criteria = null;
            int count = 0;
            for (Map.Entry<String,Object> entry : params.entrySet())
            {
                if(count == 0){
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                    count++;
                }else{
                    criteria.and(entry.getKey()).is(entry.getValue());
                }

            }
            if(criteria != null)
                query.addCriteria(criteria);
        }
        return query;
    }
    public static Update setUpdate(Map<String,Object> params)
    {
        Update update  = null;
        if(params.size()>0){
            update = new Update();
            for (Map.Entry<String,Object> entry : params.entrySet()){
                update.set(entry.getKey(),entry.getValue());
            }
        }
        return update;
    }


    public static Query setQueryById(int id)
    {
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        Query query = null;
        if(params.size()>0){
            query = new Query();
            Criteria criteria = null;
            int count = 0;
            for (Map.Entry<String,Object> entry : params.entrySet()){
                if(count == 0){
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                    count++;
                }else{
                    criteria.and(entry.getKey()).is(entry.getValue());
                }

            }
            query.addCriteria(criteria);
        }
        return query;
    }
}
