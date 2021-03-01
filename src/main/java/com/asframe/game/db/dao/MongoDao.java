package com.asframe.game.db.dao;

import com.asframe.game.db.IEntity;
import com.asframe.game.enums.DaoTypeEnum;
import com.asframe.game.utils.MongoDaoUtils;
import com.mongodb.WriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import game.dao.entity.HeroEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mongoDao的实现对象，实现了mongodb的基础操作
 * @author sodaChen
 */
public class MongoDao<T extends IEntity> extends BaseDao<T,MongoTemplate>
{
    private Logger logger = LogManager.getLogger(MongoDao.class);
    private static String idColumn = "_id";

    public MongoDao(String tableName)
    {
        super(tableName);
        this.daoTypeEnum = DaoTypeEnum.Mongodb;
    }

    /**
     * 创建指定key为id的
     * @param value
     * @return
     */
    public Query createIDQuery( Object value)
    {
        return MongoDaoUtils.createQuery(idColumn,value);
    }
    /**
     * 根据id查找数据
     * @param id
     * @return
     */
    public T findByID(long id)
    {
        Query query = MongoDaoUtils.createQuery(idColumn,id);
        return this.template.findOne(query,entityClass);
    }

    @Override
    public T findByPlayerId(long playerId) {
        return this.find("playerId",playerId);
    }

    @Override
    public List<T> findsByPlayerId(long playerId) {
        return this.finds("playerId",playerId);
    }

    /**
     * 直接保存整个实体对象
     * @param entity
     */
    public void saveEntity(T entity)
    {
        this.template.save(entity,this.tableName);
    }

    /**
     * 根据指定的key和value来查找对象
     * @param id
     * @return
     */
    public List<T> finds(long id)
    {
        Query query = MongoDaoUtils.createQuery(idColumn,id);
        return this.template.find(query,entityClass,this.tableName);
    }


    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    @Override
    public T find(String key, Object value) {
        Query query = MongoDaoUtils.createQuery(key,value);
        return this.template.findOne(query,entityClass,this.tableName);
    }

    /**
     * 根据指定的key和value查询出列表
     * @param key
     * @param value
     * @return
     */
    @Override
    public List<T> finds(String key, Object value)
    {
        Query query = MongoDaoUtils.createQuery(key,value);
        return this.template.find(query,entityClass,this.tableName);
    }


    /**
     * 根据指定的query 查找对象
     * @param query
     * @return
     */
    public List<T> finds(Query query)
    {
        return this.template.find(query,entityClass,this.tableName);
    }

    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    public List<T> finds(String key,long value)
    {
        Query query = MongoDaoUtils.createQuery(key,value);
        return this.template.find(query,entityClass,this.tableName);
    }

    /**
     * 全查
     * @return
     */
    public List<T> findAll() {
        return this.template.findAll(entityClass, this.tableName);
    }

    @Override
    public boolean delete(long id)
    {
        Query query = MongoDaoUtils.createAndIsQuery("id",id);
        List<T> TList =  this.template.findAllAndRemove(query,entityClass,this.tableName);
        return this.isSucceed("删除",TList.size());

    }

    @Override
    public boolean deleteByPlayerId(long playerId)
    {
        Query query = MongoDaoUtils.createQuery("playerId",playerId);
        return this.deleteByQuery(query);
    }

    public boolean deleteByQuery(Query query){

        DeleteResult writeResult =  this.template.remove(query,this.tableName);
        long deletedCount =  writeResult.getDeletedCount();
        if(deletedCount >0)
        {
            return true;
        }else
            {
            logger.info(query + "文档删除一个失败");
            return false;
        }
    }

    /**
     * 保存更新数据到数据库中
     * @param query
     * @param update
     * @return
     */
    public boolean saveDB(Query query, Update update){
        if(update== null){
            logger.info("没有需要更新的数据");
            return true;
        }
        UpdateResult writeResult =  this.template.upsert(query,update,entityClass,this.tableName);
        long deletedCount = writeResult.getModifiedCount();
        if(deletedCount >0){
            return true;
        }else{
            logger.info("文档插入失败");
            return false;
        }
    }



//    public boolean delete(List<WhereCondetion> params)
//    {
//        Query query = new Query();
//        for (WhereCondetion condetion:params) {
//            Criteria criteria = new Criteria();
//            criteria.and(condetion.getStr()).is(condetion.getValue());
//            query.addCriteria(criteria);
//        }
//        List<T> TList =  this.template.findAllAndRemove(query,entityClass,this.tableName);
//
//        return this.isSucess("删除",TList.size());
//    }



    //    @Override
    public boolean insert(T entity)
    {
        this.template.insert(entity,this.tableName);
        return true;
    }

    /**
     * 根据Id更新指定的值
     * @param key
     * @param value
     * @return
     */
    public boolean updateById(long id,String key, Object value)
    {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set(key, value);
        //更新查询返回结果集的第一条
        UpdateResult WriteResult =  this.template.updateFirst(query,update,entityClass,this.tableName);
        return isSucceed("更新",WriteResult.getModifiedCount());
    }

    public boolean update(Query query, Update update)
    {
        if(null == update){
            logger.info("没有指定更新的字段");
            return true;
        }
        //_id  唯一值  更新一条信息就用_id
        UpdateResult WriteResult =  this.template.updateFirst(query,update,entityClass,this.tableName);
        return isSucceed("更新",WriteResult.getModifiedCount());
    }

    public boolean updateAll(Query query, Update update)
    {
        if(null == update){
            logger.info("没有指定更新的字段");
            return true;
        }
        //_id  唯一值  更新一条信息就用_id
        UpdateResult WriteResult =  this.template.updateMulti(query,update,entityClass);
        return isSucceed("更新",WriteResult.getModifiedCount());
    }



    private boolean isSucceed(String msg,long count)
    {
        if(count > 0){
            return true;
        }else{
            logger.info("失败");
            return false;
        }
    }


    /**
     * 多条件查询 key->value   todo 没有试过无条件查询
     * @return
     */
    @Override
    public T findByMap(Map<String,Object> paramsMap)
    {
        Query query = MongoDaoUtils.mapChangeQuery(paramsMap);
        return this.template.findOne(query,entityClass,this.tableName);
    }

    /**
     * 多条件查询 key->value   todo 没有试过无条件查询
     * @return
     */
    @Override
    public List<T> findsByMap(Map<String,Object> paramsMap)
    {
        Query query = MongoDaoUtils.mapChangeQuery(paramsMap);
        return this.template.find(query,entityClass,this.tableName);
    }
}
