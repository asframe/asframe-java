package com.asframe.game.db.dao;

import com.asframe.game.GameSession;
import com.asframe.game.db.IDao;
import com.asframe.game.db.IEntity;
import com.asframe.game.enums.DaoTypeEnum;
import game.dao.entity.BagEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * 基础的dao操作对象
 * @author sodaChen
 */
public abstract class BaseDao<T extends IEntity,M> implements IDao<T,M>
{
    protected DaoTypeEnum daoTypeEnum;
    protected String tableName;
    protected M template;
    /**
     * 实体bean的Class对象
     */
    protected Class<T> entityClass;

    public BaseDao(String tableName)
    {
        this.tableName = tableName;
        GameSession.daoList.add(this);
    }
    @Override
    public DaoTypeEnum getType() {
        return this.daoTypeEnum;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public M getTemplate() {
        return template;
    }

    @Override
    public void setTemplate(M template) {
        this.template = template;
    }
}
