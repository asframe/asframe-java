package com.asframe.game.db;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 基于Mongodb的Dao
 * @author sodaChen
 */
public interface IMongoDao<T extends IEntity> extends IDao<T,MongoTemplate>
{
}
