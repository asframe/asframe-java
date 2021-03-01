package com.asframe.game.db;

import java.io.Serializable;

/**
 * 和玩家相关的实体对象，增加了playerId
 */
public interface IPlayerEntity extends IEntity {
     /**
      * 获得玩家id
      * @return
      */
     long getPlayerId() ;

     /**
      * 设置玩家id
      * @param playerId
      */
     void setPlayerId(long playerId);
}
