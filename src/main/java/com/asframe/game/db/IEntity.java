package com.asframe.game.db;

import java.io.Serializable;

/**
 * 数据库实体的基础接口，所有的数据库实体对象都继承这个对象
 */
public interface IEntity extends Serializable {
     long getId() ;

     void setId(long id) ;
}
