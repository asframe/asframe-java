package com.asframe.game.dao.entity;

import java.io.Serializable;

/**
 * 所有DAO的实体类的接口
 * @date 2020/10/10
 */
public interface IEntity extends Serializable {

     long getPlayerId() ;

     void setPlayerId(long playerId) ;
}
