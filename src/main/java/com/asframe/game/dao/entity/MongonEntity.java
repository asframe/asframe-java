package com.asframe.game.dao.entity;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

/**
 * 数据的实体对象，和数据库字段一一对应
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/22
 */
public class MongonEntity implements IEntity
{
//    @Field(value="uid")
//    private long uid;
    @Indexed(name = "index_playerId", direction = IndexDirection.DESCENDING)
    @Field(value="playerId")
    private long playerId;

//    public long getUid() {
//        return uid;
//    }
//
//    public void setUid(long uid) {
//        this.uid = uid;
//    }


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }



    public Query setQuery(Map<String,Object> params)
    {
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
